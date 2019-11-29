package sample.model;

public class Dogovor {
    private int id;
    private String date_issue;
    private String date_brake;
    private String id_agent;
    private int id_client;
    private String type_insurance;
    private String insurance_payment;
    private int id_auto;
    private String pay;


    public Dogovor(String id_agent) {
        this.id_agent = id_agent;
    }

    public Dogovor(int id, String date_issue, String date_brake, String id_agent, int id_client, String type_insurance, String insurance_payment, int id_auto, String pay) {
        this.id = id;
        this.date_issue = date_issue;
        this.date_brake = date_brake;
        this.id_agent = id_agent;
        this.id_client = id_client;
        this.type_insurance = type_insurance;
        this.insurance_payment = insurance_payment;
        this.id_auto = id_auto;
        this.pay = pay;
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

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getType_insurance() {
        return type_insurance;
    }

    public void setType_insurance(String type_insurance) {
        this.type_insurance = type_insurance;
    }

    public String getInsurance_payment() {
        return insurance_payment;
    }

    public void setInsurance_payment(String insurance_payment) {
        this.insurance_payment = insurance_payment;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }



//    private int id ;
//    private String fio_client;
//    private String date_issue;
//    private String date_brake;
//    private String type_insurance;
//    private String marka;
//    private String model;
//    private String payment;
//    private String pay;
//    private String fio_agent;
//
//    public Dogovor(int id, String fio_client, String date_issue, String date_brake, String type_insurance, String marka, String model, String payment, String pay, String fio_agent) {
//        this.id = id;
//        this.fio_client = fio_client;
//        this.date_issue = date_issue;
//        this.date_brake = date_brake;
//        this.type_insurance = type_insurance;
//        this.marka = marka;
//        this.model = model;
//        this.payment = payment;
//        this.pay = pay;
//        this.fio_agent = fio_agent;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFio_client() {
//        return fio_client;
//    }
//
//    public void setFio_client(String fio_client) {
//        this.fio_client = fio_client;
//    }
//
//    public String getDate_issue() {
//        return date_issue;
//    }
//
//    public void setDate_issue(String date_issue) {
//        this.date_issue = date_issue;
//    }
//
//    public String getDate_brake() {
//        return date_brake;
//    }
//
//    public void setDate_brake(String date_brake) {
//        this.date_brake = date_brake;
//    }
//
//    public String getType_insurance() {
//        return type_insurance;
//    }
//
//    public void setType_insurance(String type_insurance) {
//        this.type_insurance = type_insurance;
//    }
//
//    public String getMarka() {
//        return marka;
//    }
//
//    public void setMarka(String marka) {
//        this.marka = marka;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public String getPayment() {
//        return payment;
//    }
//
//    public void setPayment(String payment) {
//        this.payment = payment;
//    }
//
//    public String getPay() {
//        return pay;
//    }
//
//    public void setPay(String pay) {
//        this.pay = pay;
//    }
//
//    public String getFio_agent() {
//        return fio_agent;
//    }
//
//    public void setFio_agent(String fio_agent) {
//        this.fio_agent = fio_agent;
//    }
}
