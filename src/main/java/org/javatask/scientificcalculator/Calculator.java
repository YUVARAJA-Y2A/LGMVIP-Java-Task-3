package org.javatask.scientificcalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Calculator extends JFrame implements ActionListener {
    private JTextField displayField;
    private String currentInput;

    public Calculator() {
        currentInput = "";

        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Larger display field with larger font size
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setPreferredSize(new Dimension(300, 50));
        displayField.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font size for the display field
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
                "9", "8", "7", "/",
                "*"  , "6", "5", "4",
                "+" , "-", "3", "2",
                "1", ".", "=",  "0",
                "sin", "cos", "tan", "log" ,"C"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            if (label.equals("C")) {
                button.setPreferredSize(new Dimension(70, 40));
            }
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Allow keyboard interaction
        addKeyListener(new CalculatorKeyListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "=":
                evaluate();
                break;
            case "C":
                clear();
                break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
                applyTrigOrLogFunction(command);
                break;
            default:
                currentInput += command;
                displayField.setText(currentInput);
                break;
        }
    }

    private void evaluate() {
        try {
            System.out.println("Current Input: " + currentInput);
            double result = CalculatorEngine.calculate(currentInput);
            System.out.println("Result: " + result);
            displayField.setText(Double.toString(result));
            currentInput = Double.toString(result);
        } catch (ArithmeticException | NumberFormatException ex) {
            ex.printStackTrace();  // Print the exception stack trace for debugging
            displayField.setText("Error");
            currentInput = "";
        }
    }

    private void applyTrigOrLogFunction(String function) {
        try {
            double result = CalculatorEngine.applyFunction(Double.parseDouble(currentInput), function);
            displayField.setText(Double.toString(result));
            currentInput = Double.toString(result);
        } catch (ArithmeticException | NumberFormatException ex) {
            displayField.setText("Error");
            currentInput = "";
        }
    }

    private void clear() {
        currentInput = "";
        displayField.setText("");
    }

    // Inner class for handling keyboard events
    private class CalculatorKeyListener extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();

            if (Character.isDigit(keyChar) || "+-*/.".indexOf(keyChar) != -1) {
                currentInput += keyChar;
                displayField.setText(currentInput);
            } else if (keyChar == '=') {
                evaluate();
            } else if (keyChar == 'C') {
                clear();
            }
            // Handle additional keys as needed (e.g., trigonometric functions)
        }

        // Handle Enter key for '=' functionality
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                evaluate();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}