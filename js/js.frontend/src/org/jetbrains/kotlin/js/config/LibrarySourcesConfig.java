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

package org.jetbrains.kotlin.js.config;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.PathUtil;
import com.intellij.util.io.URLUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.JsMetadataVersion;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadata;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils;
import org.jetbrains.kotlin.utils.LibraryUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jetbrains.kotlin.utils.LibraryUtils.isOldKotlinJavascriptLibrary;
import static org.jetbrains.kotlin.utils.PathUtil.getKotlinPathsForDistDirectory;

public class LibrarySourcesConfig extends JsConfig {
    public static final List<String> JS_STDLIB =
            Collections.singletonList(getKotlinPathsForDistDirectory().getJsStdLibJarPath().getAbsolutePath());

    public static final List<String> JS_KOTLIN_TEST =
            Collections.singletonList(getKotlinPathsForDistDirectory().getJsKotlinTestJarPath().getAbsolutePath());

    public static final Key<String> EXTERNAL_MODULE_NAME = Key.create("externalModule");
    public static final String UNKNOWN_EXTERNAL_MODULE_NAME = "<unknown>";

    public LibrarySourcesConfig(@NotNull Project project, @NotNull CompilerConfiguration configuration) {
        super(project, configuration);
    }

    @NotNull
    public List<String> getLibraries() {
        return getConfiguration().getList(JSConfigurationKeys.LIBRARIES);
    }

    @Override
    protected void init(@NotNull final List<KtFile> sourceFilesInLibraries, @NotNull final List<KotlinJavascriptMetadata> metadata) {
        if (getLibraries().isEmpty()) return;

        final PsiManager psiManager = PsiManager.getInstance(getProject());

        JsConfig.Reporter report = new JsConfig.Reporter() {
            @Override
            public void error(@NotNull String message) {
                throw new IllegalStateException(message);
            }
        };

        Function2<String, VirtualFile, Unit> action = new Function2<String, VirtualFile, Unit>() {
            @Override
            public Unit invoke(String moduleName, VirtualFile file) {
                if (moduleName != null) {
                    JetFileCollector jetFileCollector = new JetFileCollector(sourceFilesInLibraries, moduleName, psiManager);
                    VfsUtilCore.visitChildrenRecursively(file, jetFileCollector);
                }
                else {
                    String libraryPath = PathUtil.getLocalPath(file);
                    assert libraryPath != null : "libraryPath for " + file + " should not be null";
                    metadata.addAll(KotlinJavascriptMetadataUtils.loadMetadata(libraryPath));
                }

                return Unit.INSTANCE;
            }
        };

        boolean hasErrors = checkLibFilesAndReportErrors(report, action);
        assert !hasErrors : "hasErrors should be false";
    }

    @Override
    public boolean checkLibFilesAndReportErrors(@NotNull JsConfig.Reporter report) {
        return checkLibFilesAndReportErrors(report, null);
    }

    private boolean checkLibFilesAndReportErrors(@NotNull JsConfig.Reporter report, @Nullable Function2<String, VirtualFile, Unit> action) {
        List<String> libraries = getLibraries();
        if (libraries.isEmpty()) {
            return false;
        }

        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem(StandardFileSystems.FILE_PROTOCOL);
        VirtualFileSystem jarFileSystem = VirtualFileManager.getInstance().getFileSystem(StandardFileSystems.JAR_PROTOCOL);

        Set<String> modules = new HashSet<String>();

        for (String path : libraries) {
            VirtualFile file;

            File filePath = new File(path);
            if (!filePath.exists()) {
                report.error("Path '" + path + "' does not exist");
                return true;
            }

            if (path.endsWith(".jar") || path.endsWith(".zip")) {
                file = jarFileSystem.findFileByPath(path + URLUtil.JAR_SEPARATOR);
            }
            else {
                file = fileSystem.findFileByPath(path);
            }

            if (file == null) {
                report.error("File '" + path + "' does not exist or could not be read");
                return true;
            }

            String moduleName;

            if (isOldKotlinJavascriptLibrary(filePath)) {
                moduleName = LibraryUtils.getKotlinJsModuleName(filePath);
                if (!modules.add(moduleName)) {
                    report.warning("Module \"" + moduleName + "\" is defined in more, than one file");
                }
            }
            else {
                List<KotlinJavascriptMetadata> metadataList = KotlinJavascriptMetadataUtils.loadMetadata(filePath);
                if (metadataList.isEmpty()) {
                    report.warning("'" + path + "' is not a valid Kotlin Javascript library");
                    continue;
                }

                for (KotlinJavascriptMetadata metadata : metadataList) {
                    if (!metadata.getVersion().isCompatible()) {
                        report.error("File '" + path + "' was compiled with an incompatible version of Kotlin. " +
                                     "The binary version of its metadata is " + metadata.getVersion() +
                                     ", expected version is " + JsMetadataVersion.INSTANCE);
                        return true;
                    }
                    if (!modules.add(metadata.getModuleName())) {
                        report.warning("Module \"" + metadata.getModuleName() + "\" is defined in more, than one file");
                    }
                }

                moduleName = null;
            }

            if (action != null) {
                action.invoke(moduleName, file);
            }
        }

        return false;
    }

    private static KtFile getJetFileByVirtualFile(VirtualFile file, String moduleName, PsiManager psiManager) {
        PsiFile psiFile = psiManager.findFile(file);
        assert psiFile != null;

        setupPsiFile(psiFile, moduleName);
        return (KtFile) psiFile;
    }

    private static void setupPsiFile(PsiFile psiFile, String moduleName) {
        psiFile.putUserData(EXTERNAL_MODULE_NAME, moduleName);
    }

    private static class JetFileCollector extends VirtualFileVisitor {
        private final List<KtFile> jetFiles;
        private final String moduleName;
        private final PsiManager psiManager;

        private JetFileCollector(List<KtFile> files, String name, PsiManager manager) {
            moduleName = name;
            psiManager = manager;
            jetFiles = files;
        }

        @Override
        public boolean visitFile(@NotNull VirtualFile file) {
            if (!file.isDirectory() && StringUtil.notNullize(file.getExtension()).equalsIgnoreCase(KotlinFileType.EXTENSION)) {
                jetFiles.add(getJetFileByVirtualFile(file, moduleName, psiManager));
            }
            return true;
        }
    }
}
