package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;

import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLOutput;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private static final ObjectMapper mapper = new ObjectMapper();

    AccountService accountService;
    MessageService messageService;


    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        String body = context.body();
        Account account = mapper.readValue(body, Account.class);

        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null) {
            context.status(200).json(addedAccount);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        String body = context.body();
        Account loginAttempt = mapper.readValue(body, Account.class);

        Account loggedIn = accountService.login(loginAttempt.getUsername(), loginAttempt.getPassword());
        if(loggedIn != null) {
            context.status(200).json(loggedIn);
        } else {
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException {
        String body = context.body();
        Message message = mapper.readValue(body, Message.class);

        Message addedMessage = messageService.addMessage(message);
        if(addedMessage !=null) {
            context.status(200).json(addedMessage);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));

        Message messageById = messageService.getMessageById(message_id);
        if(messageById == null) {
            context.status(200);
        } else {
        context.status(200).json(messageById);
        }
    }

    private void deleteMessageByIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));

        Message deletedMessage = messageService.deleteMessageById(message_id);
        if(deletedMessage == null) {
            context.status(200);
        } else {
            context.status(200).json(deletedMessage);
        }
    }

    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        String body = context.body();
        Message message = mapper.readValue(body, Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));

        Message updatedMessage = messageService.updateMessageById(message_id, message);
        if(updatedMessage == null) {
            context.status(400);
        } else {
            context.status(200).json(updatedMessage);
        }
    }
}