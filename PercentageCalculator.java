import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Model: Handles all percentage-related calculations
class PercentageCalculatorModel {
    public double calculatePercentage(double amount, double percent) {
        return (amount * percent) / 100;
    }

    public double calculatePercentageIncrease(double amount, double percent) {
        return amount + ((amount * percent) / 100);
    }

    public double calculatePercentageDecrease(double amount, double percent) {
        return amount - ((amount * percent) / 100);
    }

    public double findWholeGivenPart(double part, double percent) {
        if (percent == 0) throw new ArithmeticException("Percentage cannot be zero");
        return (part * 100) / percent;
    }
}

// View: Builds the GUI
class PercentageCalculatorView extends JFrame {
    JTextField input1, input2, resultField;
    JComboBox<String> operationBox;
    JButton calculateButton, clearButton;

    public PercentageCalculatorView() {
        setTitle("Percentage Calculator");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel label1 = new JLabel("Enter Amount/Part:");
        JLabel label2 = new JLabel("Enter Percentage:");
        JLabel operationLabel = new JLabel("Select Operation:");
        JLabel resultLabel = new JLabel("Result:");

        input1 = new JTextField();
        input2 = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        String[] operations = {
                "Find X% of a Number",
                "Percentage Increase",
                "Percentage Decrease",
                "Find Whole Given Part and Percentage"
        };
        operationBox = new JComboBox<>(operations);

        calculateButton = new JButton("Calculate");
        clearButton = new JButton("Clear");

        add(label1); add(input1);
        add(label2); add(input2);
        add(operationLabel); add(operationBox);
        add(resultLabel); add(resultField);
        add(calculateButton); add(clearButton);

        setVisible(true);
        setLocationRelativeTo(null);
    }
}

// Controller: Handles user interaction
class PercentageCalculatorController implements ActionListener {
    private final PercentageCalculatorModel model;
    private final PercentageCalculatorView view;

    public PercentageCalculatorController(PercentageCalculatorModel model, PercentageCalculatorView view) {
        this.model = model;
        this.view = view;
        this.view.calculateButton.addActionListener(this);
        this.view.clearButton.addActionListener(e -> clearFields());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double num1 = Double.parseDouble(view.input1.getText());
            double num2 = Double.parseDouble(view.input2.getText());
            double result = 0;

            String selectedOperation = (String) view.operationBox.getSelectedItem();
            assert selectedOperation != null;

            switch (selectedOperation) {
                case "Find X% of a Number":
                    result = model.calculatePercentage(num1, num2);
                    break;
                case "Percentage Increase":
                    result = model.calculatePercentageIncrease(num1, num2);
                    break;
                case "Percentage Decrease":
                    result = model.calculatePercentageDecrease(num1, num2);
                    break;
                case "Find Whole Given Part and Percentage":
                    result = model.findWholeGivenPart(num1, num2);
                    break;
            }
            view.resultField.setText(String.format("%.2f", result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Math Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        view.input1.setText("");
        view.input2.setText("");
        view.resultField.setText("");
    }
}

// Main Application: Initializes the MVC components
public class PercentageCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PercentageCalculatorModel model = new PercentageCalculatorModel();
            PercentageCalculatorView view = new PercentageCalculatorView();
            new PercentageCalculatorController(model, view);
        });
    }
}
