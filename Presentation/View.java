package org.example.Presentation;

import org.example.Model.Client;
import org.example.Presentation.ClientView;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View {

    public static void displayEntities(List<?> entities, JPanel panel, JFrame frame, List<Object> entityList) {
        panel.removeAll();
        entityList.clear();

        for (Object entity : entities) {
            JPanel entityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    entityPanel.add(new JLabel(field.getName() + ": " + value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            panel.add(entityPanel);
            entityList.add(entity);
        }

        panel.revalidate();
        frame.revalidate();
    }

}
