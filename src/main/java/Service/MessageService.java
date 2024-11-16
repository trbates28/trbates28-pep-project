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




}