package Model;

public class Intel {
    private double droneLat;
    private double droneLng;
    private int missionID;

    public Intel() {}

    public Intel(double droneLat, double droneLng, int missionID) {
        this.droneLat = droneLat;
        this.droneLng = droneLng;
        this.missionID = missionID;
    }

    public double getDroneLat() {
        return droneLat;
    }

    public void setDroneLat(double droneLat) {
        this.droneLat = droneLat;
    }

    public double getDroneLng() {
        return droneLng;
    }

    public void setDroneLng(double droneLng) {
        this.droneLng = droneLng;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }
}
