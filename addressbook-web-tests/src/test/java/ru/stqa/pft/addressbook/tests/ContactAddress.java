package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddress extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd")
                    .withAddress("FFFFFFFFlhtc");
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

        assertThat(contact.getAddress().replaceAll("\\s", ""), equalTo(contactInfoFromEditForm.getAddress().replaceAll("\\s", "")));
    }

}
