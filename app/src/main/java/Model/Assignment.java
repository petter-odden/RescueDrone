package Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Hashtable;

public class Assignment {
    private String UID;
    private Person creatorPerson;
    private LocalDate creationTime;

    private boolean lookingForPeople;
    private boolean hideAssignment;

    private HashMap<String, Person> hiredPeople;

    public Assignment(String UID, EmergencyPersonnel creatorPerson, LocalDate creationTime, boolean lookingForPeople, boolean hideAssignment) {
        this.UID = UID;
        this.creatorPerson = creatorPerson;
        this.creationTime = creationTime;
        this.lookingForPeople = lookingForPeople;
        this.hideAssignment = hideAssignment;

        this.hiredPeople = new HashMap<String, Person>();
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public EmergencyPersonnel getCreatorPerson() {
        return creatorPerson;
    }

    public void setCreatorPerson(EmergencyPersonnel creatorPerson) {
        this.creatorPerson = creatorPerson;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isLookingForPeople() {
        return lookingForPeople;
    }

    public void setLookingForPeople(boolean lookingForPeople) {
        this.lookingForPeople = lookingForPeople;
    }

    public boolean isHideAssignment() {
        return hideAssignment;
    }

    public void setHideAssignment(boolean hideAssignment) {
        this.hideAssignment = hideAssignment;
    }

    public HashMap<String, Person> getHiredPeople() {
        return hiredPeople;
    }

    public void setHiredPeople(HashMap<String, Person> hiredPeople) {
        this.hiredPeople = hiredPeople;
    }

    public void addPersonell(Person person){ //adds singular person to the assignment
        this.hiredPeople.put(person.getUID(), person);
    }

    public void removePersonell(String personUID){ //Removes person based on UID, overloaded to also take user object
        this.hiredPeople.remove(personUID);
    }
    public void removePersonell(Person person){
        this.removePersonell(person.getUID());
    }
}

