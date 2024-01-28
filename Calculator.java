import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField textField;
    private String operator;
    private double firstNumber, secondNumber;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        //clear button
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                operator = null;
                firstNumber = 0;
                secondNumber = 0;
            }
        });
        buttonPanel.add(clearButton);

        setLayout(new BorderLayout());
        add(textField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (buttonText.matches("[0-9.]")) {
                textField.setText(textField.getText() + buttonText);
            } else if (buttonText.matches("[/*\\-+]")) {
                operator = buttonText;
                firstNumber = Double.parseDouble(textField.getText());
                textField.setText("");
            } else if (buttonText.equals("=")) {
                secondNumber = Double.parseDouble(textField.getText());

                switch (operator) {
                    case "+":
                        textField.setText(String.valueOf(firstNumber + secondNumber));
                        break;
                    case "-":
                        textField.setText(String.valueOf(firstNumber - secondNumber));
                        break;
                    case "*":
                        textField.setText(String.valueOf(firstNumber * secondNumber));
                        break;
                    case "/":
                        if (secondNumber != 0) {
                            textField.setText(String.valueOf(firstNumber / secondNumber));
                        } else {
                            textField.setText("Error");
                        }
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
