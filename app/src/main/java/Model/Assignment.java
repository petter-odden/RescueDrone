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

    private int priorityLevel;

    private HashMap<String, Person> hiredPeople;

    public Assignment(String UID, Person creatorPerson, LocalDate creationTime, boolean lookingForPeople, boolean hideAssignment, int priorityLevel) {
        this.UID = UID;
        this.creatorPerson = creatorPerson;
        this.creationTime = creationTime;
        this.lookingForPeople = lookingForPeople;
        this.hideAssignment = hideAssignment;

        this.hiredPeople = new HashMap<>();

        //PriorityLevel is always set to the lowest level if the user isn't an emergencyPersonell
        if (creatorPerson instanceof EmergencyPersonnel){
            this.priorityLevel = priorityLevel;
        }
        else{
            this.priorityLevel = 3;
        }
    }

    public Assignment(String UID, Person creatorPerson, LocalDate creationTime, boolean lookingForPeople, boolean hideAssignment) {
        this.UID = UID;
        this.creatorPerson = creatorPerson;
        this.creationTime = creationTime;
        this.lookingForPeople = lookingForPeople;

        this.hiredPeople = new HashMap<>();
        this.priorityLevel  = 0;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Person getCreatorPerson() {
        return creatorPerson;
    }

    public void setCreatorPerson(Person creatorPerson) {
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

