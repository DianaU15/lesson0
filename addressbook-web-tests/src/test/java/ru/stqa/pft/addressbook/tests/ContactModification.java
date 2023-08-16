package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

public class ContactModification extends TestBase{

    @Test
    public void testContactModification(){
        if (!app.getContactHelper().isThereAContact()) {
            ContactData contactData = new ContactData("Василий", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", "test2");
            if (!app.getContactHelper().isThereThisGroup(contactData.getGroup())) {
                app.getNavigationHelper().goToGroupPage();
                app.getGroupHelper().createGroup(new GroupData(contactData.getGroup(), contactData.getGroup(), contactData.getGroup()));
            }
            app.getContactHelper().createContact(contactData);
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Михаил", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
