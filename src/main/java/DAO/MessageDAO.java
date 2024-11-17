package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {


    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {

            String sqlAccount = "SELECT * FROM Account WHERE account_id = ?;" ;
            PreparedStatement preparedStatementAccount = connection.prepareStatement(sqlAccount);
            preparedStatementAccount.setInt(1, message.getPosted_by());
            ResultSet rs = preparedStatementAccount.executeQuery();
            if (!rs.next() || message.getMessage_text() == "" || message.getMessage_text().length() > 255) {
                return null;
            }

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());            
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_user_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_user_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){ 
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(String messageId) {
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE ? = message_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int number = Integer.parseInt(messageId);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){ 
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessageById(String messageId) {
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE ? = message_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int number = Integer.parseInt(messageId);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){ 
                String deleteSql = "DELETE FROM message WHERE ? = message_id;";
                PreparedStatement preparedStatementDelete = connection.prepareStatement(deleteSql);
                preparedStatementDelete.setInt(1, number);
                preparedStatementDelete.executeUpdate();
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message patchMessageById(String messageId, Message message) {
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE ? = message_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int number = Integer.parseInt(messageId);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next() && message.getMessage_text().length() < 255 && message.getMessage_text() != ""){ 
                //UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
                String patchSql = "UPDATE message SET message_text = ? WHERE ? = message_id;";
                PreparedStatement preparedStatementPatch = connection.prepareStatement(patchSql);
                preparedStatementPatch.setString(1, message.getMessage_text());
                preparedStatementPatch.setInt(2, number);
                preparedStatementPatch.executeUpdate();
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), message.getMessage_text(), rs.getInt("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesParticularUser(String accountId) {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int number = Integer.parseInt(accountId);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){ 
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;

    }

    
}
