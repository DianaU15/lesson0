package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;

public class ContactModification extends TestBase{

    @Test
    public void testContactModification(){
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Михаил", "Артемович", "Артемьев", "Вася", "321654987", "sdf@ry.net", "16", "March", "2003"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
