package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhones extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd")
                    .withWorkPhone("1(23)").withMobilePhone("3 45").withHomePhone2("45-6").withHomePhone("383838");
            if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup())) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName(contact.getGroup()));
            }
            app.contact().createContact(contact);
        }
    }
    @Test
    public void testContactPhones() {
        ensurePreconditions();
        app.goTo().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        //assertThat(cleaned(contact.getAllPhones()), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        //return cleaned(contact.getHomePhone())+cleaned(contact.getMobilePhone())+cleaned(contact.getWorkPhone());
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhone2())
                .stream().filter((s)-> !s.equals(""))
                .map(ContactPhones::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
