package sample.model;

public class Dogovor1 {
    private int id;
    private String date_issue;
    private String date_brake;
    private String id_agent;
    private String id_client;
    private String type_insurance;
    private Integer insurance_payment;
    private String branch;
    private String id_auto;
    private Integer pay;


    public Dogovor1() {
    }

    public Dogovor1(int id, String date_issue, String date_brake, String id_agent, String id_client, String type_insurance, Integer insurance_payment, String id_auto, Integer pay) {
        this.id = id;
        this.date_issue = date_issue;
        this.date_brake = date_brake;
        this.id_agent = id_agent;
        this.id_client = id_client;
        this.type_insurance = type_insurance;
        this.insurance_payment = insurance_payment;
        this.id_auto = id_auto;
        this.pay = pay;
        branch = "H";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_issue() {
        return date_issue;
    }

    public void setDate_issue(String date_issue) {
        this.date_issue = date_issue;
    }

    public String getDate_brake() {
        return date_brake;
    }

    public void setDate_brake(String date_brake) {
        this.date_brake = date_brake;
    }

    public String getId_agent() {
        return id_agent;
    }

    public void setId_agent(String id_agent) {
        this.id_agent = id_agent;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getType_insurance() {
        return type_insurance;
    }

    public void setType_insurance(String type_insurance) {
        this.type_insurance = type_insurance;
    }

    public Integer getInsurance_payment() {
        return insurance_payment;
    }

    public void setInsurance_payment(Integer insurance_payment) {
        this.insurance_payment = insurance_payment;
    }

    public String getId_auto() {
        return id_auto;
    }

    public void setId_auto(String id_auto) {
        this.id_auto = id_auto;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }
}
