package org.example.Model;

import java.util.Date;
/**
 * The Order class represents an order made by a customer for a specific product.
 */
public class Order {
    private int id;
    private String customerName;
    private String productName;
    private float totalAmount;
    private int productAmount;
    /**
     * Constructs an Order object with the specified properties.
     *
     * @param id            The ID of the order.
     * @param customerName  The name of the customer placing the order.
     * @param totalAmount   The total amount of the order.
     * @param productName   The name of the product in the order.
     * @param productAmount The quantity of the product in the order.
     */
    public Order(int id, String customerName, float totalAmount, String productName, int productAmount) {
        this.id = id;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.productName = productName;
        this.productAmount = productAmount;
    }
    /**
     * Retrieves the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getID() {
        return id;
    }
    /**
     * Sets the ID of the order.
     *
     * @param id The ID of the order to be set.
     */
    public void setID(int id) {
        this.id = id;
    }
    /**
     * Retrieves the quantity of the product in the order.
     *
     * @return The quantity of the product in the order.
     */
    public int getProductAmount() {
        return productAmount;
    }
    /**
     * Retrieves the name of the customer placing the order.
     *
     * @return The name of the customer placing the order.
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * Retrieves the name of the product in the order.
     *
     * @return The name of the product in the order.
     */
    public String getProductName() {
        return productName;
    }
    /**
     * Sets the name of the customer placing the order.
     *
     * @param customerName The name of the customer to be set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Sets the name of the product in the order.
     *
     * @param productName The name of the product to be set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * Retrieves the total amount of the order.
     *
     * @return The total amount of the order.
     */
    public float getTotalAmount() {
        return totalAmount;
    }
    /**
     * Sets the total amount of the order.
     *
     * @param totalAmount The total amount of the order to be set.
     */
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    /**
     * Sets the quantity of the product in the order.
     *
     * @param productAmount The quantity of the product to be set.
     */
    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }
}


