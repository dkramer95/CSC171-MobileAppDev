package edu.neumont.dkramer.calculator;

import java.math.BigDecimal;

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
	protected BigDecimal m_runningTotal;

	// running total that is currently under evaluation
    protected BigDecimal m_tempRunningTotal;

    // flag to check if current number contains a decimal point
    protected boolean m_currentNumHasDecimal;

    // flag to check if last number before the operator has been captured
    protected boolean m_didCaptureNumBeforeOperator;

    // flag to check if we have entered a number
    protected boolean m_hasEnteredNum;

    // starting index in calc text of the next number we're entering
    protected int m_currentNumStartIndex;




    public CalculatorModel() {
        clear();
    }


    public void processToken(String token) {
        // check for sign change +/- token

        if (token.equals(SIGN)) {
	        toggleSign();
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

        if (!isTokenBeforeAnyNumber(token) && !isDuplicateDecimal(token)
		        && !isDuplicateLeadingZero(token)) {
            updateText(token);
        } else if (isChangedOperator(token)) {
            changeLastToken(token);
        } else {
            result = false;
        }
        return result;
    }

    protected boolean isChangedOperator(String token) {
        boolean result = (m_didCaptureNumBeforeOperator && m_currentNum.isEmpty() && !token.equals("."));
        return result;
    }

    protected void changeLastToken(String token) {
        m_calcText = m_calcText.substring(0, m_calcText.length() - m_lastOperator.length()) + token;
    }

    protected boolean isTokenBeforeAnyNumber(String token) {
        boolean result = (!isDigit(token) && !m_hasEnteredNum && !token.equals("."));
        return result;
    }

    protected boolean isDuplicateDecimal(String token) {
        boolean result = (token.equals(".") && m_currentNumHasDecimal);
        return result;
    }

	protected boolean isDuplicateLeadingZero(String token) {
		boolean result = (token.equals("0") && (m_currentNum.equals("0")));
		return result;
	}

    protected void updateText(String token) {
        m_calcText += token;
    }

    public void clear() {
        m_currentNum = "";
        m_calcText = "";
        m_lastOperator = "";
	    m_runningTotal = BigDecimal.ZERO;
	    m_tempRunningTotal = BigDecimal.ZERO;
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
		String token = "";

		if (m_currentNum.startsWith("-")) {
			m_currentNum = m_currentNum.substring(1);
		} else {
			m_currentNum = "-" + m_currentNum;
			m_hasEnteredNum = true;
			token = "-";
		}
		evaluate();
		m_calcText = m_calcText.substring(0, m_currentNumStartIndex) + token + m_currentNum;

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
            BigDecimal num = new BigDecimal(m_currentNum);
            evaluate(num);
        } catch (NumberFormatException e) {

        }
    }

    protected void evaluate(BigDecimal num) {
        switch(m_lastOperator) {
            case ADD:
                m_tempRunningTotal = m_runningTotal.add(num);
                break;
            case SUB:
	            m_tempRunningTotal = m_runningTotal.subtract(num);
                break;
            case MULT:
            	m_tempRunningTotal = m_runningTotal.multiply(num);
                break;
            case DIV:
                m_tempRunningTotal = m_runningTotal.divide(num);
                break;
            case MOD:
                m_tempRunningTotal = m_runningTotal.remainder(num);
                break;
            case EXP:
                m_tempRunningTotal = m_runningTotal.pow(num.intValue());
                break;

            // still entering the first number
            default:
                m_tempRunningTotal = num;
                break;
        }
    }

    public void setValue(BigDecimal value) {
	    m_runningTotal = value;
	    m_tempRunningTotal = value;

	    // fake out that we've entered a num if not default value
	    if (!value.equals("0") || !value.equals("0.0")) {
		    m_hasEnteredNum = true;
	    }
    }

    public void setCalcText(String text) {
	    m_calcText = text;
    }

    public String getRunningTotalDisplay() {
        String result = m_tempRunningTotal.toString();
        return result;
    }

    public BigDecimal getRunningTotal() {
        return m_tempRunningTotal;
    }

    public BigDecimal getResult() {
        return m_runningTotal;
    }

    public String getCalcText() {
        return m_calcText;
    }

    protected void checkNumCapture() {
        if (m_currentNum.isEmpty() && m_runningTotal.equals("0")) { return; }

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
