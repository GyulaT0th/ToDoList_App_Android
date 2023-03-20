package hu.javaoktatas.peldak.todolist;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hu.javaoktatas.peldak.todolist.adapters.RecyclerItemTouchHelper;
import hu.javaoktatas.peldak.todolist.adapters.ToDoListAdapter;
import hu.javaoktatas.peldak.todolist.adapters.ToDoListAdapter.OnCheckedChangeListener;
import hu.javaoktatas.peldak.todolist.comparators.ComparatorABC;
import hu.javaoktatas.peldak.todolist.comparators.ComparatorDatCsok;
import hu.javaoktatas.peldak.todolist.comparators.ComparatorDatNov;
import hu.javaoktatas.peldak.todolist.databinding.ActivityMainBinding;
import hu.javaoktatas.peldak.todolist.model.DBModel;
import hu.javaoktatas.peldak.todolist.model.IModel;
import hu.javaoktatas.peldak.todolist.model.ToDoItem;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int RQC_NEW = 1;
    public static final int RQC_EDIT = 2;
    private static final int RQC_RENDEZES = 3;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<ToDoItem> tasks;
    private RecyclerView rvItems;
    private ToDoListAdapter adapter;
    private IModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                ujElem();
            }
        });

        model = new DBModel(this);
        tasks = model.getAllToDoItem();

        rvItems = findViewById(R.id.rvItems);
        adapter = new ToDoListAdapter(tasks, R.layout.list_item, this, model);
        adapter.setOnItemCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onItemCheckedChanged(int position, CompoundButton compoundButton, boolean b) {
                teljesitveChechBox(position, b);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(rvItems);

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        lejartEllenor();

    }

    private void lejartEllenor() {
        Calendar date = new GregorianCalendar();
        Log.d("TDLAPP", "Dátum"+date.get(Calendar.YEAR)+"-"+date.get(Calendar.MONTH)+"-"+date.get(Calendar.DAY_OF_MONTH));
        List<ToDoItem> lejarnak = new ArrayList<>();
        String lejaroToDokS = "";
        for (ToDoItem tdi : tasks) {
            if (date.get(Calendar.YEAR) == tdi.getEv() && (date.get(Calendar.MONTH)+1) == tdi.getHonap() && date.get(Calendar.DAY_OF_MONTH) == tdi.getNap()) {
                lejarnak.add(tdi);
                lejaroToDokS += tdi.getCim()+"\n";
            }
        }

        if (!lejaroToDokS.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Ma lejáró teendők.")
                    .setMessage(lejaroToDokS)
                    .setPositiveButton("Ok", (dialogInterface, i) -> {});
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void teljesitveChechBox(int position, boolean b) {
        ToDoItem tdi = tasks.get(position);
        Log.d("TDLAPP", "TELCB"+tdi);
        if (b) {
            tdi.setTeljesitve(1);
        } else {
            tdi.setTeljesitve(0);
        }
        tasks.set(position, tdi);
        model.saveOrUpdateToDoItem(tdi);
        Log.d("TDLAPP", "TELCB"+tdi);
        Log.d("TDLAPP", "TELCBT"+tasks.get(position));
        Log.d("TDLAPP", "TELCBDB"+model.getToDoItem(tdi.getId()));
    }

    public void ujElem() {
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivityForResult(intent, RQC_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {   //ha mentést nyomott, akkor...
            ToDoItem tdi = (ToDoItem) data.getSerializableExtra("tdi");

            if (requestCode == RQC_NEW) {  //ha új elem felvétel miatt indult az activity
                tasks.add(tdi);
                model.saveOrUpdateToDoItem(tdi);
                adapter.notifyItemInserted(tasks.size() - 1);   //az adapternek kell szólni, hogy rajzolja újra a RecyclerView-t
            } else if (requestCode == RQC_EDIT) { //ha szerkesztés miatt indult az activity
                //Toast.makeText(MainActivity.this,"elem szerekesztés, itt módosítjuk az elemet.",Toast.LENGTH_LONG).show();
                int position = data.getIntExtra("index", -1);
                tasks.set(position, tdi);
                model.saveOrUpdateToDoItem(tdi);
                adapter.notifyItemChanged(position);
            } else if (requestCode == RQC_RENDEZES) { //ha szerkesztés miatt indult az activity
                //Toast.makeText(MainActivity.this,"elem szerekesztés, itt módosítjuk az elemet.",Toast.LENGTH_LONG).show();
                int rendTipus = data.getIntExtra("Rendezes", 0);
                if (rendTipus == 0) {
                    Collections.sort(tasks, new ComparatorABC());
                    adapter.notifyDataSetChanged();
                } else if (rendTipus == 1) {
                    Collections.sort(tasks, new ComparatorDatCsok());
                    adapter.notifyDataSetChanged();
                } else if (rendTipus == 2) {
                    Collections.sort(tasks, new ComparatorDatNov());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MySetingsActivity.class);
            startActivityForResult(intent, RQC_RENDEZES);
            return true;
        } else if (id == R.id.RendezNev) {

            Collections.sort(tasks, new ComparatorABC());

            Log.d("TDLAPP", "TASKS: "+tasks);

            /*for (int i = 0; i < tasks.size(); i++) {
                adapter.notifyItemRemoved(i);
            }
            for (int i = 0; i < tasks.size(); i++) {
                adapter.notifyItemChanged(i);
            } */

            adapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.RendezDatumCsok) {
            Collections.sort(tasks, new ComparatorDatCsok());
            adapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.RendezDatumNov) {
            Collections.sort(tasks, new ComparatorDatNov());
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}