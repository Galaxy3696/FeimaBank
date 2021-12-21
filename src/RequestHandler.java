import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{
    private Socket socket;
    private String command = null;
    private Account account = null;
    private Client client = null;

    public RequestHandler(Socket socket){
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
//                    -> save into database
                    break;
                }
                case "cancelAccount":{
                    client = (Client) objectInputStream.readObject();
//                    -> remove from database
                    break;
                }

                case "getBalance":{
                    client = (Client) objectInputStream.readObject();
//                    account = -> get from database
                    objectOutputStream.writeObject(account);
                    break;
                }

                case "withdrawMoney":{
                    client = (Client) objectInputStream.readObject();
//                    account = -> get from database
                    objectOutputStream.writeObject(account);

                    account = (Account) objectInputStream.readObject();
//                  ->  save into database
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
}