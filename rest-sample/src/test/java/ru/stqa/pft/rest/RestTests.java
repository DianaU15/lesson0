package ru.stqa.pft.rest;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

    @Test
    public void testCreateIssue() throws IOException{
        skipIfNotFixed(53);
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("EHHFFFFFFFF").withDescription("DESC");
        int issueId = createIssue(newIssue);
        oldIssues.add(newIssue.withId(issueId));
        //System.out.println(oldIssues);
        Set<Issue> newIssues = getIssues();
        //System.out.println(newIssues);
        assertEquals(newIssues, oldIssues);;
    }



}
