package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;

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
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size()-1);
        ContactData contact = new ContactData(before.get(before.size()-1).getId(), "лелzzzzzzz", "Артемович", "хомяк1", "Вася", null, "321654987", "sdf@ry.net", "16", "March", "2003", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
        before.remove(before.size()-1);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);
    }
}
