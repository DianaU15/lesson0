package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName())); //static - глобальная переменная

    @BeforeSuite
    public void setUp() throws Exception {
      //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Diana\\Documents\\GitHub\\lesson0\\addressbook-web-tests\\src\\test\java\\ru\\stqa\\pft\\addressbook\\");
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    }

}
