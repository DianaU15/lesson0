package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;

public class ContactCreation extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactCreation(new ContactData("Василий", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }


}
