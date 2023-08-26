package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmails extends TestBase{
    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd")
                    .withEmail("asd").withEmail2("dfg").withEmail3("ryty");
            if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup())) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName(contact.getGroup()));
            }
            app.contact().createContact(contact);
        }
    }
    @Test
    public void testContactEmails() {
        ensurePreconditions();
        app.goTo().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        System.out.println(contact.getAllEmails());
        System.out.println(mergeEmails(contactInfoFromEditForm));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
