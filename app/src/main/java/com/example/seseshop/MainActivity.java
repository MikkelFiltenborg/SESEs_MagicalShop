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
import com.example.seseshop.adapters.MagicalItemWaresAdapter;
import com.example.seseshop.models.MagicItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RequestQueue requestQueue;
    private List<MagicItem> magicalItemList;
    private List<MagicItem> basketItemList = new ArrayList<>();

    //IP address changed to match local unit IP address.
    private String ApiUrl = "http://192.168.0.151:8989/Item";

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

        requestQueue = Volley.newRequestQueue(this);

        magicalItemList = new ArrayList<>();
//        magicalItemList.add(new MagicItem(
//                1,
//                1,
//                3872.00,
//                "The Wizards Bane",
//                "A mythical greatsword, forged from Adamantium and imbued with a powerful field of anti magic that surrounds its wielder.",
//                "images/TWB-LS.png"));
//        magicalItemList.add(new MagicItem(2,
//                6,
//                285.25,
//                "Bag of Holding",
//                "A special bad that looks like a shoulder satchel, decirated with a face. The bag is capable of storing multiple items, despite it's small size",
//                "images/BoH-EQ.png"));
//        magicalItemList.add(new MagicItem(3,
//                2,
//                762.50,
//                "Bomarang Daggar",
//                "This nifty little bladed weapon can be used as a dual sided dagger or throws, upon which it will fly out 50ft and then return to it's user in an elyptical arch.",
//                "images/BD-TW.png"));
        getListItems();
        setAdapterToItemList();

        ImageView viewBasketBtn = findViewById(R.id.basket_iconbtn);
        viewBasketBtn.setOnClickListener(view ->
        {
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
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy. DEFAULT_BACKOFF_MULT));

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
                    magicItem + "Added To Basket",
                    Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this,
                    magicItem + "Isn't In Stock",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void setAdapterToItemList()
    {
        RecyclerView recyclerView = findViewById(R.id.item_list_view);
        MagicalItemWaresAdapter listAdapter =
                new MagicalItemWaresAdapter(this, magicalItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}