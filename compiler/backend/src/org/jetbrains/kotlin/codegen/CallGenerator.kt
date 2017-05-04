/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.codegen

import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallParameter
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallType
import org.jetbrains.org.objectweb.asm.Type

abstract class CallGenerator {

    internal class DefaultCallGenerator(private val codegen: ExpressionCodegen) : CallGenerator() {

        override fun genCallInner(
                callableMethod: Callable,
                resolvedCall: ResolvedCall<*>?,
                callDefault: Boolean,
                codegen: ExpressionCodegen,
                dynamicCallParameters: List<DynamicCallParameter>) {
            if (!callDefault) {
                if (callableMethod.isDynamicCall() || resolvedCall?.isDynamic ?: false) {
                    callableMethod.genDynamicInstruction(codegen.v, DynamicCallType.FUNCTION_INVOKE, dynamicCallParameters = dynamicCallParameters)
                }
                else {
                    callableMethod.genInvokeInstruction(codegen.v)
                }
            }
            else {
                (callableMethod as CallableMethod).genInvokeDefaultInstruction(codegen.v)
            }
        }

        override fun afterParameterPut(
                type: Type,
                stackValue: StackValue?,
                parameterIndex: Int) {

        }

        override fun processAndPutHiddenParameters(justProcess: Boolean) {
        }

        override fun processAndPutHiddenParameters(callableMethod: Callable, codegen: ExpressionCodegen, justProcess: Boolean) {
            callableMethod.putHiddenParams(codegen.v)
            processAndPutHiddenParameters(justProcess)
        }

        override fun putHiddenParamsIntoLocals() {

        }

        override fun genValueAndPut(
                valueParameterDescriptor: ValueParameterDescriptor,
                argumentExpression: KtExpression,
                parameterType: Type,
                parameterIndex: Int) {
            val value = codegen.gen(argumentExpression)
            value.put(parameterType, codegen.v)
        }

        override fun putCapturedValueOnStack(
                stackValue: StackValue, valueType: Type, paramIndex: Int) {
            stackValue.put(stackValue.type, codegen.v)
        }

        override fun putValueIfNeeded(
                parameterType: Type, value: StackValue) {
            value.put(value.type, codegen.v)
        }

        override fun reorderArgumentsIfNeeded(actualArgsWithDeclIndex: List<ArgumentAndDeclIndex>, valueParameterTypes: List<Type>) {
            val mark = codegen.myFrameMap.mark()
            val reordered = actualArgsWithDeclIndex.withIndex().dropWhile {
                it.value.declIndex == it.index
            }

            reordered.reversed().map {
                val argumentAndDeclIndex = it.value
                val type = valueParameterTypes.get(argumentAndDeclIndex.declIndex)
                val stackValue = StackValue.local(codegen.frameMap.enterTemp(type), type)
                stackValue.store(StackValue.onStack(type), codegen.v)
                Pair(argumentAndDeclIndex.declIndex, stackValue)
            }.sortedBy {
                it.first
            }.forEach {
                it.second.put(valueParameterTypes.get(it.first), codegen.v)
            }
            mark.dropTo()
        }
    }

    fun genCall(callableMethod: Callable,
                resolvedCall: ResolvedCall<*>?,
                callDefault: Boolean,
                codegen: ExpressionCodegen,
                dynamicCallParameters: List<DynamicCallParameter>) {
        if (resolvedCall != null) {
            val calleeExpression = resolvedCall.call.calleeExpression
            if (calleeExpression != null) {
                codegen.markStartLineNumber(calleeExpression)
            }
        }

        genCallInner(callableMethod, resolvedCall, callDefault, codegen, dynamicCallParameters)
    }

    abstract fun genCallInner(callableMethod: Callable,
                              resolvedCall: ResolvedCall<*>?,
                              callDefault: Boolean,
                              codegen: ExpressionCodegen,
                              dynamicCallParameters: List<DynamicCallParameter>)

    abstract fun afterParameterPut(
            type: Type,
            stackValue: StackValue?,
            parameterIndex: Int)

    abstract fun genValueAndPut(
            valueParameterDescriptor: ValueParameterDescriptor,
            argumentExpression: KtExpression,
            parameterType: Type,
            parameterIndex: Int)

    abstract fun putValueIfNeeded(
            parameterType: Type,
            value: StackValue)

    abstract fun putCapturedValueOnStack(
            stackValue: StackValue,
            valueType: Type, paramIndex: Int)

    abstract fun processAndPutHiddenParameters(justProcess: Boolean)

    abstract fun processAndPutHiddenParameters(callableMethod: Callable, codegen: ExpressionCodegen, justProcess: Boolean)

    /*should be called if justProcess = true in processAndPutHiddenParameters*/
    abstract fun putHiddenParamsIntoLocals()

    abstract fun reorderArgumentsIfNeeded(actualArgsWithDeclIndex: List<ArgumentAndDeclIndex>, valueParameterTypes: List<Type>)
}
