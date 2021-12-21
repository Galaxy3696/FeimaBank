import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://121.37.171.132:3306/dd?useSSL=false";
    public static final String USERNAME = "abab";
    public static final String PASSWORD = "123456";
    public static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        Bank fmBank = new Bank(2);
        fmBank.initial();
        Thread fmBankThread = new Thread(fmBank);
        fmBankThread.start();
        for(int i = 1 ; i <= 10 ; ++i){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fmBank.staffRegisterClient();
        }
        fmBank.close();
        connection.close();
    }

    public static void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("connection : " + connection);
        String sql = "CREATE TABLE if not exists Account"
                + "(userId VARCHAR(20), "
                + " name VARCHAR(20), "
                + " password VARCHAR(20), "
                + " idCard VARCHAR(20), "
                + " mobileNumber VARCHAR(20), "
                + " gender VARCHAR(20), "
                + " birthday VARCHAR(20), "
                + " balance DOUBLE, "
                + "PRIMARY KEY ( userId ))";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }
}