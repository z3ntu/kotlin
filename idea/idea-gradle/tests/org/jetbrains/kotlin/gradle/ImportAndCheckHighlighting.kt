/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle

import org.jetbrains.kotlin.idea.codeInsight.gradle.MultiplePluginVersionGradleImportingTestCase
import org.jetbrains.kotlin.idea.codeInsight.gradle.mppImportTestMinVersionForMaster
import org.jetbrains.plugins.gradle.tooling.annotation.PluginTargetVersions
import org.junit.Test


class ImportAndCheckHighlighting : MultiplePluginVersionGradleImportingTestCase() {

    @Test
    @PluginTargetVersions(pluginVersion = "1.3.40+", gradleVersionForLatestPlugin = mppImportTestMinVersionForMaster)
    fun testMultiplatformLibrary() {
        importAndCheckHighlighting()
    }

    @Test
    @PluginTargetVersions(pluginVersion = "1.3.40+", gradleVersionForLatestPlugin = mppImportTestMinVersionForMaster)
    fun testUnresolvedInMultiplatformLibrary() {
        importAndCheckHighlighting(false, false)
    }

    // TODO: this test fails with cryptic message due to KT-34560 when built-in declarations become unresolved
    // This happens because of intentions/quickfixes that try to fix unresolved call by adding matching import
    // and access AST fordecompiled files in process.
    @Test
    @PluginTargetVersions(pluginVersion = "1.4.30+", gradleVersionForLatestPlugin = mppImportTestMinVersionForMaster)
    fun testBuiltinsAndStdlib() {
        importAndCheckHighlighting(false, false)
    }

    private fun importAndCheckHighlighting(testLineMarkers: Boolean = true, checkWarnings: Boolean = true) {
        val files = configureByFiles()
        importProject(skipIndexing = false)
        val project = myTestFixture.project
        checkFiles(
            files.filter { it.extension == "kt" || it.extension == "java" },
            project,
            object : GradleDaemonAnalyzerTestCase(
                testLineMarkers = testLineMarkers,
                checkWarnings = checkWarnings,
                checkInfos = false,
                rootDisposable = testRootDisposable
            ) {}
        )
    }

    override fun testDataDirName(): String {
        return "importAndCheckHighlighting"
    }
}
