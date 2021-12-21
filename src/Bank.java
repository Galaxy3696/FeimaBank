import java.util.*;
import java.util.concurrent.*;

public class Bank implements Runnable {
    private int staffNumber;
    private Staff[] staffs;
    private ExecutorService pool;
    private BlockingQueue<Client> clientQueue; // 客户阻塞队列，柜员从队列中接待走客户
    private BlockingQueue<Staff> staffQueue; // 柜员阻塞队列，从队列中取走空闲的柜员执行任务
    private boolean isClosed;

    public Bank(int staffNumber){ // 初始银行柜台人员的数量（每个柜台人员是一个线程）
        this.staffNumber = staffNumber;
        staffs = new Staff[staffNumber];
        pool = Executors.newFixedThreadPool(staffNumber);
        clientQueue = new ArrayBlockingQueue<Client>(staffNumber);
        staffQueue = new ArrayBlockingQueue<Staff>(staffNumber);
        isClosed = false;
    }

    @Override
    public void run(){
        while(!isClosed()){
            try {
                pool.execute(staffQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initial(){ // 银行上班
        Scanner input = new Scanner(System.in);
        String staffID;
        String staffPassword;

        for(int i = 0 ; i < staffNumber ; ++i){ // 实例化柜员对象
            System.out.print("Please enter staff " + i + "'s ID : ");
            synchronized (Bank.class){
                staffID = input.nextLine();
            }
            System.out.print("Please enter staff " + i + "'s password : ");
            synchronized (Bank.class){
                staffPassword = input.nextLine();
            }
            staffs[i] = new Staff(staffID, staffPassword, clientQueue, staffQueue, 9100);
            try {
                staffQueue.put(staffs[i]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        try{
            while(!isClosed()){
                pool.execute(staffQueue.take());
            }
        }   catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed(){
        return this.isClosed;
    }

    public void staffRegisterClient(){ // 招待并登记客户信息
        Scanner input = new Scanner(System.in);
        String userId;
        String password;
        String name;
        String idCard;
        String mobileNumber;
        String gender;
        String birthday;

        synchronized (Bank.class){
            System.out.println("A client comes in.");
            System.out.println("Please register your information");
            System.out.print("userId: ");
            userId = input.nextLine();
            System.out.print("password: ");
            password = input.nextLine();
            System.out.print("name: ");
            name = input.nextLine();
            System.out.print("idCard: ");
            idCard = input.nextLine();
            System.out.print("mobileNumber: ");
            mobileNumber = input.nextLine();
            System.out.print("gender: ");
            gender = input.nextLine();
            System.out.print("birthday: ");
            birthday = input.nextLine();
        }

        try {
            clientQueue.put(new Client(userId, password, name, idCard, mobileNumber, gender, birthday));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(){ // 中断所有进程
        this.isClosed = true;
    }
}