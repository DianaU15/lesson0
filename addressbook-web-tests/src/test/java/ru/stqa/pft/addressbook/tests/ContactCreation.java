package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreation extends TestBase{

  private void ensurePreconditions(ContactData contact) {
    if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup()) ) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(contact.getGroup()));
    }
  }

  @Test
  public void testContactCreation(){
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
    ensurePreconditions(contact);
    app.contact().createContact(contact);

    Contacts after = app.contact().all();
    //Assert.assertEquals(after.size(), before.size()+1);

    //contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());
    //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
    //before.add(contact);
    /*Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);*/
    //Assert.assertEquals(before, after);
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
  }




}
