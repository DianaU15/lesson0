package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.List;

public class ContactDeletion extends TestBase{

    @Test
    public void testContactDeletionFromHome() {
        if (!app.getContactHelper().isThereAContact()) {
            ContactData contactData = new ContactData("Василий", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", null);
            if (!app.getContactHelper().isThereThisGroup(contactData.getGroup())) {
                app.getNavigationHelper().goToGroupPage();
                app.getGroupHelper().createGroup(new GroupData(contactData.getGroup(), contactData.getGroup(), contactData.getGroup()));
            }
            app.getContactHelper().createContact(contactData);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().accept();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        System.out.println(before);
        System.out.println(after);
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size()-1);
        Assert.assertEquals(after, before);
    }

    @Test
    public void testContactDeletionFromEdit() {
        if (!app.getContactHelper().isThereAContact()) {
            ContactData contactData = new ContactData("Василий", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", "test245");
            if (!app.getContactHelper().isThereThisGroup(contactData.getGroup())) {
                app.getNavigationHelper().goToGroupPage();
                app.getGroupHelper().createGroup(new GroupData(contactData.getGroup(), contactData.getGroup(), contactData.getGroup()));
            }
            app.getContactHelper().createContact(contactData);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size() - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size()-1);
        Assert.assertEquals(after, before);
    }
}
