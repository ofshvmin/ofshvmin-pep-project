package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;


public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account) {
        List<Account> existingAccounts = accountDAO.getAllAccounts();
        for(Account existingAccount : existingAccounts) {
            if(existingAccount.getUsername().equalsIgnoreCase(account.getUsername())) {
                return null;
            }
            if(account.getPassword().length() < 4) {
                return null;
            }
            if(account.getUsername().length() < 1) {
                return null;
            }
        }
        return accountDAO.insertAccount(account);
    }

    public Account login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }
}