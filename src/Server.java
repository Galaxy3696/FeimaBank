import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Server {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://121.37.171.132:3306/dd?useSSL=false";
    public static final String USERNAME = "abab";
    public static final String PASSWORD = "123456";
    public static Connection connection = null;

    public static void main(String[] args){
        try(ServerSocket server = new ServerSocket(9100);) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ExecutorService pool = Executors.newFixedThreadPool(10);
            System.out.println("Server is ready.");
            while(true){
                Socket socket = server.accept();
                Runnable threadHandler = new ThreadHandler(socket);
                pool.execute(threadHandler);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}