package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreation extends TestBase{

  @Test (enabled = false)
  public void testContactCreation(){
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("3", "", "Ортем", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003", "test6");
    if (!app.getContactHelper().isThereThisGroup(contact.getGroup())) {
      app.goTo().groupPage();
      app.group().create(new GroupData(contact.getGroup(), contact.getGroup(), contact.getGroup()));
    }
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(contact, true);
    app.getContactHelper().submitContactCreation();
    app.goTo().goToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()+1);

    //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
