// 回调  标记  阻塞
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.io.*;

public class Staff implements Runnable{
    private String staffId;
    private String password;
    private boolean isStopped;
    private BlockingQueue<Client> clientBlockingQueue;
    private BlockingQueue<Staff> staffBlockingQueue;
    private InputStream inComing = null;
    private OutputStream outComing = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    private Socket socket;
    private int port;

    public Staff(String staffId, String password, BlockingQueue<Client> clientBlockingQueue, BlockingQueue<Staff> staffBlockingQueue, int port){
        this.staffId = staffId;
        this.password = password;
        this.clientBlockingQueue = clientBlockingQueue;
        this.staffBlockingQueue = staffBlockingQueue;
        this.port = port;
        isStopped = false;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void run(){ // 银行柜员线程池
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            inComing = socket.getInputStream();
            outComing = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outComing);
            objectInputStream = new ObjectInputStream(inComing);
            Client client = clientBlockingQueue.take();
            String task = client.makeService();
            processTask(client, task);
            staffBlockingQueue.put(this);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean isStopped(){
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }

    void processTask(Client client, String task){
//        System.out.println(task);
        switch (task){
            case "openAccount":{
                openAccount(client);
                break;
            }
            case "cancelAccount":{
                cancelAccount(client);
                break;
            }
            case "getBalance":{
                double balance = getBalance(client);
                System.out.println("Your balance is : " + balance);
                break;
            }
            case "withdrawMoney":{
                double money;
                Scanner input = new Scanner(System.in);
                synchronized (Bank.class){
                    System.out.println("Please enter the money you need to withdraw");
                    money = input.nextDouble();
                }
                withdrawMoney(client, money);
                break;
            }
            case "saveMoney":{
                double money;
                Scanner input = new Scanner(System.in);
                synchronized (Bank.class){
                    System.out.println("Please enter the money you need to withdraw");
                    money = input.nextDouble();
                }

                saveMoney(client, money);
                break;
            }
            case "modifyAccount":{
                Scanner input = new Scanner(System.in);
                String userId;
                String password;
                String name;
                String idCard;
                String mobileNumber;
                String gender;
                String birthday;

                synchronized (Bank.class){
                    System.out.print("Please enter the modified userId: ");
                    userId = input.nextLine();
                    System.out.print("Please enter the modified password: ");
                    password = input.nextLine();
                    System.out.print("Please enter the modified name: ");
                    name = input.nextLine();
                    System.out.print("Please enter the modified idCard: ");
                    idCard = input.nextLine();
                    System.out.print("Please enter the modified mobileNumber: ");
                    mobileNumber = input.nextLine();
                    System.out.print("Please enter the modified gender: ");
                    gender = input.nextLine();
                    System.out.print("Please enter the modified birthday: ");
                    birthday = input.nextLine();
                }

                System.out.println();
            }
            default : ;
        }
    }

    public void openAccount(Client client){
        Account account = new Account(client.getUserId(), client.getName(),
                client.getPassword(), client.getIdCard(), client.getMobileNumber(),
                client.getGender(), client.getBirthday(),2000D);
        try {
            objectOutputStream.writeUTF("openAccount");
            objectOutputStream.writeObject(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAccount(Client client){
        try {
            objectOutputStream.writeUTF("cancelAccount"); // 在数据库中删除账户
            objectOutputStream.writeObject(client);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public double getBalance(Client client){
        Account account = null;
        try {
            objectOutputStream.writeUTF("getBalance");
            objectOutputStream.writeObject(client);

            account = (Account) objectInputStream.readObject();
  //          System.out.println("getbalance" + account.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account.getBalance();
    }

    public void withdrawMoney(Client client, double money){
        Account account; // 从服务器端获取账户
        try {
            objectOutputStream.writeUTF("withdrawMoney");
            objectOutputStream.writeObject(client);

            account = (Account) objectInputStream.readObject();


            account.setBalance(account.getBalance()-money);

            objectOutputStream.writeObject(account); // 将账户存回数据库
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveMoney(Client client, double money){
        Account account; // 从服务器端获取账户
        try {
            objectOutputStream.writeUTF("saveMoney");
            objectOutputStream.writeObject(client);

            account = (Account) objectInputStream.readObject();
            account.setBalance(account.getBalance()+money);

            objectOutputStream.writeObject(account); // 将账户存回数据库
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void modifyAccount(Client client){
        Account account; // 从服务器端获取账户
        try {
            objectOutputStream.writeUTF("saveMoney");
            objectOutputStream.writeObject(client);

            account = (Account) objectInputStream.readObject();
//            -> modifyAccount;

            objectOutputStream.writeObject(account); // 将账户存回数据库
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void importXLS(){

    }

    public void exportXLS(){

    }

    public void exportPDF(){

    }
}