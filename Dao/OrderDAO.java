package org.example.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.Connection.ConnectionFactory;
import org.example.Model.Order;
/**
 * The OrderDAO class provides data access operations for the Order entity.
 */
public class OrderDAO extends AbstractDAO {

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO schooldb.order (customerName, totalAmount, productName,productAmount,id)"+" VALUES (?,?,?,?,?)";
    private static final String updateStatementString = "UPDATE schooldb.order SET customerName=?, totalAmount=?,productName=?,productAmount=? WHERE id=?";
    private static final String deleteStatementString = "DELETE FROM schooldb.order WHERE id=?";
    private static final String getAllOrdersStatementString = "SELECT * FROM schooldb.order";
    private static final String findStatementString = "SELECT * FROM schooldb.order WHERE id=?";
    /**
     * Inserts an order into the database.
     *
     * @param order the order to insert
     * @return the generated ID of the inserted order
     */
    public static int insert(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId=-1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, order.getCustomerName());
            insertStatement.setFloat(2, order.getTotalAmount());
            insertStatement.setString(3, order.getProductName());
            insertStatement.setInt(4, order.getProductAmount());
            insertStatement.setInt(5,order.getID());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
          //  if (rs.next()) {
              //  insertedId = rs.getInt(1);
          //  }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }
    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     */
    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement getOrdersStatement = null;
        ResultSet rs = null;

        try {
            getOrdersStatement = dbConnection.prepareStatement(getAllOrdersStatementString);
            rs = getOrdersStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                //Date orderDate = rs.getDate("order_date");
                String customerName = rs.getString("customerName");
                float totalAmount = rs.getFloat("totalAmount");
                int productamount =rs.getInt("productAmount");
                String productName=rs.getString("productName");
                Order order = new Order(id, customerName,  totalAmount,productName,productamount);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:getAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(getOrdersStatement);
            ConnectionFactory.close(dbConnection);
        }

        return orders;
    }
    /**
     * Updates an order in the database.
     *
     * @param order the order to update
     */
    public static void updateOrder(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, order.getCustomerName());
            updateStatement.setFloat(2, order.getTotalAmount());
            updateStatement.setString(3, order.getProductName());
            updateStatement.setInt(4, order.getProductAmount());
            updateStatement.setInt(5, order.getID());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Deletes an order from the database.
     *
     * @param order the order to delete
     */
    public static void deleteOrder(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, order.getID());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Finds an order in the database by its ID.
     *
     * @param orderId the ID of the order to find
     * @return the found order, or null if not found
     */
    public Order findById(int orderId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        Order order = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, orderId);
            rs = findStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");

                String customerName = rs.getString("customer_name");
                float totalAmount = rs.getFloat("total_amount");
                String productName=rs.getString("product Name");
                int productAmount=  rs.getInt("product amount");
                order = new Order(id, customerName, totalAmount,productName,productAmount);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }

        return order;
    }



}


