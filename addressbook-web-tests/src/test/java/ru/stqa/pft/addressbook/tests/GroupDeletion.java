package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.GroupData;
import ru.stqa.pft.addressbook.module.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletion extends TestBase{

  @BeforeMethod
  private void ensurePreconditions() {
    if (app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }
  @Test
  public void testDeleteGroup() throws Exception {
    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next();
    app.goTo().groupPage();
    app.group().delete(deletedGroup);

    assertEquals(app.group().count(), before.size() - 1);

    Groups after = app.db().groups();
    //before.remove(deletedGroup);
    assertThat(after, equalTo(before.without(deletedGroup)));

    verifyInGroupListInUI();
  }

}
