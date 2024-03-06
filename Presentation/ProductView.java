package org.example.Presentation;

import org.example.BusinessLogic.ProductBLL;
import org.example.Dao.ProductDAO;
import org.example.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *The ProductView class represents the user interface for managing products.
 *It allows users to add, edit, and delete products, as well as view the existing products.
 *The class interacts with the ProductController and ProductDAO classes to perform the necessary operations.
 */
public class ProductView extends View {
    private JFrame frame;
    private JPanel productInfoPanel;
    private List<Product> products;
    /**
     *Constructs a new ProductView object.
     *Initializes the list of products and calls the initialize() method to set up the user interface.
     */
    public ProductView() {
        products = new ArrayList<>();
        initialize();
    }
    /**
     * Initializes the user interface components.
     *Creates the main frame, buttons, and product information panel.
     *Sets up event listeners for button actions.
     */
    private void initialize() {
        frame = new JFrame("Product View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addProductButton = new JButton("Add");
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProduct();
            }
        });

        JButton editProductButton = new JButton("Edit");
        JButton deleteProductButton = new JButton("Delete");
        JButton viewProductsButton = new JButton("View");
        viewProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProducts();
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        buttonPanel.add(addProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);
        buttonPanel.add(viewProductsButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        productInfoPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(productInfoPanel);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    /**
     *Retrieves the product details from the input fields and creates a Product object.
     *@param idField The JTextField for entering the product ID.
     *@param nameField The JTextField for entering the product name.
     *@param priceField The JTextField for entering the product price.
     *@param quantityField The JTextField for entering the product quantity.
     *@return The created Product object.
     */
    private Product getProduct(JTextField idField, JTextField nameField, JTextField priceField, JTextField quantityField) {
        String name = nameField.getText();
        int id = Integer.parseInt(idField.getText());
        float price = Float.parseFloat(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        return new Product(id, name, price, quantity);
    }
    /**
     *Displays a dialog to add a new product.
     *Prompts the user to enter product details such as ID, name, price, and quantity.
     *Creates a new Product object and adds it to the list of products.
     */
    private void AddProduct() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Add Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            float price = Float.parseFloat(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = new Product(id, name, price, quantity);
            products.add(product);
            ProductBLL.insertProduct(product);
            displayProducts();
        }
    }
    /**
     *Clears the product information panel and displays the list of products.
     *Retrieves the products from the database using the ProductDAO class.
     *Constructs the product panels and adds them to the product information panel.
     */
    private void displayProducts() {
        List<Product> dbProducts = ProductDAO.getAllProducts();
        List<Object> objectList = new ArrayList<>();
        displayEntities(dbProducts, productInfoPanel,frame, objectList);
//        products.addAll(objectList.stream().map(Product.class::cast).collect(Collectors.toList()));
        products = dbProducts;
    }
    /**
     *Displays a dialog to edit a product.
     *Prompts the user to select a product ID and enter the updated details.
     *Updates the selected product with the new values.
     */
    private void editProduct() {
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No products to edit.");
            return;
        }
        List<Integer> productIDs = new ArrayList<>();
        for (Product product : products) {
            productIDs.add(product.getId());
        }

        JComboBox<Integer> idComboBox = new JComboBox<>(productIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel editPanel = new JPanel(new GridLayout(4, 2));
        editPanel.add(new JLabel("Product ID:"));
        editPanel.add(idComboBox);

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        editPanel.add(new JLabel("Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Price:"));
        editPanel.add(priceField);
        editPanel.add(new JLabel("Quantity:"));
        editPanel.add(quantityField);

        idComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selectedID = (Integer) idComboBox.getSelectedItem();

                Product selectedProduct = null;
                for (Product product : products) {
                    if (product.getId() == selectedID) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    nameField.setText(selectedProduct.getName());
                    priceField.setText(String.valueOf(selectedProduct.getPrice()));
                    quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {
                Product selectedProduct = null;
                for (Product product : products) {
                    if (product.getId() == selectedID) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    Product product = getProduct(new JTextField(String.valueOf(selectedID)), nameField, priceField, quantityField);
                    ProductBLL.editProduct(selectedProduct, product);
                    displayProducts();
                } else {
                    JOptionPane.showMessageDialog(frame, "Product not found.");
                }
            }
            productIDs.clear();
        }
    }
    /**
     *Displays a dialog to delete a product.
     *Prompts the user to select a product ID for deletion.
     *Removes the selected product from the list and database.
     */
    private void deleteProduct() {
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No products to delete.");
            return;
        }
        List<Integer> productIDs = new ArrayList<>();
        for (Product product : products) {
            productIDs.add(product.getId());
        }

        JComboBox<Integer> idComboBox = new JComboBox<>(productIDs.toArray(new Integer[0]));
        idComboBox.setSelectedIndex(-1);

        JPanel deletePanel = new JPanel(new GridLayout(2, 2));
        deletePanel.add(new JLabel("Product ID:"));
        deletePanel.add(idComboBox);


        int result = JOptionPane.showConfirmDialog(null, deletePanel, "Delete Product", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Integer selectedID = (Integer) idComboBox.getSelectedItem();

            if (selectedID != null) {
                // Find the product with the selected ID
                Product selectedProduct = null;
                for (Product product : products) {
                    if (product.getId() == selectedID) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    ProductBLL.deleteProduct(selectedProduct);

                    products.remove(selectedProduct);
                    displayProducts();
                } else {
                    JOptionPane.showMessageDialog(frame, "Product not found.");
                }
            }
        }
    }




}
