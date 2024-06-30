package com.example.projectthree_cs360;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<InventoryItem> itemList;
    private DatabaseHelper db;

    public InventoryAdapter(Context context, ArrayList<InventoryItem> itemList, DatabaseHelper db) {
        this.context = context;
        this.itemList = itemList;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InventoryItem item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText(String.valueOf(item.getQuantity()));

        holder.editButton.setOnClickListener(v -> {
            // Logic to edit item
            item.setQuantity(item.getQuantity() + 1); // Example update
            db.updateItem(item);
            notifyItemChanged(position);
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Logic to delete item
            db.deleteItem(item.getId());
            itemList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
