package tech.ugma.brorater.model;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of Persons and a list of Bills.
 * This is used for saving the lists to XML.
 *
 * @author Marco Jakob
 * @author Bradley Turek
 */
@XmlRootElement(name = "storage")
public class StorageContainer {

    private List<Person> persons;
    private List<Bill> bills;

    @XmlElement(name = "person")
    @XmlElementWrapper(name="people")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }


    @XmlElement(name = "bill")
    @XmlElementWrapper(name = "bills")
    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}
