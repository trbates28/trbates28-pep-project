package Service;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message) {
        return this.messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(String message_id) {
        return this.messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(String message_id) {
        return this.messageDAO.deleteMessageById(message_id);
    }

    public Message patchMessageById(String message_id, Message message) {
        return this.messageDAO.patchMessageById(message_id, message);
    }

    public List<Message> getAllMessagesParticularUser(String accountId) {
        return this.messageDAO.getAllMessagesParticularUser(accountId);
    }


}