package org.example.BusinessLogic;

import org.example.Dao.ProductDAO;
import org.example.Model.Product;
/**
 * The ProductController class provides methods to perform operations on products.
 */
public class ProductBLL {
    /**
     * Updates the information of a selected product with the data from a new product.
     *
     * @param selectedProduct The selected product to be edited.
     * @param newProduct      The new product containing the updated information.
     */
    public static void editProduct(Product selectedProduct, Product newProduct) {
        selectedProduct.setName(newProduct.getName());
        selectedProduct.setPrice(newProduct.getPrice());
        selectedProduct.setQuantity(newProduct.getQuantity());


        ProductDAO.updateProduct(selectedProduct);
    }
    /**
     * Deletes the specified product.
     *
     * @param selectedProduct The product to be deleted.
     */
    public static void deleteProduct(Product selectedProduct) {

        ProductDAO.deleteProduct(selectedProduct);
    }
    /**
     * Inserts a new product into the system.
     *
     * @param product The product to be inserted.
     */
    public static void insertProduct(Product product) {

        ProductDAO.insertProduct(product);
    }
}
