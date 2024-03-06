package org.example.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.Connection.ConnectionFactory;
import org.example.Model.Client;
/**
 * The ClientDAO class provides data access operations for the Client entity.
 */
public class ClientDAO extends AbstractDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name, address, email, id, age) VALUES (?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM Client where id = ?";
    private final static String getAllClientsStatementString = "SELECT * FROM Client";
    private static final String deleteStatementString="Delete from Client where id=?";

    /**
     * Inserts a client into the database.
     *
     * @param client the client to insert
     */

    public static void insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getAddress());
            insertStatement.setString(3, client.getEmail());
            insertStatement.setInt(4, client.getId());
            insertStatement.setInt(5, client.getAge());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }


    /**
     * Retrieves all clients from the database.
     *
     * @return a list of all clients
     */
    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement getClientsStatement = null;
        ResultSet rs = null;

        try {
            getClientsStatement = dbConnection.prepareStatement(getAllClientsStatementString);
            rs = getClientsStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                int age = rs.getInt("age");

                Client client = new Client(name, address, email, id, age);
                clients.add(client);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:getAllClients " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(getClientsStatement);
            ConnectionFactory.close(dbConnection);
        }

        return clients;
    }
    /**
     * Updates a client in the database.
     *
     * @param client the client to update
     */
    public static void updateClient(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try {
            String updateStatementString = "UPDATE Client SET name=?, address=?, email=?, age=? WHERE id=?";
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getAddress());
            updateStatement.setString(3, client.getEmail());
            updateStatement.setInt(4, client.getAge());
            updateStatement.setInt(5, client.getId());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:editClient " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    /**
     * Deletes a client from the database.
     *
     * @param client the client to delete
     */
    public static void delete(Client client){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, client.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }



}