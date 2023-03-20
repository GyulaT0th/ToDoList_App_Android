package hu.javaoktatas.peldak.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import hu.javaoktatas.peldak.todolist.model.ToDoItem;

public class ToDoListActivity extends AppCompatActivity {

    private ToDoItem tdi;
    private RadioGroup rgFontossag;
    private RadioButton rbLow;
    private RadioButton rbMedium;
    private RadioButton rbHigh;
    private EditText etCim;
    private EditText etEv;
    private EditText etHonap;
    private EditText etNap;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        rgFontossag = findViewById(R.id.rgFontossag);
        rbLow = findViewById(R.id.rbAlacsony);
        rbMedium = findViewById(R.id.rbKozepes);
        rbHigh = findViewById(R.id.rbMagas);
        etCim = findViewById(R.id.etCim);
        etEv = findViewById(R.id.etEv);
        etHonap = findViewById(R.id.etHonap);
        etNap = findViewById(R.id.etNap);
        intent = getIntent();
        Log.d("TDLAPP", "TDLAct ResId: "+R.id.etEv);

        tdi = (ToDoItem) intent.getSerializableExtra("tdi");
        if (tdi != null) {
            if (tdi.getFontossag() == 0) {
                rbLow.setChecked(true);
            } else if (tdi.getFontossag() == 1) {
                rbMedium.setChecked(true);
            } else if (tdi.getFontossag() == 2) {
                rbHigh.setChecked(true);
            }
            etCim.setText(tdi.getCim());
            etEv.setText(tdi.getEv()+"");
            etHonap.setText(tdi.getHonap()+"");
            etNap.setText(tdi.getNap()+"");
        }

    }

    public void megsem(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void mentes(View view) {
        if (tdi == null) {
            tdi = new ToDoItem();
        }

        if (rbLow.isChecked()) {
            tdi.setFontossag(0);
        } else if (rbMedium.isChecked()) {
            tdi.setFontossag(1);
        } else if (rbHigh.isChecked()) {
            tdi.setFontossag(2);
        }
        tdi.setCim(etCim.getText().toString());
        tdi.setEv(Integer.parseInt(etEv.getText().toString()));
        tdi.setHonap(Integer.parseInt(etHonap.getText().toString()));
        tdi.setNap(Integer.parseInt(etNap.getText().toString()));

        intent.putExtra("tdi", tdi);
        setResult(RESULT_OK,intent);
        finish();
    }
}