package s1TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.border.EmptyBorder;


public class TaskManagerUI extends JFrame  implements ActionListener, KeyListener {

    private Button viewButton;
    private JFrame frame;
    private JPanel panel;

    // Setters and Getters
    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
    public Button getViewButton() {
        return viewButton;
    }
    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }


    public void display() {
        frame = new JFrame("Task Manager");
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.add(panel);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

