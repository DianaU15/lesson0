package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletion extends TestBase{

    @BeforeMethod
    private static void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().goToHomePage();
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
            if (app.db().groups().isEmpty()) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("GroupNew"));
            }
            app.contact().createContact(contact.inGroup(app.db().groups().iterator().next()));
        }
    }

    @Test
    public void testContactDeletionFromHome() {
        ensurePreconditions();
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().deleteFromHome(deletedContact);
        app.goTo().goToHomePage();

        Contacts after = app.db().contacts();
        Assert.assertEquals(after.size(), before.size() - 1);

        assertThat(after, equalTo(before.without(deletedContact)));

        verifyInGroupListInUI();
    }

    @Test
    public void testContactDeletionFromEdit() {
        ensurePreconditions();
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().deleteFromEdit(deletedContact);
        app.goTo().goToHomePage();

        Contacts after = app.db().contacts();
        Assert.assertEquals(after.size(), before.size() - 1);

        assertThat(after, equalTo(before.without(deletedContact)));


    }


}
