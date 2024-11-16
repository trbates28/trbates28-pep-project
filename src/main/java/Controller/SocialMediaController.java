package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerNewUserHandler);
        app.post("/login", this::loginVerifyHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("localhost:8080/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("localhost:8080/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("localhost:8080/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("localhost:8080/accounts/{account_id}/messages", this::getAllMessagesParticularUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void registerNewUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addUser(account);
         
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        } 
    }

    private void loginVerifyHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.loginUser(account);
         
        if(addedAccount==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        } 

    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.addMessage(message);
        if(newMessage!=null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        } 
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
            ctx.json(messages);
    }
    private void getMessageByIdHandler(Context ctx) {

    }
    private void deleteMessageByIdHandler(Context ctx) {

    }
    private void patchMessageByIdHandler(Context ctx) {

    }
    private void getAllMessagesParticularUserHandler(Context ctx) throws JsonProcessingException {

    }



}