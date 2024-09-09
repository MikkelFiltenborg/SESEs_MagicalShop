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

import com.example.seseshop.adapters.MagicalItemWaresAdapter;
import com.example.seseshop.models.MagicItem;

import java.util.ArrayList;
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

        List<MagicItem> magicalItemList = new ArrayList<>();
        magicalItemList.add(new MagicItem(
                1,
                1,
                3872.00,
                "The Wizards Bane",
                "A mythical greatsword, forged from Adamantium and imbued with a powerful field of anti magic that surrounds its wielder.",
                "images/TWB-LS.png"));
        magicalItemList.add(new MagicItem(2,
                6,
                285.25,
                "Bag of Holding",
                "A special bad that looks like a shoulder satchel, decirated with a face. The bag is capable of storing multiple items, despite it's small size",
                "images/BoH-EQ.png"));
        magicalItemList.add(new MagicItem(3,
                2,
                762.50,
                "Bomarang Daggar",
                "This nifty little bladed weapon can be used as a dual sided dagger or throws, upon which it will fly out 50ft and then return to it's user in an elyptical arch.",
                "images/BD-TW.png"));

        RecyclerView recyclerView = findViewById(R.id.item_list_view);
        MagicalItemWaresAdapter listAdapter =
                new MagicalItemWaresAdapter(this, magicalItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        ImageView viewBasketBtn = findViewById(R.id.basket_iconbtn);
        viewBasketBtn.setOnClickListener(view ->
        {
            Intent intent = new Intent(MainActivity.this, BasketActivity.class);
            startActivity(intent);
        });
    }
}