import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadHandler implements Runnable{
    private Socket socket;
    private String command = null;
    private Account account = null;
    private Client client = null;

    public ThreadHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            command = objectInputStream.readUTF();
            System.out.println("Server: ");
            System.out.println(command);
            switch (command){
                case "openAccount":{
                    account = (Account) objectInputStream.readObject();
                    System.out.println(account.toString());
                    saveAccountToDatabase(account);
                    break;
                }
                case "cancelAccount":{
                    client = (Client) objectInputStream.readObject();
//                    -> remove from database
                    break;
                }

                case "getBalance":{
                    client = (Client) objectInputStream.readObject();
                    account = getAccountFromDatabase(client);
//                    System.out.println("Server getbalance" + account.toString());

                    objectOutputStream.writeObject(account);
                    break;
                }

                case "withdrawMoney":{
                    client = (Client) objectInputStream.readObject();
                    account = getAccountFromDatabase(client);
                    objectOutputStream.writeObject(account);

                    account = (Account) objectInputStream.readObject();
                    saveAccountToDatabase(account);
                }

                case "saveMoney":{
                    client = (Client) objectInputStream.readObject();
//                    account = -> get from database
                    objectOutputStream.writeObject(account);

                    account = (Account) objectInputStream.readObject();
//                  ->  save into database
                }

                case "modifyAccount":{
                    client = (Client) objectInputStream.readObject();
//                    account = -> get from database
                    objectOutputStream.writeObject(account);

                    account = (Account) objectInputStream.readObject();
//                  ->  save into database
                }
                default : ;
            }

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAccountToDatabase(Account account){
        String userId = account.getUserId();
        String name = account.getName();
        String password = account.getPassword();
        String idCard = account.getIdCard();
        String mobileNumber = account.getMobileNumber();
        String gender = account.getGender();
        String birthday = account.getBirthday();
        double balance = account.getBalance();
//        System.out.println(userId + " " + name);
        String sql = "INSERT\n" +
                "INTO Account (userId, Name, password, idCard, mobileNumber, gender, birthday, balance)\n" +
                "VALUES ('" + userId + "', '"+name +"', '" + password + "',' " +
                idCard + "', '" + mobileNumber + "','" +
                gender + "','" + birthday + "'," +balance+ ")";
//        System.out.println(sql);

        try (Statement statement = Server.connection.createStatement()){
            int x=statement.executeUpdate(sql);
            System.out.println(x);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccountFromDatabase(Client client){
        String userId = null;
        String name = null;
        String password = null;
        String idCard = null;
        String mobileNumber = null;
        String gender = null;
        String birthday = null;
        double balance = 0;

        String sql = "SELECT * from Account where userId = '" + client.getUserId() +"'";
//        System.out.println(sql);

        try(Statement statement = Server.connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery(sql)){
                while(resultSet.next()){
                    userId = resultSet.getString("userid");
                    name = resultSet.getString("name");
                    password = resultSet.getString("password");
                    idCard = resultSet.getString("idCard");
                    mobileNumber = resultSet.getString("mobileNumber");
                    gender = resultSet.getString("gender");
                    birthday = resultSet.getString("birthday");
                    balance = resultSet.getDouble("balance");
                }
//                System.out.println("getbanlce" + userId + " " + name);
                this.account = new Account(userId, name, password, idCard, mobileNumber, gender, birthday, balance);
//                System.out.println("Server GEt :"  + this.account.toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return this.account;
    }

}