package edu.neumont.dkramer.calculator;

/**
 * Backing model for a calculator that keeps track of the running total
 * and provides functions for different calculation operations.
 * Created by dkramer on 4/5/17.
 */

public class CalculatorModel {

    /*
     * Operator constants
     */
    public static final String ADD      = "+";
    public static final String SUB      = "-";
    public static final String MULT     = "×";
    public static final String DIV      = "÷";
    public static final String MOD      = "%";
    public static final String EXP      = "^";
    public static final String SIGN     = "+/-";
    public static final String DECIMAL  = ".";

    // the active number that is being entered
    protected String m_currentNum;

    // all the numbers and operators that have been entered
    protected String m_calcText;

    // last entered operator
    protected String m_lastOperator;

    // the current running total after performing all operations
    protected double m_runningTotal;

    // running total that is currently under evaluation
    protected double m_tempRunningTotal;

    // flag to check if current number contains a decimal point
    protected boolean m_currentNumHasDecimal;

    // flag to check if last number before the operator has been captured
    protected boolean m_didCaptureNumBeforeOperator;

    // flag to check if we have entered a number
    protected boolean m_hasEnteredNum;

    protected int m_currentNumStartIndex;


    public CalculatorModel() {
        clear();
    }

    public void processToken(String token) {
        // check for sign change +/- token

        if (token.equals(CalculatorModel.SIGN)) {
            token = toggleSign();
            m_calcText = m_calcText.substring(0, m_currentNumStartIndex) + token + m_currentNum;
        } else {
            if (validateToken(token)) {

                if (isDigit(token)) {
                    processDigit(token);
                } else {
                    processOperator(token);
                }
            }
        }
    }

    protected boolean validateToken(String token) {
        // make sure we don't add an operator before a number
        // if there is a number already, change the last operator to the current token
        boolean result = true;

        if (m_didCaptureNumBeforeOperator && m_currentNum.isEmpty() && !isDigit(token)) {
            changeLastToken(token);
        } else {
            result = (!isTokenBeforeAnyNumber(token) && !isDuplicateDecimal(token));

            if (result) {
                updateText(token);
            }
        }
        return result;
    }

    protected void changeLastToken(String token) {
        m_calcText = m_calcText.substring(0, m_calcText.length() - 1) + token;
    }

    protected boolean isValidToken(String token) {
        boolean isValid = !isTokenBeforeAnyNumber(token) && !isDuplicateDecimal(token);
        return isValid;
    }

    protected boolean isTokenBeforeAnyNumber(String token) {
        boolean result = (!isDigit(token) && !m_hasEnteredNum);
        return result;
    }

    protected boolean isDuplicateDecimal(String token) {
        boolean result = (token.equals(".") && m_currentNumHasDecimal);
        return result;
    }

    protected void updateText(String token) {
        m_calcText += token;
    }

    public void clear() {
        m_currentNum = "";
        m_calcText = "";
        m_lastOperator = "";
        m_runningTotal = 0.0;
        m_tempRunningTotal = 0.0;
        m_currentNumHasDecimal = false;
        m_didCaptureNumBeforeOperator = false;
        m_hasEnteredNum = false;
        m_currentNumStartIndex = 0;
    }

    protected void processOperator(String op) {
        m_hasEnteredNum = false;
        m_currentNumStartIndex = m_calcText.length();

        switch (op) {
            case DECIMAL:
                checkDecimal();
                break;
            case SIGN:
                toggleSign();
                break;
            case ADD:
            case SUB:
            case MULT:
            case DIV:
            case MOD:
            case EXP:
                m_lastOperator = op;
                checkNumCapture();
                break;
            default:
                throw new IllegalArgumentException("Invalid Operator: [" + op + "]");
        }
    }

    protected String toggleSign() {
        // if positive, make negative
        String token = "";

        if (m_currentNum.startsWith("-")) {
            m_currentNum = m_currentNum.substring(1);
        } else {
            m_currentNum = "-" + m_currentNum;
            m_hasEnteredNum = true;
            token = "-";
        }
        evaluate();
        return token;
    }

    protected void processDigit(String digit) {
        m_didCaptureNumBeforeOperator = false;

        if (!m_hasEnteredNum) {
            // capture starting index of first digit of current number to allow editing
            // of calc text with +/-
            m_currentNumStartIndex = (m_calcText.isEmpty()) ? 0 : m_calcText.length() - 1;
            m_hasEnteredNum = true;
        }
        m_currentNum += digit;

        if (!m_calcText.isEmpty()) {
            evaluate();
        }
    }

    protected void evaluate() {
        try {
            double num = Double.parseDouble(m_currentNum);
            evaluate(num);
        } catch (NumberFormatException e) {

        }
    }

    protected void evaluate(double num) {
        switch(m_lastOperator) {
            case ADD:
                m_tempRunningTotal = (m_runningTotal + num);
                break;
            case SUB:
                m_tempRunningTotal = (m_runningTotal - num);
                break;
            case MULT:
                m_tempRunningTotal = (m_runningTotal * num);
                break;
            case DIV:
                m_tempRunningTotal = (m_runningTotal / num);
                break;
            case MOD:
                m_tempRunningTotal = (m_runningTotal % num);
                break;
            case EXP:
                m_tempRunningTotal = Math.pow(m_runningTotal, num);
                break;

            // still entering the first number
            default:
                m_tempRunningTotal = num;
                break;
        }
    }

    public String getRunningTotalDisplay() {
        String result = "" + m_tempRunningTotal;
        return result;
    }

    public double getRunningTotal() {
        return m_tempRunningTotal;
    }

    public double getResult() {
        return m_runningTotal;
    }

    public String getCalcText() {
        return m_calcText;
    }

    protected void checkNumCapture() {
        if (m_currentNum.isEmpty() && m_runningTotal == 0.0) { return; }

        if (!m_didCaptureNumBeforeOperator) {
            m_runningTotal = m_tempRunningTotal;
            m_didCaptureNumBeforeOperator = true;

            // clear out current num, now that we've captured it
            m_currentNum = "";

            // reset decimal flag, since we're on to another number
            m_currentNumHasDecimal = false;
        }
    }

    protected boolean isDigit(String s) {
        boolean isDigit = s.matches("\\d+");
        return isDigit;
    }

    protected boolean checkDecimal() {
        boolean didAdd = false;

        if (!m_currentNumHasDecimal) {
            m_currentNum += ".";
            m_currentNumHasDecimal = true;
            didAdd = true;
        }
        return didAdd;
    }
}
