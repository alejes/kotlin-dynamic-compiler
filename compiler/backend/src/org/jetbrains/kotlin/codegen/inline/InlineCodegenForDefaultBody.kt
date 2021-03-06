/*
 * Copyright 2010-2016 JetBrains s.r.o.
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

package org.jetbrains.kotlin.codegen.inline

import org.jetbrains.kotlin.codegen.*
import org.jetbrains.kotlin.codegen.state.GenerationState
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallParameter
import org.jetbrains.kotlin.resolve.inline.InlineUtil
import org.jetbrains.org.objectweb.asm.Label
import org.jetbrains.org.objectweb.asm.Type
import org.jetbrains.org.objectweb.asm.tree.MethodNode

class InlineCodegenForDefaultBody(
        function: FunctionDescriptor,
        codegen: ExpressionCodegen,
        val state: GenerationState
) : CallGenerator() {

    private val sourceMapper: SourceMapper = codegen.parentCodegen.orCreateSourceMapper

    private val functionDescriptor =
            if (InlineUtil.isArrayConstructorWithLambda(function))
                FictitiousArrayConstructor.create(function as ConstructorDescriptor)
            else
                function.original


    private val context = InlineCodegen.getContext(
            functionDescriptor, state,
            DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor)?.containingFile as? KtFile
    )

    private val jvmSignature = state.typeMapper.mapSignatureWithGeneric(functionDescriptor, context.contextKind)

    private val methodStartLabel = Label()

    init {
        assert(InlineUtil.isInline(function)) {
            "InlineCodegen can inline only inline functions and array constructors: " + function
        }
        InlineCodegen.reportIncrementalInfo(functionDescriptor, codegen.context.functionDescriptor.original, jvmSignature, state)

        //InlineCodegenForDefaultBody created just after visitCode call
        codegen.v.visitLabel(methodStartLabel)
    }

    override fun genCallInner(callableMethod: Callable,
                              resolvedCall: ResolvedCall<*>?,
                              callDefault: Boolean,
                              codegen: ExpressionCodegen,
                              dynamicCallParameters: List<DynamicCallParameter>) {
        val nodeAndSmap = InlineCodegen.createMethodNode(functionDescriptor, jvmSignature, codegen, context, callDefault, null)
        val childSourceMapper = InlineCodegen.createNestedSourceMapper(nodeAndSmap, sourceMapper)

        val node = nodeAndSmap.node
        val transformedMethod = MethodNode(
                node.access,
                node.name,
                node.desc,
                node.signature,
                node.exceptions.toTypedArray())

        val argsSize = (Type.getArgumentsAndReturnSizes(jvmSignature.asmMethod.descriptor) ushr  2) - if (callableMethod.isStaticCall()) 1 else 0
        node.accept(object : InlineAdapter(transformedMethod, 0, childSourceMapper) {
            override fun visitLocalVariable(name: String, desc: String, signature: String?, start: Label, end: Label, index: Int) {
                val startLabel = if (index < argsSize) methodStartLabel else start
                super.visitLocalVariable(name, desc, signature, startLabel, end, index)
            }
        })

        transformedMethod.accept(MethodBodyVisitor(codegen.v))
    }

    override fun afterParameterPut(type: Type, stackValue: StackValue?, parameterIndex: Int) {
        throw UnsupportedOperationException("Shouldn't be called")
    }

    override fun genValueAndPut(valueParameterDescriptor: ValueParameterDescriptor, argumentExpression: KtExpression, parameterType: Type, parameterIndex: Int) {
        throw UnsupportedOperationException("Shouldn't be called")
    }

    override fun putValueIfNeeded(parameterType: Type, value: StackValue) {
        //original method would be inlined directly into default impl body without any inline magic
        //so we no need to load variables on stack to further method call
    }

    override fun putCapturedValueOnStack(stackValue: StackValue, valueType: Type, paramIndex: Int) {
        throw UnsupportedOperationException("Shouldn't be called")
    }

    override fun processAndPutHiddenParameters(justProcess: Boolean) {
        throw UnsupportedOperationException("Shouldn't be called")
    }

    override fun processAndPutHiddenParameters(callableMethod: Callable, codegen: ExpressionCodegen, justProcess: Boolean) {
        processAndPutHiddenParameters(justProcess)
    }

    override fun putHiddenParamsIntoLocals() {
        throw UnsupportedOperationException("Shouldn't be called")
    }

    override fun reorderArgumentsIfNeeded(actualArgsWithDeclIndex: List<ArgumentAndDeclIndex>, valueParameterTypes: List<Type>) {
        throw UnsupportedOperationException("Shouldn't be called")
    }
}
