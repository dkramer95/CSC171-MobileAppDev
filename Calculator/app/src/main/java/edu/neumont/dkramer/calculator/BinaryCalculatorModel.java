package edu.neumont.dkramer.calculator;

import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;

/**
 * Created by dkramer on 4/6/17.
 */

public class BinaryCalculatorModel extends CalculatorModel {
    public static final String BIT_AND  = "AND";
    public static final String BIT_OR   = "OR";
    public static final String BIT_XOR  = "XOR";

    @Override
    protected void evaluate() {
        // ensure that we're in base 2
        long num = Long.parseLong(m_currentNum, 2);
        evaluate(new BigDecimal(num));
    }

    @Override
    protected void processOperator(String op) {
	    m_hasEnteredNum = false;
	    m_currentNumStartIndex = m_calcText.length();

	    switch (op) {
		    case BIT_AND:
		    case BIT_OR:
		    case BIT_XOR:
		    	m_lastOperator = op;
			    checkNumCapture();
			    break;
		    default:
		    	super.processOperator(op);
	    }
    }

    @Override
    protected void evaluate(BigDecimal num) {
        switch (m_lastOperator) {
            case BIT_AND:
                m_tempRunningTotal = new BigDecimal(m_tempRunningTotal.longValue() & num.longValue());
                break;
            case BIT_OR:
                m_tempRunningTotal = new BigDecimal(m_tempRunningTotal.longValue() | num.longValue());
            case BIT_XOR:
	            m_tempRunningTotal = new BigDecimal(m_tempRunningTotal.longValue() ^ num.longValue());
	            break;
            default:
                super.evaluate(num);
        }
    }

    protected boolean validateToken(String token) {
        boolean isValid = super.validateToken(token) && !checkOverflow(token);
        return isValid;
    }

    protected boolean checkOverflow(String token) {
        boolean willOverflow = (m_currentNum.length() + 1 >= 64);
        return willOverflow;
    }

    protected boolean isDuplicateLeadingZero(String token) {
        boolean result = (token.equals("0") && (m_currentNum.equals("0")));
        return result;
    }

    @Override
    public String getRunningTotalDisplay() {
        String result = "" + m_tempRunningTotal.longValue();
        return result;
    }

}
