package edu.neumont.dkramer.calculator;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by dkramer on 4/6/17.
 */

public class BinaryCalculatorModel extends CalculatorModel {

    @Override
    protected void evaluate() {
        // ensure that we're in base 2
        long num = Long.parseLong(m_currentNum, 2);
        evaluate(num);
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
        String result = "" + (long)m_tempRunningTotal;
        return result;
    }

}
