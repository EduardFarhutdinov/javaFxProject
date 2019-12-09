package sample.model;

public class Client {
    private int id_clienta;
    private String fio_clienta;
    private String birth_date;
    private String number_prav;
    private String auto_skill;
    private String gender;
    private String address;
    private String phone;
    private String city;

    public Client() {
    }

    public Client(String fio_clienta) {
        this.fio_clienta = fio_clienta;
    }

    public Client(int id_clienta, String fio_clienta, String birth_date, String number_prav, String gender) {
        this.id_clienta = id_clienta;
        this.fio_clienta = fio_clienta;
        this.birth_date = birth_date;
        this.number_prav = number_prav;
        this.gender = gender;
    }

    public Client(int id_clienta, String fio_clienta, String birth_date, String number_prav, String auto_skill, String gender, String address, String phone, String city) {
        this.id_clienta = id_clienta;
        this.fio_clienta = fio_clienta;
        this.birth_date = birth_date;
        this.number_prav = number_prav;
        this.auto_skill = auto_skill;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.city = city;
    }

    public int getId_clienta() {
        return id_clienta;
    }

    public void setId_clienta(int id_clienta) {
        this.id_clienta = id_clienta;
    }

    public String getFio_clienta() {
        return fio_clienta;
    }

    public void setFio_clienta(String fio_clienta) {
        this.fio_clienta = fio_clienta;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getNumber_prav() {
        return number_prav;
    }

    public void setNumber_prav(String number_prav) {
        this.number_prav = number_prav;
    }

    public String getAuto_skill() {
        return auto_skill;
    }

    public void setAuto_skill(String auto_skill) {
        this.auto_skill = auto_skill;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_clienta=" + id_clienta +
                ", fio_clienta='" + fio_clienta + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", number_prav='" + number_prav + '\'' +
                ", auto_skill='" + auto_skill + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
