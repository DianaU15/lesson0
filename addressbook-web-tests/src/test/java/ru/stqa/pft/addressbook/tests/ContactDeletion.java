package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

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
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().accept();
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
        app.getContactHelper().initContactModification();
        app.getContactHelper().deleteSelectedContact();
    }
}
