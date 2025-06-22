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

    public Account addAccount(Account account) {
        // List<Account> existingAccounts = accountDAO.getAllAccounts(); //< - doesn'ty exist yet
        // for(Account existingAccount : existingAccounts) {
        //     if(existingAccount.getUsername().equalsIgnoreCase(account.getUsername())) {
        //         //what actuall??
        //         return null;
        //     }
        // }
        return accountDAO.insertAccount(account);
    }
    
}
    
