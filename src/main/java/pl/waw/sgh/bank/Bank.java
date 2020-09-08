package pl.waw.sgh.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bank implements Serializable {

    public List<Customer> customerList = new ArrayList<>();

    private List<Account> accountList = new ArrayList<>();

    public Customer nextCustomer(Customer curCustomer) {
        int curCustIdx = curCustomer.getId();
        if ((curCustIdx >= 0) && (curCustIdx < customerList.size()-1)) {
            return customerList.get(curCustIdx+1);
        } else {
            return null;
        }
    }

    public Customer previousCustomer(Customer curCustomer) {
        int curCustIdx = curCustomer.getId();
        if (curCustIdx > 0) {
            return customerList.get(curCustIdx-1);
        } else {
            return null;
        }
    }


    public Customer newCustomer(String firstName, String lastName, String email) {
        Customer c = new Customer(firstName, lastName, email, customerList.size());
        customerList.add(c);
        return c;
    }

    public Account newAccount(boolean isDebitAccount, String currency, Customer customer) {
        Account a;
        if (isDebitAccount) {
            a = new DebitAccount(currency, customer);
        } else {
            a = new SavingsAccount(currency, customer);
        }
        accountList.add(a);
        return a;
    }

    public Account newDebitAccount(String currency, Customer customer) {
        return newAccount(true, currency, customer);
    }

    public Account newSavingsAccount(String currency, Customer customer) {
        return newAccount(false, currency, customer);
    }

    public boolean ifCustexists(Integer custId) {
        System.out.println(customerList);
        if (customerList.size() <= custId) {
            return false;
        }
        return true;
    }

    public Customer findCustomerById(Integer custId) throws NoCustomerException {
        for (Customer c : customerList) {
            if (ifCustexists(custId)) {
                return c;
            }
        } throw new NoCustomerException(custId);
    }

    public String changeCustomerName(String firstName, String lastName, String email, String custId) throws NoCustomerException {
        Integer custid = Integer.valueOf(custId);
        Customer c = findCustomerById(custid);
        c = new Customer(firstName, lastName, email, custid);
        customerList.set(custid, c);
        return c.getFirstName();
    }

    public Account findAccountById(Integer accId) throws AccountNotFoundException {
        //TODO: May be needed for transfer...
        // Loop through list of accounts and if accId matches then return that account
        for (Account acc : accountList) {
            if (accId.equals(acc.getId()))
                return acc;
        }
        return null;
        //throw new AccountNotFoundException("Account with ID: " + accId + " not found!", accId);
        //return null;
    }

    public void transfer(Integer fromAccountId, Integer toAccountId, Double amount)
            throws AccountNotFoundException, NotEnoughMoneyException {
        //TODO: Find relevant account objects given their Ids and perform actions to transfer
        // Check if account exists etc.
        Account fromAccount = findAccountById(fromAccountId);
        Account toAccount = findAccountById(toAccountId);
        fromAccount.charge(amount);
        toAccount.deposit(amount);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "\tcusts:\n" + customerList +
                "\n\taccounts:\n" + accountList +
                '}';
    }
}
