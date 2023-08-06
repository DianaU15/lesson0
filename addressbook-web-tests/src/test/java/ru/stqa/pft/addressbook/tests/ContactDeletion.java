package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletion extends TestBase{

    @Test
    public void testContactDeletionFromHome() throws InterruptedException {
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().accept();
        Thread.sleep(4000);
    }

    @Test
    public void testContactDeletionFromEdit() throws InterruptedException {
        app.getContactHelper().initContactModification();
        app.getContactHelper().deleteSelectedContact();
        Thread.sleep(4000);
    }
}
