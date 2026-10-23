import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Complete GUI: do not edit for this assignment. */
public class CalculatorGUI extends JFrame {
    private final JTextField expression = new JTextField();
    private final JLabel result = new JLabel("Enter a postfix expression", SwingConstants.RIGHT);

    public CalculatorGUI() {
        super("Postfix Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setBackground(new Color(238, 242, 248));
        JLabel heading = new JLabel("POSTFIX CALCULATOR");
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        heading.setForeground(new Color(40, 60, 95));
        JPanel display = new JPanel(new GridLayout(3, 1, 0, 6));
        display.setOpaque(false);
        expression.setFont(new Font("Monospaced", Font.PLAIN, 22));
        expression.setToolTipText("Use spaces between tokens: 12 3 4 * +");
        expression.addActionListener(e -> evaluate());
        result.setFont(new Font("SansSerif", Font.BOLD, 21));
        result.setForeground(new Color(28, 84, 145));
        display.add(heading);
        display.add(expression);
        display.add(result);
        root.add(display, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5, 4, 7, 7));
        buttons.setOpaque(false);
        String[] labels = {"7", "8", "9", "/", "4", "5", "6", "*",
                           "1", "2", "3", "-", "C", "0", "SPACE", "+",
                           "BACK", "EXAMPLE", "=", "HELP"};
        for (String label : labels) {
            JButton button = new JButton(label);
            button.setFont(new Font("SansSerif", Font.BOLD, label.length() > 2 ? 12 : 20));
            button.setFocusPainted(false);
            button.setBackground(label.equals("=") ? new Color(180, 213, 250) : Color.WHITE);
            button.addActionListener(e -> handleButton(label));
            buttons.add(button);
        }
        root.add(buttons, BorderLayout.CENTER);
        JLabel hint = new JLabel("Space-separated tokens. Example: 12 3 4 * + = 24");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        root.add(hint, BorderLayout.SOUTH);
        setContentPane(root);
        setSize(460, 520);
        setMinimumSize(new Dimension(400, 460));
        setLocationRelativeTo(null);
    }

    private void handleButton(String key) {
        switch (key) {
            case "=" -> evaluate();
            case "C" -> { expression.setText(""); result.setText("Enter a postfix expression"); }
            case "SPACE" -> append(" ");
            case "BACK" -> {
                String s = expression.getText();
                if (!s.isEmpty()) expression.setText(s.substring(0, s.length() - 1));
            }
            case "EXAMPLE" -> { expression.setText("12 3 4 * +"); result.setText("Press = to evaluate"); }
            case "HELP" -> JOptionPane.showMessageDialog(this,
                "Postfix: place the operator after its operands.\n" +
                "Example: 12 3 4 * + evaluates to 24.\n" +
                "Separate every number and operator with a space.\n" +
                "You can type expressions or use the buttons.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
            default -> {
                if ("+-*/".contains(key)) append(" " + key + " ");
                else append(key);
            }
        }
        expression.requestFocusInWindow();
    }

    private void append(String value) {
        expression.setText(expression.getText() + value);
        expression.setCaretPosition(expression.getText().length());
    }

    private void evaluate() {
        try {
            int answer = PostfixEvaluator.evaluate(expression.getText());
            result.setForeground(new Color(20, 110, 69));
            result.setText("Result: " + answer);
        } catch (PostFixException | ArithmeticException error) {
            result.setForeground(new Color(180, 45, 45));
            result.setText("Error: " + error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI().setVisible(true));
    }
}
