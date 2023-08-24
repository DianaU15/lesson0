package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.module.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("home"), contactData.getPhone());
        type(By.name("email"), contactData.getMail());

        if (creation) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initContactModification(int index) {
        String s = "//tr["+String.valueOf(index+2)+"]/td[8]/a";
        click(By.xpath(s));

    }

    public void initContactModificationById(int id) {
        click(By.xpath("//a[@href='edit.php?id=" + id + "']"));

    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void selectContact(int index) {
        //click(By.name("selected[]"));
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void accept() {
        wd.switchTo().alert().accept();
    }

    public void createContact(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public boolean isThereThisGroup(String group) {
        try {
            new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements){
            String lastname = element.findElement(By.xpath("td[2]")).getText();
            String firstname = element.findElement(By.xpath("td[3]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String mail = element.findElement(By.xpath("td[5]")).getText();
            String phone = element.findElement(By.xpath("td[6]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withLastname(lastname).withFirstname(firstname).withAddress(address).withMail(mail).withPhone(phone);
            contacts.add(contact);
        }
        return contacts;
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements){
            String lastname = element.findElement(By.xpath("td[2]")).getText();
            String firstname = element.findElement(By.xpath("td[3]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String mail = element.findElement(By.xpath("td[5]")).getText();
            String phone = element.findElement(By.xpath("td[6]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withLastname(lastname).withFirstname(firstname).withAddress(address).withMail(mail).withPhone(phone);
            contacts.add(contact);
        }
        return contacts;
    }

    public void deleteFromHome(int index) {
        selectContact(index);
        deleteSelectedContact();
        accept();
    }

    public void deleteFromHome(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        accept();
    }

    public void deleteFromEdit(int index) {
        initContactModificationById(index);
        deleteSelectedContact();
    }

    public void deleteFromEdit(ContactData contact) {
        initContactModificationById(contact.getId());
        deleteSelectedContact();
    }

    public void modify(ContactData deletedGroup, ContactData contact) {
        initContactModificationById(deletedGroup.getId());
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }
}

