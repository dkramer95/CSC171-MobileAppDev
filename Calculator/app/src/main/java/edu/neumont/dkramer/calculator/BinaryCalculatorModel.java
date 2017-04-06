package edu.neumont.dkramer.calculator;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by dkramer on 4/6/17.
 */

public class BinaryCalculatorModel extends CalculatorModel {

    @Override
    protected void evaluate() {
        if (m_calcText.isEmpty()) { return; }

        Log.i("INFO", "Current num=" + m_currentNum);

        // ensure that we're in base 2
        int num = Integer.parseInt(m_currentNum, 2);
        evaluate(num);

        String binStr = Integer.toBinaryString(num);

        Log.i("INFO", "Base10 value: " + num);
        Log.i("INFO", "Binary string value: " + binStr);
    }

    protected boolean isValidToken(String token) {
        boolean isValid = super.isValidToken(token) && !isDuplicateLeadingZero(token);
        return isValid;
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
