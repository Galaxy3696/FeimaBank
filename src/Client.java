import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private String userId;
    private String password;
    private String name;
    private String idCard;
    private String mobileNumber;
    private String gender;
    private String birthday;
    private boolean isTreated;

    public Client(String userId, String password, String name , String idCard, String mobileNumber, String gender, String birthday){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.idCard = idCard;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.birthday = birthday;
        isTreated = false;
    }

    public String makeService(){
        System.out.println("Please choose the service you need:");
        System.out.println("1.openAccount");
        System.out.println("2.cancelAccount");
        System.out.println("3.getBalance");
        System.out.println("4.withdrawMoney");
        System.out.println("5.saveMoney");
        System.out.println("6.modifyAccount");

        Scanner input = new Scanner(System.in);
        int option;
        System.out.print("Please entry you choice : ");
        synchronized (Bank.class){
            option = input.nextInt();
        }
        String taskType = null;
        switch (option){
            case 1:{ taskType = "openAccount"; break; }
            case 2:{ taskType = "cancelAccount"; break; }
            case 3:{ taskType = "getBalance"; break; }
            case 4:{ taskType = "withdrawMoney"; break; }
            case 5:{ taskType = "saveMoney"; break; }
            case 6:{ taskType = "modifyAccount"; break; }
            default : taskType = "null";
        }
        return taskType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }
}