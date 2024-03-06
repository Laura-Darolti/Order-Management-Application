package org.example.Dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.Connection.ConnectionFactory;
import org.example.Model.Product;
/**
 * The ProductDAO class provides data access operations for the Product entity.
 */
public class ProductDAO extends AbstractDAO {

    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name, price, quantity, id) VALUES (?,?,?,?)";
    private static final String updateStatementString = "UPDATE product SET name=?, price=?, quantity=? WHERE id=?";
    private static final String deleteStatementString = "DELETE FROM product WHERE id=?";
    private static final String getAllProductsStatementString = "SELECT * FROM product";
    private static final String findStatementString = "SELECT * FROM product WHERE id=?";

    /**
     * Inserts a product into the database.
     *
     * @param product the product to insert
     */
    public static void insertProduct(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setFloat(2, product.getPrice());
            insertStatement.setInt(3, product.getQuantity());
            insertStatement.setInt(4, product.getId());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
//            if (rs.next()) {
//                int generatedId = rs.getInt(1);
//                product.setID(generatedId);
//            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insertProduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement getProductsStatement = null;
        ResultSet rs = null;

        try {
            getProductsStatement = dbConnection.prepareStatement(getAllProductsStatementString);
            rs = getProductsStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");

                Product product = new Product(id, name, price, quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:getAllProducts " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(getProductsStatement);
            ConnectionFactory.close(dbConnection);
        }

        return products;
    }
    /**
     * Retrieves a product from the database by its name.
     *
     * @param productName the name of the product to retrieve
     * @return the found product, or null if not found
     */
    public static Product getProductByName(String productName) {
        Connection dbConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product product = null;

        try {
            dbConnection = ConnectionFactory.getConnection();


            String sql = "SELECT * FROM Product WHERE name = ?";
            statement = dbConnection.prepareStatement(sql);
            statement.setString(1, productName);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                int quantity = resultSet.getInt("quantity");

                product = new Product(id, name, price, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }

        return product;
    }
    /**
     * Updates a product in the database.
     *
     * @param product the product to update
     */
    public static void updateProduct(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, product.getName());
            updateStatement.setFloat(2, product.getPrice());
            updateStatement.setInt(3, product.getQuantity());
            updateStatement.setInt(4, product.getId());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:updateProduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Deletes a product from the database.
     *
     * @param product the product to delete
     */
    public static void deleteProduct(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, product.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:deleteProduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Finds a product in the database by its ID.
     *
     * @param productId the ID of the product to find
     * @return the found product, or null if not found
     */
    public static Product findProductById(int productId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        Product product = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, productId);
            rs = findStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");

                product = new Product(id, name, price, quantity);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findProductById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }

        return product;
    }


}

