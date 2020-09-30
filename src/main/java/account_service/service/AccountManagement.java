package account_service.service;

import account_service.connection.CustomConnection;
import account_service.exception.NotEnoughMoneyException;
import account_service.exception.UnknownAccountException;

import java.sql.*;

import static java.lang.String.format;

public class AccountManagement implements AccountService {
    private final Connection connection = CustomConnection.getConnection();

    @Override
    public void withdraw(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        String sql = "SELECT amount FROM students WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            int currentAmount;
            if (!resultSet.next()) {
                throw new UnknownAccountException("Нет аккаунта с таким id");
            }
            currentAmount = resultSet.getInt("amount");
            if (currentAmount < amount) {
                throw new NotEnoughMoneyException("Не хватает средств");
            }
            currentAmount -= amount;
            sql = "UPDATE students SET amount = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, currentAmount);
            statement.setInt(2, accountId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void balance(int accountId) throws UnknownAccountException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new UnknownAccountException("Нет аккаунта с таким id");
            }
            System.out.println(format("%d %s %d",
                    resultSet.getInt("id"),
                    resultSet.getString("holder"),
                    resultSet.getInt("amount")));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws UnknownAccountException {
        String sql = "SELECT amount FROM students WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            int currentAmount;
            if (!resultSet.next()) {
                throw new UnknownAccountException("Нет аккаунта с таким id");
            }
            currentAmount = resultSet.getInt("amount");
            currentAmount += amount;
            sql = "UPDATE students SET amount = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, currentAmount);
            statement.setInt(2, accountId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public void manage(String operation) {
        String[] info = operation.split(" ");
        try {
            switch (info[0]) {
                case "balance":
                    try {
                        balance(Integer.parseInt(info[1]));
                    } catch (UnknownAccountException e) {
                        e.printStackTrace();
                    }
                    break;
                case "withdraw":
                    try {
                        withdraw(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } catch (UnknownAccountException | NotEnoughMoneyException e) {
                        e.printStackTrace();
                    }
                    break;
                case "deposite":
                    try {
                        deposit(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } catch (UnknownAccountException e) {
                        e.printStackTrace();
                    }
                    break;
                case "transfer":
                    try {
                        transfer(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]));
                    } catch (UnknownAccountException | NotEnoughMoneyException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Operation not supported");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
