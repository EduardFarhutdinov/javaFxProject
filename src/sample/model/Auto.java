package sample.model;

public class Auto {
    private int id;
    private String marka;
    private String model;
    private String color;
    private String power;
    private String volume;
    private String number;
    private String speed;
    private String drive;
    private String body;

    public Auto(String marka) {
        this.marka = marka;
    }

    public Auto(int id, String marka, String model, String number) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.number = number;
    }

    public Auto(int id, String marka, String model, String color, String power, String volume, String number, String speed, String drive, String body) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.color = color;
        this.power = power;
        this.volume = volume;
        this.number = number;
        this.speed = speed;
        this.drive = drive;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
