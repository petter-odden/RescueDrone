package Model;

import com.google.maps.model.LatLng;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class Mission {
    private static int increment = 0;
    private int missionID;
    private String title;
    private LatLng location;
    private String description;
    private String uid;
    private User creatorUser;
    private Date creationTime;

    private boolean lookingForAllPeople;
    private boolean lookingForQualifiedPeople;

    private boolean hideMission;

    private int priorityLevel;

    private HashMap<String, User> hiredPeople;



    public Mission(String title, LatLng location, String description, String uid, User creatorUser, Date creationTime, boolean lookingForAllPeople, boolean lookingForQualifiedPeople, boolean hideMission, int priorityLevel) {
        this.missionID = increment++;
        this.title = title;
        this.location = location;
        this.description = description;
        this.uid = uid;
        this.creatorUser = creatorUser;
        this.creationTime = creationTime;
        this.lookingForAllPeople = lookingForAllPeople;
        this.lookingForQualifiedPeople = lookingForQualifiedPeople;
        this.hideMission = hideMission;

        this.hiredPeople = new HashMap<>();

        //PriorityLevel is always set to the lowest level if the user isn't an emergencyPersonell
        if (creatorUser instanceof EmergencyPersonnel){
            this.priorityLevel = priorityLevel;
        }
        else{
            this.priorityLevel = 3;
        }
    }

    public Mission(String uid, User creatorUser, Date creationTime, boolean lookingForAllPeople, boolean lookingForQualifiedPeople, boolean hideMission) {
        this.uid = uid;
        this.creatorUser = creatorUser;
        this.creationTime = creationTime;
        this.lookingForAllPeople = lookingForAllPeople;
        this.lookingForQualifiedPeople = lookingForQualifiedPeople;
        this.hiredPeople = new HashMap<>();
        this.priorityLevel  = 0;
        this.hideMission = hideMission;
    }

    public Mission() {
    }

    public String getTitle() {
        return title;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLookingForAllPeople() {
        return lookingForAllPeople;
    }

    public void setLookingForAllPeople(boolean lookingForAllPeople) {
        this.lookingForAllPeople = lookingForAllPeople;
    }

    public boolean isLookingForQualifiedPeople() {
        return lookingForQualifiedPeople;
    }

    public void setLookingForQualifiedPeople(boolean lookingForQualifiedPeople) {
        this.lookingForQualifiedPeople = lookingForQualifiedPeople;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String UID) {
        this.uid = uid;
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isHideMission() {
        return hideMission;
    }

    public void setHideMission(boolean hideMission) {
        this.hideMission = hideMission;
    }

    public HashMap<String, User> getHiredPeople() {
        return hiredPeople;
    }

    public void setHiredPeople(HashMap<String, User> hiredPeople) {
        this.hiredPeople = hiredPeople;
    }

    public void addPersonell(User user, String uid){ //adds singular person to the mission
        this.hiredPeople.put(uid, user);
    }

    public void removePersonell(String personUid){ //Removes person based on UID, overloaded to also take user object
        this.hiredPeople.remove(personUid);
    }

    public int getMissionID() {
        return missionID;
    }
}

