package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;

import static org.testng.Assert.fail;

public class DeleteGroup extends TestBase{

  @Test
  public void testDeleteGroup() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroup();
    app.getGroupHelper().returnToGroupPage();
  }

}
