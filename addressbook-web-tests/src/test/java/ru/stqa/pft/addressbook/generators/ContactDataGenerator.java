package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.module.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        //generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts,new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(contacts,new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contacts,new File(file));
        } else {
            System.out.println("Unrecognized format" + format);
        }
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        //xstream.processAnnotations(GroupData.class);
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s\n", contact.getFirstname(), contact.getLastname(), contact.getHomePhone()));
        }
        writer.close();
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData().withFirstname(String.format("sdfsdfs 1.%s", i))
                    .withLastname(String.format("sdfsdfs %s", i))
                    .withMobilePhone(String.format("89658962%s", i))
                    .withWorkPhone(String.format("55555555552%s", i))
                    .withMiddlename(String.format("sdfsdfs 2.%s", i))
                    .withNickname(String.format("sdfsdfs 2.%s", i))
                    .withHomePhone2(String.format("354532%s", i))
                    .withAddress(String.format("Flhtc Flhtc Flhtc.%s", i))
                    .withEmail(String.format("sdfsdfs 2.%s", i))
                    .withEmail2(String.format("sdfsdfs 2.%s", i))
                    .withEmail3(String.format("dfbfbf 2.%s", i))
                    .withHomePhone(String.format("8965896%s", i))
                    //.withGroup(String.format("group %s", i))
                    );
        }
        return contacts;
    }
}
