package com.example.seseshop;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seseshop.adapters.BasketAdapter;
import com.example.seseshop.models.MagicItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BasketActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.basket), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String basketItemJson = getIntent().getStringExtra("BASKET_ITEMS");
        List<MagicItem> basketItemList = new Gson().fromJson(basketItemJson,
                new TypeToken<List<MagicItem>>() {}.getType());

//        List<String> basketItemList = Arrays.asList(
//                "MagicalItemName 1",
//                "MagicalItemName 2",
//                "MagicalItemName 3"
//        );

        RecyclerView recyclerView = findViewById(R.id.basket_item_view);
        BasketAdapter magicalItemBasketAdapter = new BasketAdapter(this, basketItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(magicalItemBasketAdapter);
    }
}