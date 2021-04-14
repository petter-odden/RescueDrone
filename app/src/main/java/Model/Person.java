package Model;

public class Person {
    private String name;
    private String UID;
    private String username;
    private boolean confirmedPilot;

    public Person(String name, String UID, String username) {
        this.name = name;
        this.UID = UID;
        this.username = username;
        this.confirmedPilot = false;
    }

    public boolean isConfirmedPilot() {
        return confirmedPilot;
    }

    public void setConfirmedPilot(boolean confirmedPilot) {
        this.confirmedPilot = confirmedPilot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
