package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;
import ru.stqa.pft.addressbook.module.Groups;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionFromGroup extends TestBase{

    private static void ensurePreconditions1() {
       if (app.db().groups().isEmpty()) {
           app.goTo().groupPage();
           app.group().create(new GroupData().withName("test1"));
       }
    }

    private static GroupData ensurePreconditions2(GroupData selectedGroup) {
        if (selectedGroup.getContacts().isEmpty()) {
            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
            app.contact().createContact(contact.inGroup(selectedGroup));
            return app.db().groups().stream().filter(data -> {
                return Objects.equals(data.getId(), selectedGroup.getId());
            }).findFirst().get();
        }
        return selectedGroup;
    }

    @Test
    public void testContactDeletionFromGroup() {
        ensurePreconditions1();
        Groups groups = app.db().groups();
        GroupData selectedGroup = ensurePreconditions2(groups.iterator().next());
        System.out.println(selectedGroup);
        Contacts before = selectedGroup.getContacts();  //all contacts in the selected group
        System.out.println(before);
        ContactData selectedContact = before.iterator().next();
        app.contact().removeContactFromGroup(selectedContact, selectedGroup);
        app.goTo().goToHomePage();

        Groups groupsAfter = app.db().groups();
        GroupData selectedGroupAfter = groupsAfter.stream().filter(data -> {
                return Objects.equals(data.getId(), selectedGroup.getId());
            }).findFirst().get();
        //GroupData selectedGroupAfter = groupsAfter.iterator().next();
        System.out.println(selectedGroupAfter);
        Contacts after = selectedGroupAfter.getContacts();  //all contacts in the selected group
        Assert.assertEquals(after.size(), before.size() - 1);

        assertThat(after, equalTo(before.without(selectedContact)));

        //verifyInGroupListInUI();
    }
}
