package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfo extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (!app.contact().isThereAContact()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd")
                    .withWorkPhone("1(23)").withMobilePhone("3 45").withHomePhone2("45-6").withHomePhone("383838")
                    .withEmail("asd").withEmail2("dfg").withEmail3("ryty")
                    .withAddress("FFFFFFFFlhtc");
            if (app.db().groups().isEmpty()) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("GroupNew"));
            }
            app.contact().createContact(contact.inGroup(app.db().groups().iterator().next()));
        }
    }
    @Test
    public void testContactInfoFromHome() {
        ensurePreconditions();
        app.goTo().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        //assertThat(cleaned(contact.getAllPhones()), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(cleanedAddress(contactInfoFromEditForm.getAddress())));

    }

    public static String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String mergePhones(ContactData contact) {
        //return cleaned(contact.getHomePhone())+cleaned(contact.getMobilePhone())+cleaned(contact.getWorkPhone());
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhone2())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfo::cleanedPhone)
                .collect(Collectors.joining("\n"));
    }

    public static String cleanedPhone(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    public static String cleanedAddress(String address) {
        return address.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
    }
}
