package com.example.seseshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seseshop.InfoActivity;
import com.example.seseshop.R;

import java.util.List;

public class MagicalItemBasketAdapter
        extends RecyclerView.Adapter<MagicalItemBasketAdapter.ViewHolder>
{
    private List<String> basketItemList;
    private Context context;

    public MagicalItemBasketAdapter(Context context, List<String> basketItemList)
    {
        this.context = context;
        this.basketItemList = basketItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos)
    {
        String basketItem = basketItemList.get(pos);
        holder.basketItemTextView.setText(basketItem);

        holder.basketItemTextView.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, InfoActivity.class);
            context.startActivity(intent);
        });

        holder.removeBtn.setOnClickListener(view ->
        {
            Toast.makeText(context,
                    basketItem + "Removed From Basket",
                    Toast.LENGTH_SHORT).show();
        });
    }

    public int getItemCount()
    {
        return basketItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView basketItemTextView;
        public Button removeBtn;

        public ViewHolder(View magicalItemView)
        {
            super(magicalItemView);
            basketItemTextView = magicalItemView.findViewById(R.id.basket_magical_item_name_textbtn);
            removeBtn = magicalItemView.findViewById(R.id.basket_magical_item_list_remove_btn);
        }
    }
}
