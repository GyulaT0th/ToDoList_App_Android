package hu.javaoktatas.peldak.todolist.adapters;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import hu.javaoktatas.peldak.todolist.R;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoListAdapter adapter;

    public RecyclerItemTouchHelper(ToDoListAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.LEFT) {  //törles
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Törlési megerősítés")
                    .setMessage("Biztosan törölni akarod?")
                    .setPositiveButton("Igen", (dialogInterface, i) -> adapter.removeItem(position))
                    .setNegativeButton("Nem", (dialogInterface, i) -> adapter.notifyItemChanged(position));
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {  //szerkesztés
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int iconLeft = 0;
        int iconRight = 0;
        if (dX > 0) {  //jobbra swipe
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
            background = new ColorDrawable(Color.BLUE);
        } else {  //balra swipe
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);
        }

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + iconMargin;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) {
            iconLeft = itemView.getLeft() + iconMargin;
            iconRight = iconLeft+icon.getIntrinsicWidth();
            background.setBounds(itemView.getLeft(),itemView.getTop(),itemView.getLeft()+ (int)dX,itemView.getBottom());
        } else if (dX<0){ //balra swipe
            iconRight = itemView.getRight()-iconMargin;
            iconLeft =  iconRight-icon.getIntrinsicWidth();
            background.setBounds(itemView.getRight()+(int)dX,itemView.getTop(),itemView.getRight(),itemView.getBottom());
        }
        icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
        background.draw(c);
        icon.draw(c);
    }
}
