package org.example.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.example.BusinessLogic.OrderBLL;
import org.example.Dao.ClientDAO;
import org.example.Dao.OrderDAO;
import org.example.Dao.ProductDAO;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
/**
 The OrderView class represents the user interface for managing orders.
 It provides functionality to add, edit, delete, and display orders.
 */
public class OrderView extends View {
    private JFrame frame;
    private JPanel orderInfoPanel;
    private List<Order> orders;
    private List<Client> clients;
    private List<Product> products;
    /**
     * Constructs an OrderView object.
     * Initializes the lists of orders, clients, and products.
     * Calls initialize() method to set up the user interface.
     */
    public OrderView() {
        orders = new ArrayList<>();
        clients=new ArrayList<>();
        products=new ArrayList<>();
        initialize();
    }
    /**
     * Initializes the user interface components.
     * Sets up the frame, buttons, and panels.
     */
    private void initialize() {
        frame = new JFrame("Order View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addOrderButton = new JButton("Add Order");
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrder();
            }
        });

        JButton editOrderButton = new JButton("Edit Order");
        JButton deleteOrderButton = new JButton("Delete Order");
        JButton viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrders();
            }
        });

        editOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editOrder();
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        buttonPanel.add(addOrderButton);
        buttonPanel.add(editOrderButton);
        buttonPanel.add(deleteOrderButton);
        buttonPanel.add(viewOrdersButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        orderInfoPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(orderInfoPanel);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    /**
     * Creates an Order object based on the input fields.
     *
     * @param idField           the JTextField for the order ID
     * @param customerNameField the JTextField for the customer name
     * @param totalAmountField  the JTextField for the total amount
     * @param productNameField the JTextField for the product name
     * @param productAmountField the JTextField for the product amount
     * @return the created Order object
     */
    private Order getOrder(JTextField idField, JTextField customerNameField, JTextField totalAmountField, JTextField productNameField, JTextField productAmountField) {
        int id = Integer.parseInt(idField.getText());
        String customerName = customerNameField.getText();
        String productName = productNameField.getText();
        float totalAmount = Float.parseFloat(totalAmountField.getText());
        int productAmount = Integer.parseInt(productAmountField.getText());

        return new Order(id, customerName, totalAmount, productName, productAmount);
    }

    /**
     * Displays a dialog to add an order.
     * Prompts the user to enter order details and adds the order to the list.
     */
    private void AddOrder() {
        JTextField idField = new JTextField();
        clients=ClientDAO.getAllClients();
        products=ProductDAO.getAllProducts();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No orders to add.");
            return;
        }
        List<String> clientnames = new ArrayList<>();
        for (Client client : clients) {
            clientnames.add(client.getName());
        }
        List<String> productnames = new ArrayList<>();
        for (Product product : products) {
            productnames.add(product.getName());
        }

        JComboBox<String> customerComboBox = new JComboBox<>(clientnames.toArray(new String[0]));
        JTextField totalAmountField = new JTextField();
        JComboBox<String> productComboBox = new JComboBox<>(productnames.toArray(new String[1]));
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Customer:"));
        panel.add(customerComboBox);
        panel.add(new JLabel("Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Order", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            String customerName = (String) customerComboBox.getSelectedItem();
            Random random=new Random();
            float totalAmount= random.nextFloat();
            String productName = (String) productComboBox.getSelectedItem();
            int productAmount = Integer.parseInt(quantityField.getText());

            Order order = new Order(id, customerName, totalAmount, productName, productAmount);
            if(productAmount<=ProductDAO.getProductByName(productName).getQuantity())
            { orders.add(order);
                OrderBLL.insertOrder(order);
                displayOrders();}
            else
                JOptionPane.showMessageDialog(frame, "Under stock!");
        }
    }
    /**
     * Displays the list of orders in the orderInfoPanel.
     * Retrieves the orders from the database and adds them to the panel.
     */
    private void displayOrders() {
        List<Order> dbOrders = OrderDAO.getAllOrders();
        List<Object> objectList = new ArrayList<>();
        displayEntities(dbOrders, orderInfoPanel,frame, objectList);
        orders = objectList.stream().map(Order.class::cast).collect(Collectors.toList());
    }

    /**
     * Displays a dialog to edit an order.
     * Prompts the user to select an order ID and enter updated details for the order.
     * Updates the order in the list and in the database.
     */
    private void editOrder() {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No orders to edit.");
            return;
        }

        List<Integer> orderIDs = new ArrayList<>();
        for (Order order : orders) {
            orderIDs.add(order.getID());
        }

        JComboBox<Integer> idComboBox = new JComboBox<>(orderIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        editPanel.add(new JLabel("Order ID:"));
        editPanel.add(idComboBox);

        JTextField customerNameField = new JTextField();
        JTextField totalAmountField = new JTextField();
        JTextField productField = new JTextField();
        JTextField quantityField = new JTextField();

        editPanel.add(new JLabel("Customer Name:"));
        editPanel.add(customerNameField);
        editPanel.add(new JLabel("Total Amount:"));
        editPanel.add(totalAmountField);
        editPanel.add(new JLabel("Product:"));
        editPanel.add(productField);
        editPanel.add(new JLabel("Quantity:"));
        editPanel.add(quantityField);

        idComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selectedID = (Integer) idComboBox.getSelectedItem();

                // Find the order with the selected ID
                Order selectedOrder = null;
                for (Order order : orders) {
                    if (order.getID() == selectedID) {
                        selectedOrder = order;
                        break;
                    }
                }

                if (selectedOrder != null) {
                    customerNameField.setText(selectedOrder.getCustomerName());
                    totalAmountField.setText(String.valueOf(selectedOrder.getTotalAmount()));
                    productField.setText(selectedOrder.getProductName());
                    quantityField.setText(String.valueOf(selectedOrder.getProductAmount()));
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Order", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {
                // Find the order with the selected ID
                Order selectedOrder = null;
                for (Order order : orders) {
                    if (order.getID() == selectedID) {
                        selectedOrder = order;
                        break;
                    }
                }

                if (selectedOrder != null) {
                    Order order = getOrder(new JTextField(String.valueOf(selectedID)), customerNameField, totalAmountField, productField, quantityField);
                    int quantityDifference = order.getProductAmount() - selectedOrder.getProductAmount();
                    int remainingProductAmount = ProductDAO.getProductByName(selectedOrder.getProductName()).getQuantity() - quantityDifference;
                    if(remainingProductAmount>=0){
                        OrderBLL.editOrder(selectedOrder, order);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Under stock");
                    }
                    displayOrders();
                } else {
                    JOptionPane.showMessageDialog(frame, "Order not found.");
                }
            }
        }
    }
    /**
     * Deletes an order from the list and the database.
     * Prompts the user to select an order ID to delete.
     */
    public void deleteOrder() {
        List<Order> orders = OrderDAO.getAllOrders();
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No orders to delete.");
            return;
        }

        List<Integer> orderIDs = new ArrayList<>();
        for (Order order : orders) {
            orderIDs.add(order.getID());
        }
        JComboBox<Integer> idComboBox = new JComboBox<>(orderIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel editPanel = new JPanel(new GridLayout(4, 2));
        editPanel.add(new JLabel("Order ID:"));
        editPanel.add(idComboBox);


        int result = JOptionPane.showConfirmDialog(null, editPanel, "Delete Order", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {
                Order selectedOrder = null;
                for (Order order : orders) {
                    if (order.getID() == selectedID) {
                        selectedOrder = order;
                        break;
                    }
                }

                if (selectedOrder != null) {
                    OrderBLL.deleteOrder(selectedOrder);
                    displayOrders();
                } else {
                    JOptionPane.showMessageDialog(frame, "Order not found.");
                }
            }
        }
    }
}


