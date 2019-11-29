package sample.util;

public class DbConnection {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/main_office";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "postgres";
    public static final String INSERT_CLIENT = "INSERT INTO client (fio_client,birth_date,number_prav,auto_skill,gender,address,phone,city) values (?,?,?,?,?,?,?,?)";
    //language=SQL
    public static final String SELECT_AUTO = "SELECT * FROM  auto";
    public static final String SELECT_CLIENTS = "SELECT * FROM client";
    public static final String INSERT_AUTO = "INSERT INTO auto (marka,model,color,power,volume,number_auto,speed,drive,body) values (?,?,?,?,?,?,?,?,?)";


//
//    public static Connection getConnection(){
//        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS)){
//            System.out.println("Opened database successfully!!!!!!!");
//        }catch (SQLException e1){
//            System.err.println(e1);
//        }
//
//        return getConnection();
//    }
//
//    public static PreparedStatement getPreparedStatement(){
//        try {PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_CLIENT);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return getPreparedStatement();
//    }
}
