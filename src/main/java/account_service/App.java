package account_service;

import account_service.exception.UnknownAccountException;
import account_service.service.AccountManagement;

public class App {

    public static void main(String[] args) throws UnknownAccountException {
        AccountManagement accountManagement = new AccountManagement();
        accountManagement.balance(1);
    }
}
