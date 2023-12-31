package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;
import ru.stqa.pft.addressbook.module.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.ContactInfo.*;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.FIREFOX.browserName())); //static - глобальная переменная

    @BeforeSuite
    public void setUp() throws Exception {
      //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Diana\\Documents\\GitHub\\lesson0\\addressbook-web-tests\\src\\test\java\\ru\\stqa\\pft\\addressbook\\");
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test " + m.getName() + "with parametrs " + Arrays.asList(p));
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }


    public void verifyInGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }

    }

    public void verifyInContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts, equalTo(dbContacts.stream()
                    .map((g) -> new ContactData()
                            .withId(g.getId()).withLastname(g.getLastname()).withFirstname(g.getLastname())
                            .withAddress(cleanedAddress(g.getAddress())).
                            withAllEmails(mergeEmails(g)).withAllPhones(mergePhones(g)))
                    .collect(Collectors.toSet())));
        }

    }

}
