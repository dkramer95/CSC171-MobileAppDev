package edu.neumont.dkramer.calculator;

import android.util.Log;

import java.math.BigDecimal;

/**
 * Created by dkramer on 4/6/17.
 */

public class HexCalculatorModel extends BinaryCalculatorModel {

    @Override
    protected boolean isDigit(String token) {
        boolean isDigit = token.matches("[0-9A-F]+");
        return isDigit;
    }

    @Override
    protected void evaluate() {
        // ensure that we're in base 16
        long num = Long.parseLong(m_currentNum, 16);
        evaluate(new BigDecimal(num));
    }
}
