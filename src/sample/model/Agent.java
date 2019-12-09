package sample.model;

public class Agent {

    private Integer id;
    private String fio_agent;
    private String gender_agent;
    private String callNumber_agent;
    private String login_agent;
    private String pass_agent;
    private boolean access;

    public Agent(String fio_agent, String gender_agent, String callNumber_agent, String login_agent, String pass_agent,boolean access) {
        this.fio_agent = fio_agent;
        this.gender_agent = gender_agent;
        this.callNumber_agent = callNumber_agent;
        this.login_agent = login_agent;
        this.pass_agent = pass_agent;
        this.access = access;
    }

    public Agent(String fio_agent) {
        this.fio_agent = fio_agent;
    }

    public Agent(Integer id,String login, String password, boolean access) {
        this.id = id;
        this.login_agent = login;
        this.pass_agent = password;
        this.access = access;

    }

    public Agent(String fio_agent, boolean access) {
        this.fio_agent = fio_agent;
        this.access = access;
    }

    public String getFio_agent() {
        return fio_agent;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public void setFio_agent(String fio_agent) {
        this.fio_agent = fio_agent;
    }

    public String getGender_agent() {
        return gender_agent;
    }

    public void setGender_agent(String gender_agent) {
        this.gender_agent = gender_agent;
    }

    public String getCallNumber_agent() {
        return callNumber_agent;
    }

    public void setCallNumber_agent(String callNumber_agent) {
        this.callNumber_agent = callNumber_agent;
    }

    public String getLogin_agent() {
        return login_agent;
    }

    public void setLogin_agent(String login_agent) {
        this.login_agent = login_agent;
    }

    public String getPass_agent() {
        return pass_agent;
    }

    public void setPass_agent(String pass_agent) {
        this.pass_agent = pass_agent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", fio_agent='" + fio_agent + '\'' +
                ", gender_agent='" + gender_agent + '\'' +
                ", callNumber_agent='" + callNumber_agent + '\'' +
                ", login_agent='" + login_agent + '\'' +
                ", pass_agent='" + pass_agent + '\'' +
                ", access=" + access +
                '}';
    }
}
