package org.lambdaTestCertification;

import com.microsoft.playwright.*;
import lombok.extern.log4j.Log4j2;
import org.lambdaTestCertification.pages.utils.PlayWrightAPI;
import org.lambdaTestCertification.pages.utils.PlayWrightThread;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.lambdaTestCertification.pages.*;

import java.lang.reflect.Method;


@Log4j2
public class TestBase {
    //Playwright
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    //Pages
    PlaygroundPage playgroundPage;
    DragAndDropPage dragAndDropPage;
    InputFormPage inputFormPage;

    @BeforeMethod
    void beforeMethod(Method method) {
        log.warn("********************************************");
        log.warn("{} is started", method.getName());
        page = PlayWrightThread.getPage();
    }

    @AfterMethod
    void afterMethod(ITestResult result, Method method) {
        String testName = method.getName();
        if (result.isSuccess()) {
            PlayWrightAPI.setTestStatus("passed", testName + " passed", page);
            log.warn("{} is finished with status passed", testName);
        } else {
            PlayWrightAPI.setTestStatus("failed", testName + " failed", page);
            log.warn("{} is finished with status failed", testName);
        }
        PlayWrightThread.closePage(method);
    }
}
