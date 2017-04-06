package edu.neumont.dkramer.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BinaryCalcActivity extends CalcActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_calc);
        init();
    }

    @Override
    protected void init() {
        calcModel = new BinaryCalculatorModel();
    }
}
