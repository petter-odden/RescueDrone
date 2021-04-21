package Model;

public class Drone {
    private String UID;
    private String name;

    private double weight;

    private double height;
    private double width;
    private double length;

    private String payloads; //Supports multiple payload descriptions, separated by #

    public Drone(String UID, String name, double weight, String payloads) {
        this.UID = UID;
        this.name = name;
        this.weight = weight;
        this.payloads = payloads;
    }

    public Drone(String UID, String name, double weight, double height, double width, double length, String payloads) {
        this.UID = UID;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.payloads = payloads;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getPayloads() {
        return payloads;
    }

    public void setPayloads(String payloads) {
        this.payloads = payloads;
    }
}
