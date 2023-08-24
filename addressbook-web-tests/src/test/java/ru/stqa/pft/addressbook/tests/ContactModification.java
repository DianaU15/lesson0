package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModification extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
            if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup())) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName(contact.getGroup()));
            }
            app.contact().createContact(contact);
        }
    }

    @Test
    public void testContactModification(){
        ensurePreconditions();
        Set<ContactData> before = app.contact().all();
        ContactData deletedGroup = before.iterator().next();
        ContactData contact = new ContactData().withId(deletedGroup.getId()).withFirstname("Fhntv").withLastname("fylhttd");
        app.contact().modify(deletedGroup, contact);

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
        before.remove(deletedGroup);
        before.add(contact);
        /*Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);*/
        Assert.assertEquals(before, after);
    }


}
