package edu.neumont.dkramer.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
    }

    public void calcButtonClicked(View view)
    {
        Button btn = (Button)view;
        String text = btn.getText().toString();
        Log.i("Info", "Button: " + text + ", was pressed!");
    }
}
