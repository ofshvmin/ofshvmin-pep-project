package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;


public class MessageDAO {

//    public List<Account> getAllAccounts(){
//        Connection connection = ConnectionUtil.getConnection();
//        List<Account> accounts = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM Account";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            while(rs.next()){
//                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
//                accounts.add(account);
//            }
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }
//        return accounts;
//    }

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch()); //<-----what is actual method name?  Why does Library app say 'getLong'?
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

/**
 * Whem retrieving a message from the database, all fields will be needed. In that case, a constructor with all
 * fields is needed.
 * @param message_id
 * @param posted_by
 * @param message_text
 * @param time_posted_epoch
 */