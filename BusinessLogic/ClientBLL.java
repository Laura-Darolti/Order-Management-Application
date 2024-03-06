package org.example.BusinessLogic;

import org.example.Dao.ClientDAO;
import org.example.Model.Client;
/**
 * The ClientController class provides methods for managing client data.
 */
public class ClientBLL {
    /**
     * Edits an existing client with new client information.
     *
     * @param selectedClient the client to edit
     * @param newClient      the new client information
     */
    public static void editClient(Client selectedClient, Client newClient) {

        selectedClient.setName(newClient.getName());
        selectedClient.setAge(newClient.getAge());
        selectedClient.setAddress(newClient.getAddress());
        selectedClient.setEmail(newClient.getEmail());

        ClientDAO.updateClient(selectedClient);
    }
    /**
     * Deletes a client.
     *
     * @param selectedClient the client to delete
     */
    public static void deleteClient(Client selectedClient) {
        ClientDAO.delete(selectedClient);
    }
    /**
     * Inserts a new client into the database.
     *
     * @param client the client to insert
     */
    public static void insertClient(Client client) {
        ClientDAO.insert(client);
    }
}
