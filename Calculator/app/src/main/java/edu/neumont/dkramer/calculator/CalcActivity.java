package edu.neumont.dkramer.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {
    protected static CalculatorModel calcModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        init();
    }

    protected void init() {
        calcModel = new CalculatorModel();
        Log.i("Info", "Calculator model instance created!");
    }

    public void calcButtonClicked(View view)
    {
        Button btn = (Button)view;
        String btnText = btn.getText().toString();

        calcModel.processToken(btnText);

        // update views
        TextView numView = (TextView)(findViewById(R.id.calcNumView));
        numView.setText(calcModel.getRunningTotalDisplay());

        TextView calcView = (TextView)(findViewById(R.id.calcInputView));
        calcView.setText(calcModel.getCalcText());
    }

    public void binaryModeButtonClicked(View view) {
        Intent binaryCalcActivity = new Intent(this, BinaryCalcActivity.class);
        startActivity(binaryCalcActivity);
    }

    public void hexModeButtonClicked(View view) {
        Intent hexCalcActivity = new Intent(this, HexCalcActivity.class);
        startActivity(hexCalcActivity);
    }

    public void equalsCalcButtonClicked(View view) {
        TextView numView = (TextView)(findViewById(R.id.calcNumView));

        numView.setText(calcModel.getCalcText());
    }

    public void clearCalcButtonClicked(View view) {
        calcModel.clear();

        TextView numView = (TextView)(findViewById(R.id.calcNumView));
        numView.setText("0");

        TextView calcView = (TextView)(findViewById(R.id.calcInputView));
        calcView.setText("");
    }
}
