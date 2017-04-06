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
        int num = Integer.parseInt(m_currentNum, 2);
        evaluate(num);
    }

    @Override
    protected boolean isValidToken(String token) {
        boolean isValid =   super.isValidToken(token)
                            && !isDuplicateLeadingZero(token)
                            && !checkOverflow(token);
        return isValid;
    }

    protected boolean checkOverflow(String token) {
        boolean willOverflow = (m_currentNum.length() + 1 >= 32);
        return willOverflow;
    }

    protected boolean isDuplicateLeadingZero(String token) {
        boolean result = (token.equals("0") && m_currentNum.equals("0"));
        return result;
    }

    @Override
    public String getRunningTotalDisplay() {
        String result = "" + (int)m_tempRunningTotal;
        return result;
    }

}
