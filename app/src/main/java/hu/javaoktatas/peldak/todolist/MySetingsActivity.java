package hu.javaoktatas.peldak.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MySetingsActivity extends AppCompatActivity {

    private RadioGroup rgRendezes;
    private RadioButton rbABC;
    private RadioButton rbDatCsok;
    private RadioButton rbDatNov;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setings);

        rgRendezes = findViewById(R.id.rgRendezes);
        rbABC = findViewById(R.id.rbABC);
        rbDatCsok = findViewById(R.id.rbDatCsok);
        rbDatNov = findViewById(R.id.rbDatNov);
        intent = getIntent();

    }

    public void kesz(View view) {
        if (rbABC.isChecked()) {
            intent.putExtra("Rendezes", 0);
        } else if (rbDatCsok.isChecked()) {
            intent.putExtra("Rendezes", 1);
        } else if (rbDatNov.isChecked()) {
            intent.putExtra("Rendezes", 2);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}