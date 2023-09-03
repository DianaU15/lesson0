package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.ContactData;
import ru.stqa.pft.addressbook.module.Contacts;
import ru.stqa.pft.addressbook.module.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreation extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src\\test\\resources\\contacts.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    xstream.allowTypes(new Class[]{ContactData.class});
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  private void ensurePreconditions(ContactData contact) {
    if (contact.getGroup() != null && !app.contact().isThereThisGroup(contact.getGroup()) ) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(contact.getGroup()));
    }
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact){
    app.goTo().goToHomePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/image.jpg");
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
