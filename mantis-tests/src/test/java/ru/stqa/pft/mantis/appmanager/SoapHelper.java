package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import com.google.protobuf.ServiceException;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, javax.xml.rpc.ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
        return Arrays.asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
                .collect(Collectors.toSet());
    }

    private static MantisConnectPortType getMantisConnect() throws MalformedURLException, javax.xml.rpc.ServiceException, RemoteException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.25.7/api/soap/mantisconnect.php?"));
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, javax.xml.rpc.ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueDate = new IssueData();
        issueDate.setSummary(issue.getSummary());
        issueDate.setDescription(issue.getDescription());
        issueDate.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueDate.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add("administrator", "root", issueDate);
        IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
        return new Issue().withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId().intValue()))
                .withName(createdIssueData.getProject().getName());
    }

    public boolean isIssueOpen(int issueId) throws MalformedURLException, javax.xml.rpc.ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData issue = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        System.out.println(issue.getStatus().getName());
        return !(issue.getStatus().getName().equals("resolved") || issue.getStatus().getName().equals("closed"));
    }

}
