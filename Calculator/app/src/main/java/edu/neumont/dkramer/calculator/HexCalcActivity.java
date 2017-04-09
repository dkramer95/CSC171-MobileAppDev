package edu.neumont.dkramer.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HexCalcActivity extends CalcActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_calc);
        init();
    }

    @Override
    protected void init() {
        calcModel = new HexCalculatorModel();
        openValues();
    }

    @Override
    protected String getSaveLastResultString() {
        String str = getString(R.string.binary_last_result);
        return str;
    }

    @Override
    protected String getSaveLastCalcString() {
        String str = getString(R.string.binary_last_calctext);
        return str;
    }
}
