package com.example.seseshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seseshop.adapters.BasketAdapter;
import com.example.seseshop.models.MagicItem;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private BasketAdapter basketAdapter;
    private TextView basketTotalCost;
    private List<MagicItem> basketItemList;
    private Button checkoutBtn;

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


        ImageView bgImage = findViewById(R.id.basket_bg_img);
        basketTotalCost = findViewById(R.id.basket_total_cost);
        checkoutBtn = findViewById(R.id.basket_checkout_btn);

        Glide.with(this)
                .load("https://cdn.discordapp.com/attachments/971514286029553735/1283750917513941002/image.png?ex=66e421b5&is=66e2d035&hm=edb3452e2ec327e3e9249c3ff62d7e9329e453cf2ff82bd580452dff397c6d9f&")
                .into(bgImage);


        recyclerView = findViewById(R.id.basket_item_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.basketItemList = Data.basketList;
        String basketItemJson = getIntent().getStringExtra("BASKET_ITEMS");
        if (basketItemList == null)
        {
            basketItemList = new ArrayList<>();
        }

        basketAdapter = new BasketAdapter(this, basketItemList);
        recyclerView.setAdapter(basketAdapter);

        updateTotalCost();

        checkoutBtn.setOnClickListener(view ->
        {
            basketItemList.clear();
            basketAdapter.notifyDataSetChanged();
            updateTotalCost();

            Toast.makeText(
                    BasketActivity.this,
                    "Snessks Enjoyss Your Busssinesss Adventurer, Now Enjoyss Your Purchassse",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(BasketActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void updateTotalCost()
    {
        double totalCost = 0.00;
        for (MagicItem magicItem : basketItemList)
        {
            totalCost += magicItem.getItemCost();
        }
        basketTotalCost.setText(String.format("%.2f", totalCost) + " Gp.");
    }
}