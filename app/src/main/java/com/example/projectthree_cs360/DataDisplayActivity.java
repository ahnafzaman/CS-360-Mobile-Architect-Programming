package com.example.projectthree_cs360;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DataDisplayActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private ArrayList<InventoryItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = db.getAllItems();
        adapter = new InventoryAdapter(this, itemList, db);
        recyclerView.setAdapter(adapter);
    }

    public void onAddData(View view) {
        // Logic to add new item
        InventoryItem newItem = new InventoryItem(0, "New Item", 1); // Example item
        db.insertItem(newItem);
        itemList.add(newItem);
        adapter.notifyItemInserted(itemList.size() - 1);
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DataDisplayActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


