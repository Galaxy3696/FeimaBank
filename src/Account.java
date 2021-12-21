import java.io.Serializable;

public class Account implements Serializable {
    private String userId;
    private String name ;
    private String password;
    private String idCard;
    private String mobileNumber;
    private String gender;
    private String birthday;
    private double balance;

    public Account(String userId, String name, String password, String idCard, String mobileNumber, String gender, String birthday, double balance){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.idCard = idCard;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.balance = balance;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString(){
         return "userId: " + userId + "\n"
                 + "name: " + name + "\n"
                 + "password: " + password + "\n"
                 + "idCard: " + idCard + "\n"
                 + "mobileNumber: " + mobileNumber + "\n"
                 + "gender: " + gender + "\n"
                 + "birthday: " + birthday + "\n"
                 + "balance: " + balance;
    }
}