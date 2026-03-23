import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private String displayValue = "0";
    private double firstValue = 0;
    private String operator = "";
    private boolean isTypingValue = false;

    // UI Colors
    private final Color bgColor = new Color(200, 230, 255); // Light Blue
    private final Color bodyColor = new Color(150, 190, 230); // Pastel Blue
    private final Color buttonColor = new Color(180, 210, 255);
    private final Color borderColor = Color.BLACK;

    public Calculator() {
        setTitle("Cartoon Calc");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with custom background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2d.setColor(bgColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Calculator Body
                g2d.setColor(bodyColor);
                g2d.fillRoundRect(30, 30, 275, 400, 40, 40);

                // Thick Black Border
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRoundRect(30, 30, 275, 400, 40, 40);
            }
        };
        mainPanel.setLayout(null);

        // Display Screen
        JLabel displayLabel = new JLabel(displayValue, SwingConstants.RIGHT);
        displayLabel.setBounds(50, 60, 235, 60);
        displayLabel.setOpaque(true);
        displayLabel.setBackground(Color.WHITE);
        displayLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        displayLabel.setBorder(BorderFactory.createLineBorder(borderColor, 3));
        mainPanel.add(displayLabel);

        // Button Layout Data
        String[][] buttons = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"C", "0", "=", "+"}
        };

        // Create and place buttons
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                String text = buttons[r][c];
                RoundButton btn = new RoundButton(text);
                btn.setBounds(55 + (c * 58), 150 + (r * 70), 50, 50);

                btn.addActionListener(e -> {
                    handleInput(text);
                    displayLabel.setText(displayValue);
                });

                mainPanel.add(btn);
            }
        }

        add(mainPanel);
        setVisible(true);
    }

    private void handleInput(String input) {
        if (input.matches("[0-9]")) {
            if (!isTypingValue || displayValue.equals("0")) {
                displayValue = input;
            } else {
                displayValue += input;
            }
            isTypingValue = true;
        } else if (input.equals("C")) {
            displayValue = "0";
            firstValue = 0;
            operator = "";
            isTypingValue = false;
        } else if (input.equals("=")) {
            calculate();
            operator = "";
            isTypingValue = false;
        } else { // Operators
            firstValue = Double.parseDouble(displayValue);
            operator = input;
            isTypingValue = false;
        }
    }

    private void calculate() {
        double secondValue = Double.parseDouble(displayValue);
        switch (operator) {
            case "+" -> displayValue = String.valueOf(firstValue + secondValue);
            case "-" -> displayValue = String.valueOf(firstValue - secondValue);
            case "*" -> displayValue = String.valueOf(firstValue * secondValue);
            case "/" -> {
                if (secondValue == 0) displayValue = "Error";
                else displayValue = String.valueOf(firstValue / secondValue);
            }
        }
        // Clean up trailing zeros
        if (displayValue.endsWith(".0")) {
            displayValue = displayValue.substring(0, displayValue.length() - 2);
        }
    }

    // Inner class for the Custom Cartoon Button
    class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isArmed()) g2d.setColor(buttonColor.darker());
            else g2d.setColor(buttonColor);

            // Draw circular button body
            g2d.fillOval(0, 0, getWidth() - 3, getHeight() - 3);

            // Thick border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(0, 0, getWidth() - 3, getHeight() - 3);

            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}