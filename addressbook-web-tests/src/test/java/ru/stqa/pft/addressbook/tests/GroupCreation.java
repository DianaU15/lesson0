package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.module.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class GroupCreation extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("test1");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size()+1);

    group.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());
    //список преобразуем в поток, ищем в нем максимальный с помощью анонимной функции
    before.add(group);
    /*Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);*/   /*для списка*/
    Assert.assertEquals(before, after);
  }

}
