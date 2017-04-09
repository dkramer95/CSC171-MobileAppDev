package edu.neumont.dkramer.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.math.BigDecimal;

public class CalcActivity extends AppCompatActivity {
    protected static CalculatorModel calcModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        init();
    }

    @Override
    protected void onPause() {
	    saveValues();
	    super.onPause();
    }

    protected void openValues() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	    String lastResult = sharedPref.getString(getSaveLastResultString(), "0");
	    String lastCalcText = sharedPref.getString(getSaveLastCalcString(), "");

	    BigDecimal value = new BigDecimal(lastResult);
	    calcModel.setValue(value);
	    calcModel.setCalcText(lastCalcText);

	    updateView(lastResult, lastCalcText);
    }

    protected void saveValues() {
	    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPref.edit();
	    editor.putString(getSaveLastResultString(), calcModel.getRunningTotal().toString());
	    editor.putString(getSaveLastCalcString(), calcModel.getCalcText());
	    editor.commit();
    }

    protected String getSaveLastResultString() {
	    String str = getString(R.string.decimal_last_result);
	    return str;
    }

    protected String getSaveLastCalcString() {
	    String str = getString(R.string.decimal_last_calctext);
	    return str;
    }

    protected void init() {
        calcModel = new CalculatorModel();
	    openValues();
    }

    public void calcButtonClicked(View view)
    {
        Button btn = (Button)view;
        String btnText = btn.getText().toString();

        calcModel.processToken(btnText);

//        // update views
	    updateView(calcModel.getRunningTotalDisplay(), calcModel.getCalcText());
    }

    protected void updateView(String numViewText, String calcViewText) {
	    TextView numView = (TextView)(findViewById(R.id.calcRunningTotalView));
	    numView.setText(numViewText);

	    TextView calcView = (TextView)(findViewById(R.id.calcTextView));
	    calcView.setText(calcViewText);
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
