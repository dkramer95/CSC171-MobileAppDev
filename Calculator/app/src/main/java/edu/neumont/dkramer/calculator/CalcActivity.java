package edu.neumont.dkramer.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        TextView numView = (TextView)(findViewById(R.id.calcRunningTotalView));
        numView.setText(calcModel.getRunningTotalDisplay());

        TextView calcView = (TextView)(findViewById(R.id.calcTextView));
        calcView.setText(calcModel.getCalcText());
    }

    public void saveResultToCSV(View view) {
        String path = getFilesDir().getPath().toString() + "/calcResults.csv";
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File(path);
	        String data = calcModel.getRunningTotalDisplay() + ",";

	        if (!file.exists()) {
		        file.createNewFile();
	        }
	        fw = new FileWriter(file.getAbsoluteFile(), true);
	        bw = new BufferedWriter(fw);
	        bw.write(data);

	        Toast.makeText(this, "Saved result to CSV file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
	        Toast.makeText(this, "Failed to save result to file!", Toast.LENGTH_SHORT).show();
        } finally {
	        try {
		        if (bw != null) {
			        bw.close();
		        }
		        if (fw != null) {
			        fw.close();
		        }
	        } catch (IOException e) {
		        Log.e("ERROR", "Failed to close stream(s)");
	        }
        }
    }

    public void binaryModeButtonClicked(View view) {
        Intent binaryCalcActivity = new Intent(this, BinaryCalcActivity.class);
        startActivity(binaryCalcActivity);
    }

    public void hexModeButtonClicked(View view) {
        Intent hexCalcActivity = new Intent(this, HexCalcActivity.class);
        startActivity(hexCalcActivity);
    }

    public void decimalModeButtonClicked(View view) {
        Intent decimalCalcActivity = new Intent(this, CalcActivity.class);
        startActivity(decimalCalcActivity);
    }

    public void equalsCalcButtonClicked(View view) {
        TextView numView = (TextView)(findViewById(R.id.calcRunningTotalView));
        numView.setText(calcModel.getRunningTotalDisplay());
    }

    public void clearCalcButtonClicked(View view) {
        calcModel.clear();

        TextView numView = (TextView)(findViewById(R.id.calcRunningTotalView));
        numView.setText("0");

        TextView calcView = (TextView)(findViewById(R.id.calcTextView));
        calcView.setText("");
    }
}
