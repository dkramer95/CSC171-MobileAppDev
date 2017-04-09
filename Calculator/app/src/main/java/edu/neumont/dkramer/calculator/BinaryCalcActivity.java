package edu.neumont.dkramer.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class BinaryCalcActivity extends CalcActivity {

    protected static final int[] bitButtons = {
		R.id.bitButton0,  R.id.bitButton1,  R.id.bitButton2,  R.id.bitButton3,
		R.id.bitButton4,  R.id.bitButton5,  R.id.bitButton6,  R.id.bitButton7,
		R.id.bitButton8,  R.id.bitButton9,  R.id.bitButton10, R.id.bitButton11,
		R.id.bitButton12, R.id.bitButton13, R.id.bitButton14, R.id.bitButton15,
		R.id.bitButton16, R.id.bitButton17, R.id.bitButton18, R.id.bitButton19,
		R.id.bitButton20, R.id.bitButton21, R.id.bitButton22, R.id.bitButton23,
		R.id.bitButton24, R.id.bitButton25, R.id.bitButton26, R.id.bitButton27,
		R.id.bitButton28, R.id.bitButton29, R.id.bitButton30, R.id.bitButton31,
		R.id.bitButton32, R.id.bitButton33, R.id.bitButton34, R.id.bitButton35,
		R.id.bitButton36, R.id.bitButton37, R.id.bitButton38, R.id.bitButton39,
		R.id.bitButton40, R.id.bitButton41, R.id.bitButton42, R.id.bitButton43,
		R.id.bitButton44, R.id.bitButton45, R.id.bitButton46, R.id.bitButton47,
		R.id.bitButton48, R.id.bitButton49, R.id.bitButton50, R.id.bitButton51,
		R.id.bitButton52, R.id.bitButton53, R.id.bitButton54, R.id.bitButton55,
		R.id.bitButton56, R.id.bitButton57, R.id.bitButton58, R.id.bitButton59,
		R.id.bitButton60, R.id.bitButton61, R.id.bitButton62, R.id.bitButton63,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_calc);
	    resetBitButtons();
    }

    @Override
    protected void init() {
        calcModel = new BinaryCalculatorModel();
    }

    protected void resetBitButtons() {
	    int bitValue = 0;
        int j = 0;

	    for(int buttonId : bitButtons) {
		    bitValue = (int)Math.pow(bitValue, j);
		    setBitButton(buttonId, 0, bitValue);
		    ++j;
        }
    }

    public void setBitButton(int bitButtonId, int state, int bitValue) {
	    if (!(state == 0 || state == 1)) {
		    throw new IllegalArgumentException("INVALID BIT VALUE");
	    }
	    Button bitButton = (Button)findViewById(bitButtonId);
	    bitButton.setText("" + state);
	    bitButton.setTag(bitValue);
    }

    public void setBit(View view) {
        Button button = (Button)view;
        int bitNum = Integer.parseInt(button.getTag().toString());
    }
}
