package Service;
import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addUser(Account user) {
        return this.accountDAO.insertAccount(user);
    }
    public Account loginUser(Account user) {
        return this.accountDAO.loginUser(user);
    }
}
