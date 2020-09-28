package account_service.service;

import account_service.connection.CustomConnection;
import account_service.exception.NotEnoughMoneyException;
import account_service.exception.UnknownAccountException;

import java.sql.*;

public class AccountManagement implements AccountService {
    @Override
    public void withdraw(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException {

    }

    @Override
    public void balance(int accountId) throws UnknownAccountException {
        String sql = "SELECT * FROM STUDENTS where id = ?";
        PreparedStatement statement = null;
        try {
            statement = CustomConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String result = resultSet.getString("id")
                        + resultSet.getString("holder")
                        + resultSet.getString("amount");
                System.out.println(result);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException {

    }

    @Override
    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException {

    }
}
