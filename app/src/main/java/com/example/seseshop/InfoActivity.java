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

        ImageView bgImage = findViewById(R.id.info_bg_img);
        Glide.with(this)
                .load("https://cdn.discordapp.com/attachments/326698456339185664/1281641055388962827/sese_shop_info.png?ex=66e3b4ff&is=66e2637f&hm=bb19f359da4a90318916be6de0481f3abf6d2f79607c92dfcd56862c4377af68&")
                .into(bgImage);

        itemNameView = findViewById(R.id.menu_header_txt);
        itemImgView = findViewById(R.id.info_image);
        itemDescView = findViewById(R.id.info_layout_3).findViewById(R.id.info_desc_text);
        addBtn = findViewById(R.id.wares_magical_item_info_add_btn);

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("ITEM_NAME");
        String itemImgPath = intent.getStringExtra("ITEM_IMG_PATH");
        String itemDesc = intent.getStringExtra("ITEM_DESC");

        if (itemName != null)
        {
            itemNameView.setText(itemName);
        }

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

        if (itemDesc != null)
        {
            itemDescView.setText(itemDesc);
        }

        addBtn.setOnClickListener(view ->
        {
            //TODO Add function to add chosen item from wares list to basket list.
        });
    }
}