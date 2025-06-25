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
        if (message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        //must be under 255 chars
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        //must be posted by a valid existing account
        List<Account> validAccounts = accountDAO.getAllAccounts();
        for (Account validAccount : validAccounts)
            if (validAccount.getAccount_id() == message.getPosted_by()) {
                return messageDAO.insertMessage(message);
            }
        return null;
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, Message message) {
        //text cannot be blank
        if (message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        //must be under 255 chars
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        //find & update the existing message
        List<Message> existingMessages = messageDAO.getAllMessages();
        for(Message existingMessage : existingMessages) {
            if(existingMessage.getMessage_id() == message_id) {
                message.message_id = existingMessage.getMessage_id();
                message.posted_by = existingMessage.getPosted_by();
                message.time_posted_epoch = existingMessage.getTime_posted_epoch();

                return messageDAO.updateMessageById(message_id, message);
            }
        }
        return null;
    }

    public List<Message> getAllMessagesByAccount(int account_id) {
        return messageDAO.getAllMessagesByAccount(account_id);
    }
}