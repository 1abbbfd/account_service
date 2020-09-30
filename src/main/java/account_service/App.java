package account_service;

import account_service.connection.CustomConnection;
import account_service.service.AccountManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws SQLException {
        AccountManagement accountManagement = new AccountManagement();
        System.out.println("Для выхода введите: exit");
        Scanner scanner = new Scanner(System.in);
        String line;
        while (!(line = scanner.nextLine()).equals("exit")) {
            accountManagement.manage(line);
        }
        CustomConnection.getConnection().close();
    }
}
