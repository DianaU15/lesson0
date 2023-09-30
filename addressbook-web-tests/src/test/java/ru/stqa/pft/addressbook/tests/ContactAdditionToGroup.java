package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;
import ru.stqa.pft.addressbook.module.Groups;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroup extends TestBase{
    @BeforeMethod
    private static void ensurePreconditions1() {
        if (app.db().groups().isEmpty()) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if (app.db().contacts().isEmpty()) {
            app.contact().createContact(new ContactData().withFirstname("Fhntv").withLastname("fylhttd"));
        }
    }

    private static GroupData ensurePreconditions2(ContactData selectedContact, GroupData selectedGroup) {
        Contacts contacts = selectedGroup.getContacts();
//        if (!contacts.isEmpty()) {
//            System.out.println("---------------" + contacts.stream().filter(data -> {
//                return Objects.equals(data.getId(), selectedContact.getId());
//            }) + "---------------");
//            ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
//            app.contact().createContact(contact.inGroup(selectedGroup));
//            return app.db().groups().stream().filter(data -> {
//                return Objects.equals(data.getId(), selectedGroup.getId());
//            }).findFirst().get();
//        }
        Groups groups = selectedContact.getGroups();
        System.out.println(selectedGroup);
        System.out.println(selectedContact);
        System.out.println("==========");
        if (!groups.stream().noneMatch(data -> Objects.equals(data.getName(), selectedGroup.getName()))) {
            app.contact().removeContactFromGroup(selectedContact, selectedGroup);
            return app.db().groups().stream().filter(data -> {
                    return Objects.equals(data.getId(), selectedGroup.getId());
                }).findFirst().get();
        }
        System.out.println("==========");

        return selectedGroup;
    }

    @Test (enabled = false)
    public void testContactAdditionToGroup() {
        ensurePreconditions1();
        Contacts contacts = app.db().contacts();
        ContactData selectedContact = contacts.iterator().next();
        Groups groups = app.db().groups();
        GroupData selectedGroup = groups.iterator().next();
        System.out.println(selectedGroup);
        selectedGroup = ensurePreconditions2(selectedContact, selectedGroup);
        Contacts before = selectedGroup.getContacts();  //all contacts in the selected group
        System.out.println(before);

        app.contact().addContactToGroup(selectedContact, selectedGroup);
        app.goTo().goToHomePage();

        Groups groupsAfter = app.db().groups();
//        GroupData selectedGroupAfter = groupsAfter.stream().filter(data -> {
//            return Objects.equals(data, selectedGroup.getId());
//        }).findFirst().get();
        GroupData selectedGroupAfter = groupsAfter.iterator().next();
        System.out.println(selectedGroupAfter);
        Contacts after = selectedGroupAfter.getContacts();  //all contacts in the selected group
        System.out.println(before);
        System.out.println(after);
        Assert.assertEquals(after.size(), before.size() + 1);

        assertThat(after, equalTo(before.withAdded(selectedContact)));

        //verifyInGroupListInUI();
    }
}
