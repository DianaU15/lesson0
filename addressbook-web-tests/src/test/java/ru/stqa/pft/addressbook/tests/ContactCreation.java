package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreation extends TestBase{

  private void ensurePreconditions(ContactData contact) {
    if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup()) ) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(contact.getGroup()));
    }
  }

  @Test
  public void testContactCreation(){
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().withFirstname("Fhntv").withLastname("fylhttd");
    ensurePreconditions(contact);
    app.contact().createContact(contact);

    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size()+1);

    //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }




}
