package org.example.Model;

/**
 * The Product class represents a product with its associated properties.
 */

public class Product {
    String name;
    int id;
    float price;
    int quantity;
    /**
     * Constructs a Product object with the specified properties.
     *
     * @param productID The ID of the product.
     * @param name      The name of the product.
     * @param price     The price of the product.
     * @param quantity  The quantity of the product.
     */
    public Product(int productID, String name, float price, int quantity) {
        this.id = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    /**
     * Retrieves the ID of the product.
     *
     * @return The ID of the product.
     */
    public int getId() {
        return id;
    }
    /**
     * Retrieves the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }
    /**
     * Retrieves the price of the product.
     *
     * @return The price of the product.
     */
    public float getPrice() {
        return price;
    }
    /**
     * Retrieves the quantity of the product.
     *
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets the ID of the product.
     *
     * @param id The ID of the product to be set.
     */
    public void setID(int id) {
        this.id = id;
    }
    /**
     * Sets the name of the product.
     *
     * @param name The name of the product to be set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the price of the product.
     *
     * @param price The price of the product to be set.
     */
    public void setPrice(float price) {this.price = price;}
    /**
     * Sets the quantity of the product.
     *
     * @param quantity The quantity of the product to be set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

