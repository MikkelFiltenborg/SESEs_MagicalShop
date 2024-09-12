package com.example.seseshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.seseshop.adapters.WaresAdapter;
import com.example.seseshop.models.MagicItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private WaresAdapter waresAdapter;
    private RequestQueue requestQueue;
    private List<MagicItem> magicalItemList;
    private List<MagicItem> waresItemList;
    private List<MagicItem> basketItemList = new ArrayList<>();

    //IP address changed to match local unit IP address.
    private String ApiUrl = "http://192.168.0.78:8989/Item";

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

        ImageView bgImage = findViewById(R.id.main_bg_img);
        Glide.with(this)
                .load("https://cdn.discordapp.com/attachments/326698456339185664/1281641054113763409/Snessks.png?ex=66e3b4ff&is=66e2637f&hm=a0ec4343b96ea024593526d3357c246043a691865e18cdf4bc4f9db9c8f0c711&")
                .into(bgImage);

        requestQueue = Volley.newRequestQueue(this);

        magicalItemList = new ArrayList<>();

        getListItems();
        setAdapterToItemList();

        ImageView basketBtn = findViewById(R.id.main_basket_icon_btn);
        basketBtn.setOnClickListener(view ->
        {
            Data.basketList = this.basketItemList;
            Intent intent = new Intent(MainActivity.this, BasketActivity.class);
            startActivity(intent);
        });
    }

    public void getListItems()
    {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Type listType = new TypeToken<List<MagicItem>>() {}.getType();
                        List<MagicItem> magicItems = new Gson().fromJson(response, listType);
                        magicalItemList = magicItems;
                        setAdapterToItemList();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e(">>> Volley", "onGetErrorResponse", error);
                    }
                });

        final int TIMEOUT_MS = 10000;
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy. DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void addItemToBasket(MagicItem magicItem)
    {
        if (magicItem.getItemAmount() > 0)
        {
            magicItem.setItemAmount(magicItem.getItemAmount() - 1);

            boolean itemInBasket = false;
            for (MagicItem item : basketItemList)
            {
                if (item.getItemName().equals(magicItem.getItemName()))
                {
                    item.setItemAmount(item.getItemAmount() + 1);
                    itemInBasket = true;
                    break;
                }
            }

            if (!itemInBasket)
            {
                MagicItem basketItem = new MagicItem(
                        magicItem.getItemId(),
                        1,
                        magicItem.getItemCost(),
                        magicItem.getItemName(),
                        magicItem.getItemDesc(),
                        magicItem.getItemImg()
                );
                basketItemList.add(basketItem);
            }

            setAdapterToItemList();

            Toast.makeText(this,
                    magicItem.getItemName() + " Added To Basket",
                    Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this,
                    magicItem.getItemName() + " Isn't In Stock",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void setAdapterToItemList()
    {
        recyclerView = findViewById(R.id.item_list_view);
        waresAdapter = new WaresAdapter(this, magicalItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(waresAdapter);
    }

    public int getAvailableItemAmount(MagicItem magicItem)
    {
        return magicItem.getItemAmount();
    }
}