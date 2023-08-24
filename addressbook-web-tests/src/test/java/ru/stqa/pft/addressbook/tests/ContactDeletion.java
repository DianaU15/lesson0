package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.List;

public class ContactDeletion extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
            if (!app.contact().isThereThisGroup(contact.getGroup())) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName(contact.getGroup()));
            }
            app.contact().createContact(contact);
        }
    }

    @Test
    public void testContactDeletionFromHome() {
        ensurePreconditions();
        List<ContactData> before = app.contact().list();
        int index = 1;
        app.contact().deleteFromHome(index);
        app.goTo().goToHomePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(after, before);
    }

    @Test (enabled = false)
    public void testContactDeletionFromEdit() {
        ensurePreconditions();
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().deleteFromEdit(index);
        app.goTo().goToHomePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(after, before);
    }


}
