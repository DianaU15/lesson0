package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.module.GroupData;
import ru.stqa.pft.addressbook.module.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModification extends TestBase{

    @BeforeMethod
    private void ensurePreconditions() {
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }
    @Test
    public void testGroupModification(){
        ensurePreconditions();
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("newtest9").withHeader("newtest2").withFooter("newTest3");
        app.goTo().groupPage();
        app.group().modify(modifiedGroup, group);

        Assert.assertEquals(app.group().count(), before.size());

        //before.remove(modifiedGroup);
        //before.add(group);
        //Assert.assertEquals(before, after);
        Groups after = app.db().groups();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

        verifyInGroupListInUI();

    }




}
