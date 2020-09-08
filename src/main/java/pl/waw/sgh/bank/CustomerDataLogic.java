package pl.waw.sgh.bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDataLogic extends CustomerData {

    private JFrame mainWindow;

    private Bank bank;

    private Customer currentCust;

    public CustomerDataLogic(JFrame mainWindow, Bank bank) {
        super();
        this.mainWindow = mainWindow;
        this.bank = bank;
        // Adding main Panel to Main Frame
        //this.mainWindow.add(mainCustomerPanel);
        //sendButton.addActionListener(new SendButtonActionListener());
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerIDtextField.getText().trim().isEmpty()) {
                    Customer newCust = bank.newCustomer(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText());
                    customerIDtextField.setText(newCust.getId().toString());
                    currentCust = newCust;
                    JOptionPane.showMessageDialog(null, "Saving the customer: "
                            + firstNameTextField.getText() + " bank: " + bank.toString());
                } else {
                    try {
                        bank.changeCustomerName(firstNameTextField.getText(), lastNameTextField.getText(),
                                emailTextField.getText(),
                                customerIDtextField.getText());
                        JOptionPane.showMessageDialog(null, "Saving new data");
                    } catch (NoCustomerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentCust = new Customer(firstNameTextField.getText(), lastNameTextField.getText(),
                        emailTextField.getText(), Integer.valueOf(customerIDtextField.getText()));
                Customer nextCust = bank.nextCustomer(currentCust);
                if (nextCust!=null) {
                    showCustomer(nextCust);
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentCust = new Customer(firstNameTextField.getText(), lastNameTextField.getText(),
                        emailTextField.getText(), Integer.valueOf(customerIDtextField.getText()));
                Customer prevCust = bank.previousCustomer(currentCust);
                if (prevCust!=null)
                    showCustomer(prevCust);
            }
        });

        newButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                customerIDtextField.setText(" ");
                firstNameTextField.setText(" ");
                lastNameTextField.setText(" ");
                emailTextField.setText(" ");
            }
        });
    }

    private void showCustomer(Customer customer) {
        currentCust = customer;
        firstNameTextField.setText(customer.getFirstName());
        lastNameTextField.setText(customer.getLastName());
        emailTextField.setText(customer.getEmail());
        customerIDtextField.setText(customer.getId().toString());
    }


    static boolean ifStartup = true;
    public JPanel getMainCustomerPanel() throws NoCustomerException {
        if (bank.ifCustexists(0) && ifStartup) {
            customerIDtextField.setText("0");
            firstNameTextField.setText(bank.findCustomerById(0).getFirstName());
            lastNameTextField.setText(bank.findCustomerById(0).getLastName());
            emailTextField.setText(bank.findCustomerById(0).getEmail());
            ifStartup = false;
        }
        return mainCustomerPanel;
    }
}
