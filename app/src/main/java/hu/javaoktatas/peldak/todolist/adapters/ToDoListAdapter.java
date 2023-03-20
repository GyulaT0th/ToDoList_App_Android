package hu.javaoktatas.peldak.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.internal.MaterialCheckable;

import java.util.List;

import hu.javaoktatas.peldak.todolist.MainActivity;
import hu.javaoktatas.peldak.todolist.R;
import hu.javaoktatas.peldak.todolist.ToDoListActivity;
import hu.javaoktatas.peldak.todolist.model.IModel;
import hu.javaoktatas.peldak.todolist.model.ToDoItem;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    public static OnCheckedChangeListener OnItemCheckedChangeListener;
    private List<ToDoItem> items;
    private int layoutResId;
    private MainActivity activity;
    private IModel model;
    private CompoundButton.OnCheckedChangeListener checkedlistener;

    public ToDoListAdapter(List<ToDoItem> items, int layoutResId, MainActivity activity, IModel model) {
        this.items = items;
        this.layoutResId = layoutResId;
        this.activity = activity;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutResId,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoItem tdi = items.get(position);

        if (tdi.getFontossag() == 0) {
            holder.ivDrawableFontossag.setImageResource(R.drawable.low);
        } else if (tdi.getFontossag() == 1) {
            holder.ivDrawableFontossag.setImageResource(R.drawable.medium);
        } else if (tdi.getFontossag() == 2) {
            holder.ivDrawableFontossag.setImageResource(R.drawable.high);
        }

        holder.tvCim.setText(tdi.getCim());
        holder.tvHatarido.setText(tdi.getEv()+"."+ tdi.getHonap()+"."+tdi.getNap());

        if (tdi.getTeljesitve() == 0) {
            holder.cbTeljesitve.setChecked(false);
        } else if (tdi.getTeljesitve() == 1) {
            holder.cbTeljesitve.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        ToDoItem tdi = items.get(position);
        items.remove(tdi);
        model.removeToDoItem(tdi);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDoItem tdi = items.get(position);
        Intent intent = new Intent(activity, ToDoListActivity.class);
        intent.putExtra("tdi", tdi);
        intent.putExtra("index",position);
        activity.startActivityForResult(intent,MainActivity.RQC_EDIT);
        notifyItemChanged(position);
    }

    public Context getContext() {
        return activity;
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChanged(int position, CompoundButton compoundButton, boolean b);
    }

    public void setOnItemCheckedChangedListener(OnCheckedChangeListener listener) {
        this.OnItemCheckedChangeListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private ImageView ivDrawableFontossag;
        private TextView tvCim;
        private TextView tvHatarido;
        private CheckBox cbTeljesitve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivDrawableFontossag = itemView.findViewById(R.id.ivDrawFontossag);
            tvCim = itemView.findViewById(R.id.tvCim);
            tvHatarido = itemView.findViewById(R.id.tvHatarido);
            cbTeljesitve = itemView.findViewById(R.id.cbTeljesitve);
            cbTeljesitve.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            OnItemCheckedChangeListener.onItemCheckedChanged(getAdapterPosition(), compoundButton, b);
        }
    }



}
