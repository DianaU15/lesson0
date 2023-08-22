package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModification extends TestBase{

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
    public void testContactModification(){
        ensurePreconditions();
        List<ContactData> before = app.contact().list();
        int index = before.size()-1;
        ContactData contact = new ContactData(before.get(index).getId(), "лелzzzzzzz", "Артемович", "хомяк1", "Вася", null, "321654987", "sdf@ry.net", "16", "March", "2003", null);
        app.contact().modify(index, contact);

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}
