package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();

    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public Message addMessage(Message message) {
        //text cannot be blank
        if (message.getMessage_text().trim().length() < 1) {
            return null;
        }

        //must be under 255 chars
        if (message.getMessage_text().length() > 255) {
            return null;
        }

        List<Account> validAccounts = accountDAO.getAllAccounts();
        for (Account validAccount : validAccounts)
            if (validAccount.getAccount_id() == message.getPosted_by()) {
                return messageDAO.insertMessage(message);

            }
        return null;
    }


}


