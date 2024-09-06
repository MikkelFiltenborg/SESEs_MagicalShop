package com.example.seseshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> magicalItemList = Arrays.asList(
                "MagicalItem 1",
                "MagicalItem 2",
                "MagicalItem 3"
        );

        RecyclerView recyclerView = findViewById(R.id.item_list_view);
        MagicalItemAdapter listAdapter = new MagicalItemAdapter(this, magicalItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        ImageView viewBasketBtn = findViewById(R.id.basket_btn);
        viewBasketBtn.setOnClickListener(view ->
        {
            Intent intent = new Intent(MainActivity.this, BasketActivity.class);
            startActivity(intent);
        });
    }
}