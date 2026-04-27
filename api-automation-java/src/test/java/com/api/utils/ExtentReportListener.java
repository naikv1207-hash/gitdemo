package com.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportListener — Generates beautiful HTML reports using ExtentReports.
 *
 * Attach in testng.xml:
 *   <listener class-name="com.api.utils.ExtentReportListener"/>
 *
 * Reports saved to: test-output/reports/ExtentReport_<timestamp>.html
 */
public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ─────────────────────────────────────────────────────────
    // Suite-level: setup & teardown
    // ─────────────────────────────────────────────────────────
    @Override
    public void onStart(ISuite suite) {
        String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath  = "test-output/reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("REST API Test Results");
        sparkReporter.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Framework",    "RestAssured + TestNG");
        extent.setSystemInfo("Environment",  "Reqres.in (Sandbox)");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS",           System.getProperty("os.name"));

        System.out.println("📊 Extent Report will be saved to: " + reportPath);
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ Extent Report generated successfully.");
        }
    }

    // ─────────────────────────────────────────────────────────
    // Test-level: pass / fail / skip
    // ─────────────────────────────────────────────────────────
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(
                result.getMethod().getDescription(),
                result.getMethod().getMethodName()
        );
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail("❌ Test Failed: " + result.getThrowable().getMessage());
        extentTest.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("⏭ Test Skipped: " + result.getThrowable().getMessage());
    }
}
