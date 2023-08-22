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
            ContactData contactData = new ContactData("Василий", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", null);
            if (!app.contact().isThereThisGroup(contactData.getGroup())) {
                app.goTo().groupPage();
                app.group().create(new GroupData(contactData.getGroup(), contactData.getGroup(), contactData.getGroup()));
            }
            app.contact().createContact(contactData);
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
