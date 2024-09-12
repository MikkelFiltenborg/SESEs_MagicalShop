package com.example.seseshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seseshop.BasketActivity;
import com.example.seseshop.InfoActivity;
import com.example.seseshop.MainActivity;
import com.example.seseshop.R;
import com.example.seseshop.models.MagicItem;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder>
{
    private List<MagicItem> basketItemList;
    private Context context;

    public BasketAdapter(Context context, List<MagicItem> basketItemList)
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
        MagicItem basketItem = basketItemList.get(pos);

        holder.basketItemNameText.setText(basketItem.getItemName());
        holder.basketItemAmountText.setText(String.valueOf(basketItem.getItemAmount()) + " pcs.");
        holder.basketItemCostText.setText(String.format("%.2f", basketItem.getItemCost()) + " Gp.");

        holder.basketItemNameText.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("ITEM_NAME", basketItem.getItemName());
            intent.putExtra("ITEM_AMOUNT", basketItem.getItemAmount());
            intent.putExtra("ITEM_COST", basketItem.getItemCost());
            intent.putExtra("ITEM_DESC", basketItem.getItemDesc());
            context.startActivity(intent);
        });

        holder.addItemBtn.setOnClickListener(view ->
        {
            int currentAmount = basketItem.getItemAmount();
            int availableAmount = ((MainActivity) context).getAvailableItemAmount(basketItem);

            if (currentAmount < availableAmount)
            {
                int newAmount = currentAmount + 1;
                basketItem.setItemAmount(newAmount);
                notifyItemChanged(pos);
            }

            else
            {
                Toast.makeText(context,
                        "There isn't any more " + basketItem.getItemName(),
                        Toast.LENGTH_SHORT).show();
            }

            if (context instanceof MainActivity)
            {
                ((MainActivity) context).addItemToBasket(basketItem);
            }
        });

//        subtract_item_amount_btn
        holder.subtractItemBtn.setOnClickListener(view ->
        {
            //Impliment function here.
        });

        holder.removeBtn.setOnClickListener(view ->
        {
            basketItemList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, basketItemList.size());

            Toast.makeText(context,
                    basketItem.getItemName() + "Removed From Basket",
                    Toast.LENGTH_SHORT).show();

            if (context instanceof BasketActivity)
            {
                ((BasketActivity) context).updateTotalCost();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return basketItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView
                basketItemNameText,
                basketItemAmountText,
                basketItemCostText;
        public ImageView addItemBtn;
        public ImageView subtractItemBtn;
        public ImageView removeBtn;

        public ViewHolder(View magicItemView)
        {
            super(magicItemView);
            basketItemNameText = magicItemView.findViewById(R.id.basket_magical_item_name_textbtn);
            basketItemAmountText = magicItemView.findViewById(R.id.basket_magical_item_amount_text);
            basketItemCostText = magicItemView.findViewById(R.id.basket_magical_item_cost_text);
            addItemBtn = magicItemView.findViewById(R.id.basket_add_item_amount_btn);
            subtractItemBtn = magicItemView.findViewById(R.id.basket_subtract_item_amount_btn);
            removeBtn = magicItemView.findViewById(R.id.basket_magical_item_list_remove_btn);
        }
    }
}
