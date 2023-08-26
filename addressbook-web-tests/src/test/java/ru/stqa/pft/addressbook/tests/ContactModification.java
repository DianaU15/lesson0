package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModification extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        app.goTo().goToHomePage();
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
        Contacts before = app.contact().all();
        System.out.println(before.size());
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Fhntv").withLastname("fylhttd");
        app.contact().modify(modifiedContact, contact);

        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции

        /*Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);*/
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }


}
