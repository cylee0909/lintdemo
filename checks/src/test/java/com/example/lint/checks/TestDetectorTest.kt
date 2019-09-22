package com.example.lint.checks

import com.android.sdklib.AndroidVersion
import com.android.tools.lint.checks.BatteryDetector
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Project
import java.io.File

class TestDetectorTest : LintDetectorTest() {
    fun testBasic() {
        val testFile = javaClass.getResource("/BatteryTest.java").file
        val content = File(testFile).readText()

        val gradleContent = File(javaClass.getResource("/build.gradle").file).readText()
        TestFiles.gradle("")
        val result = lint().
                sdkHome(File("/Users/cylee/Library/Android/sdk")).
                files(java(content), gradle(gradleContent))
//                .client(object : com.android.tools.lint.checks.infrastructure.TestLintClient(){
//                    override fun createProject(dir: File, referenceDir: File): Project {
//                        var p = super.createProject(dir, referenceDir)
//                        ReflectUtil.setFieldValue(null, "manifestTargetSdk", AndroidVersion(24, null))
//                        return p;
//                    }
//                })
//                .modifyGradleMocks { androidProject, variant ->
//                    println(androidProject)
//                }
                .run()

        result.expectWarningCount(3)
    }

    override fun setUp() {
        super.setUp()
        ReflectUtil.setFieldValue(AndroidVersion.DEFAULT, "mApiLevel", 24);
        ReflectUtil.setFieldValue(AndroidVersion.DEFAULT, "mCodename", "24");
    }

    override fun getDetector(): Detector {
        return BatteryDetector()
    }

    override fun getIssues(): List<Issue> {
        return listOf(BatteryDetector.ISSUE)
    }
}
