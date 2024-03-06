package org.example.Start;

import org.example.Presentation.ClientView;
import org.example.Presentation.OrderView;

import java.util.logging.Logger;
import org.example.Model.Client;
import org.example.Presentation.ProductView;

import javax.swing.*;
/**
 *The Start class is the entry point of the application.
 *It initializes and starts the different views of the application.
 */
public class Start {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    /**
     * The main method is the entry point of the application.
     * It creates an instance of Client, initializes the views, and starts the Swing application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        Client student = new Client("dummy name", "dummy address", "dummy@address.co", 12, 0);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientView();
                new OrderView();
                new ProductView();
            }
        });

    }
}
