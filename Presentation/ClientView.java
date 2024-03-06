package org.example.Presentation;

import org.example.BusinessLogic.ClientBLL;
import org.example.Dao.ClientDAO;
import org.example.Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ClientView class represents the graphical user interface for managing clients.
 */
public class ClientView extends View{
    private JFrame frame;
    private JPanel clientInfoPanel;
    private List<Client> clients;
    /**
     * Constructs a ClientView object and initializes the GUI.
     */
    public ClientView() {
        clients = new ArrayList<>();
        initialize();
    }
    /**
     * Initializes the graphical user interface.
     */
    private void initialize() {
        frame = new JFrame("Client View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        JButton editClientButton = new JButton("Edit Client");
        JButton deleteClientButton = new JButton("Delete Client");
        JButton viewClientsButton = new JButton("View Clients");
        viewClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayClients();
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editClient();
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        buttonPanel.add(addClientButton);
        buttonPanel.add(editClientButton);
        buttonPanel.add(deleteClientButton);
        buttonPanel.add(viewClientsButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        clientInfoPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(clientInfoPanel);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    /**
     * Retrieves a Client object based on the input fields.
     *
     * @param idField      The text field for the client's ID.
     * @param nameField    The text field for the client's name.
     * @param ageField     The text field for the client's age.
     * @param addressField The text field for the client's address.
     * @param emailField   The text field for the client's email.
     * @return The Client object created based on the input fields.
     */
    private Client getClient(JTextField idField, JTextField nameField, JTextField ageField, JTextField addressField, JTextField emailField) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String address = addressField.getText();
        String email = emailField.getText();
        return new Client(name, address, email, id, age);
    }
    /**
     * Displays the list of clients in the clientInfoPanel.
     */
    private void displayClients() {
        clientInfoPanel.removeAll();
        clients.clear();
        List<Client> dbClients = ClientDAO.getAllClients();
        List<Object> objectList = new ArrayList<>();
        View.displayEntities(dbClients, clientInfoPanel,frame, objectList);
        clients.addAll(objectList.stream().map(Client.class::cast).collect(Collectors.toList()));
    }
    /**
     * Adds a new client.
     */
    private void addClient() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();

        JPanel addPanel = new JPanel(new GridLayout(6, 2));
        addPanel.add(new JLabel("ID:"));
        addPanel.add(idField);
        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Age:"));
        addPanel.add(ageField);
        addPanel.add(new JLabel("Address:"));
        addPanel.add(addressField);
        addPanel.add(new JLabel("Email:"));
        addPanel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, addPanel, "Add Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            Client client = getClient(idField, nameField, ageField, addressField, emailField);
            clients.add(client);
            ClientBLL.insertClient(client);
            displayClients();
        }
    }
    /**
     * Edits an existing client.
     */
    private void editClient() {
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No clients to edit.");
            return;
        }

        List<Integer> clientIDs = new ArrayList<>();
        for (Client client : clients) {
            clientIDs.add(client.getId());
        }

        JComboBox<Integer> idComboBox = new JComboBox<>(clientIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        editPanel.add(new JLabel("Client ID:"));
        editPanel.add(idComboBox);

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();

        editPanel.add(new JLabel("Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Age:"));
        editPanel.add(ageField);
        editPanel.add(new JLabel("Address:"));
        editPanel.add(addressField);
        editPanel.add(new JLabel("Email:"));
        editPanel.add(emailField);

        idComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selectedID = (Integer) idComboBox.getSelectedItem();

                // Find the client with the selected ID
                Client selectedClient = null;
                for (Client client : clients) {
                    if (client.getId() == selectedID) {
                        selectedClient = client;
                        break;
                    }
                }

                if (selectedClient != null) {
                    nameField.setText(selectedClient.getName());
                    ageField.setText(String.valueOf(selectedClient.getAge()));
                    addressField.setText(selectedClient.getAddress());
                    emailField.setText(selectedClient.getEmail());
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {
                // Find the client with the selected ID
                Client selectedClient = null;
                for (Client client : clients) {
                    if (client.getId() == selectedID) {
                        selectedClient = client;
                        break;
                    }
                }

                if (selectedClient != null) {
                    // Get the updated details
                    Client client = getClient(new JTextField(String.valueOf(selectedID)), nameField, ageField, addressField, emailField);

                    ClientBLL.editClient(selectedClient, client);
                    displayClients();
                } else {
                    JOptionPane.showMessageDialog(frame, "Client not found.");
                }
            }
        }
    }
    /**
     * Deletes an existing client.
     */
    public void deleteClient() {
        List<Client> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No clients to delete.");
            return;
        }
        List<Integer> clientIDs = new ArrayList<>();
        for (Client client : clients) {
            clientIDs.add(client.getId());
        }

        JComboBox<Integer> idComboBox = new JComboBox<>(clientIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        editPanel.add(new JLabel("Client ID:"));
        editPanel.add(idComboBox);


        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {

                Client selectedClient = null;
                for (Client client : clients) {
                    if (client.getId() == selectedID) {
                        selectedClient = client;
                        break;
                    }
                }

                if (selectedClient != null) {
                    ClientBLL.deleteClient(selectedClient);
                    displayClients();
                } else {
                    JOptionPane.showMessageDialog(frame, "Client not found.");
                }
            }
        }
    }
}

