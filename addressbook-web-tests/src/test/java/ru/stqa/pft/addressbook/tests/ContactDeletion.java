package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.List;
import java.util.Set;

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

    @Test (enabled = false)
    public void testContactDeletionFromHome() {
        ensurePreconditions();
        Set<ContactData> before = app.contact().all();
        ContactData deletedGroup = before.iterator().next();
        app.contact().deleteFromHome(deletedGroup);
        app.goTo().goToHomePage();

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedGroup);
        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactDeletionFromEdit() {
        ensurePreconditions();
        Set<ContactData> before = app.contact().all();
        ContactData deletedGroup = before.iterator().next();
        app.contact().deleteFromEdit(deletedGroup);
        app.goTo().goToHomePage();

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedGroup);
        Assert.assertEquals(after, before);
    }


}
