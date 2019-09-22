package com.example.lint.checks

import com.android.sdklib.AndroidVersion
import com.android.tools.lint.checks.BatteryDetector
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.client.api.LintClient
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Project
import java.io.File

class TestDetectorTest : LintDetectorTest() {
    fun testBasic() {
        val testFile = javaClass.getResource("/BatteryTest.java").file
        val content = File(testFile).readText()
        TestFiles.gradle("")
        val result = lint().
                sdkHome(File("/Users/cylee/Library/Android/sdk")).
                files(java(content))
                .modifyGradleMocks { androidProject, variant ->
//                    androidProject.defaultConfig.productFlavor.minSdkVersion = 19
                    println(androidProject)
                }
                .run()

        result.expectWarningCount(2)
    }

    override fun getDetector(): Detector {
        return BatteryDetector()
    }

    override fun getIssues(): List<Issue> {
        return listOf(BatteryDetector.ISSUE)
    }
}
