package org.javatask.scientificcalculator;

public class CalculatorEngine {

    // Perform basic arithmetic operations
    static double calculate(String expression) {
        // Split the expression into operands and operator
        String[] tokens = expression.split(" ");

        // If splitting with space doesn't yield tokens, try without space
        if (tokens.length == 1) {
            tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        }

        // Ensure there are enough tokens to perform the calculation
        if (tokens.length != 3) {
            throw new ArithmeticException("Invalid expression format");
        }

        try {
            double operand1 = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double operand2 = Double.parseDouble(tokens[2]);

            switch (operator) {
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    if (operand2 == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    return operand1 / operand2;
                default:
                    throw new ArithmeticException("Invalid operator");
            }
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Invalid numeric input");
        }
    }

    // Apply trigonometric or logarithmic function
    static double applyFunction(double value, String function) {
        try {
            if (function.equals("sin") || function.equals("cos") || function.equals("tan")) {
                // Handle sin, cos, and tan functions
                switch (function) {
                    case "sin":
                        return Math.sin(value);
                    case "cos":
                        return Math.cos(value);
                    case "tan":
                        return Math.tan(value);
                    default:
                        throw new ArithmeticException("Invalid function: " + function);
                }
            } else if (function.equals("log")) {
                // Handle logarithmic function
                if (value <= 0) {
                    throw new ArithmeticException("Invalid input for logarithm");
                }
                return Math.log10(value);
            } else {
                throw new ArithmeticException("Invalid function: " + function);
            }
        } catch (ArithmeticException ex) {
            throw ex;  // Re-throw the exception if there is an error
        }
    }
}
