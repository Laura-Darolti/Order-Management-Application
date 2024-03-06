package org.example.BusinessLogic;

import org.example.Dao.OrderDAO;
import org.example.Dao.ProductDAO;
import org.example.Model.Order;
import org.example.Model.Product;

/**
 * The OrderController class provides methods for managing order data.
 */
public class OrderBLL {
    /**
     * Edits an existing order with new order information.
     *
     * @param selectedOrder the order to edit
     * @param newOrder      the new order information
     */
    public static String editOrder(Order selectedOrder, Order newOrder) {


        int quantityDifference = newOrder.getProductAmount() - selectedOrder.getProductAmount();

        selectedOrder.setCustomerName(newOrder.getCustomerName());
        selectedOrder.setProductName(newOrder.getProductName());


        // edit product
        // if quantity +=2 => product amount -2
        // if quantity -=2 => product amount +2

        int remainingProductAmount = ProductDAO.getProductByName(selectedOrder.getProductName()).getQuantity() - quantityDifference;

        System.out.println("remaining amount is " + remainingProductAmount);
        // if enough product, update product and order
        if (remainingProductAmount >= 0) {


            selectedOrder.setProductAmount(newOrder.getProductAmount());
            System.out.println("new product amount is " + selectedOrder.getProductAmount());
            float newPrice = ProductDAO.getProductByName(selectedOrder.getProductName()).getPrice() * newOrder.getProductAmount();
            selectedOrder.setTotalAmount(newPrice);

            OrderDAO.updateOrder(selectedOrder);
            Product product = ProductDAO.getProductByName(selectedOrder.getProductName());
            product.setQuantity(remainingProductAmount);
            ProductDAO.updateProduct(product);
            return "OK";
        } else {
            return "UNDERSTOCKED";
        }

    }

    /**
     * Deletes an order.
     *
     * @param selectedOrder the order to delete
     */
    public static void deleteOrder(Order selectedOrder) {

        OrderDAO.deleteOrder(selectedOrder);
    }

    /**
     * Inserts a new order into the database.
     *
     * @param order the order to insert
     */
    public static String insertOrder(Order order) {

        String productName = order.getProductName();
        Product product = ProductDAO.getProductByName(productName);
        float price = product.getPrice() * order.getProductAmount();

        order.setTotalAmount(price);


        int remainingProductAmount = product.getQuantity() - order.getProductAmount();

        if (remainingProductAmount >= 0) {
            OrderDAO.insert(order);
            product.setQuantity(remainingProductAmount);
            ProductDAO.updateProduct(product);
            return "OK";
        } else {
            return "UNDERSTOCKED";
        }


    }


}