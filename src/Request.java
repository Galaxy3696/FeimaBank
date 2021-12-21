import java.io.Serializable;

public class Request implements Serializable {
    private String request = null;
    private Account account = null;
    private Client client = null;

    public Request(String request){
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
