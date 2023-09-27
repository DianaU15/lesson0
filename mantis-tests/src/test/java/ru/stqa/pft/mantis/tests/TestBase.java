package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName())); //static - глобальная переменная

    @BeforeSuite
    public void setUp() throws Exception {
      //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Diana\\Documents\\GitHub\\lesson0\\addressbook-web-tests\\src\\test\java\\ru\\stqa\\pft\\addressbook\\");
        app.init();
        //app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        //app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        return app.soap().isIssueOpen(issueId);
    }

    public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
