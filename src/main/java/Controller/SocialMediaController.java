package Controller;

import Model.Account;
import Service.AccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

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

    AccountService accountService;


    public SocialMediaController() {
        this.accountService = new AccountService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {

        String body = context.body();
        System.out.println("Incoming request body: " + body);

        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(body, Account.class);
        System.out.println("Parsed Account object: " + account);
        
        Account addedAccount = accountService.addAccount(account);

        if(addedAccount != null) {
            context.status(200).json(addedAccount);
        } else {
            context.status(400);
        }

        // context.json("sample text");
    }


}