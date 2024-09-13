package com.example.seseshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class InfoActivity extends AppCompatActivity
{
    private TextView itemNameView;
    private ImageView itemImgView;
    private TextView itemDescView;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Sets ImageView to display image fetches from URL
        ImageView bgImage = findViewById(R.id.info_bg_img);
        Glide.with(this)
                .load("https://cdn.discordapp.com/attachments/971514286029553735/1284082508132122624/sese_shop_info.png?ex=66e55687&is=66e40507&hm=f91d64c8947849be41742fb3feed02b0bc91774255b5bfb254b670b9f2fe329d&")
                .into(bgImage);

        itemNameView = findViewById(R.id.menu_header_txt);
        itemImgView = findViewById(R.id.info_image);
        itemDescView = findViewById(R.id.info_layout_3).findViewById(R.id.info_desc_text);
        addBtn = findViewById(R.id.wares_magical_item_info_add_btn);

//        Set reference to UI elements in layout
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("ITEM_NAME");
        String itemImgPath = intent.getStringExtra("ITEM_IMG_PATH");
        String itemDesc = intent.getStringExtra("ITEM_DESC");

//        Ensures TextView for item name is the name of the item
        if (itemName != null)
        {
            itemNameView.setText(itemName);
        }

//        Load image based on dynamic resource-path
        //TODO Change to use Glide for item images
        if(itemImgPath != null && !itemImgPath.isEmpty())
        {
            //TODO. NOT DEPRICATED! find alternative to getIdentifier()
            int imgResId = getResources().getIdentifier(
                    itemImgPath, "drawable", getPackageName());
            itemImgView.setImageResource(imgResId);
        }
        else
        {
            itemImgView.setImageResource(R.drawable.placeholder_img);
        }

//        Ensures TextView for description is the description of the item
        if (itemDesc != null)
        {
            itemDescView.setText(itemDesc);
        }

//        Adds item to basket from info page
        addBtn.setOnClickListener(view ->
        {
            //TODO Add function to add chosen item from wares list to basket list.
        });
    }
}