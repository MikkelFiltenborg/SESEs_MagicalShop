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

    //    Inflates layout for items
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
//        Inflates layout for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos)
    {
        MagicItem basketItem = basketItemList.get(pos);

        //TODO Add image to item via Glide
        holder.basketItemNameText.setText(basketItem.getItemName());
        holder.basketItemAmountText.setText(String.valueOf(basketItem.getItemAmount()) + " pcs.");
        holder.basketItemCostText.setText(String.format("%.2f", basketItem.getItemCost()) + " Gp.");

//        Info
        holder.basketItemNameText.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("ITEM_NAME", basketItem.getItemName());
            intent.putExtra("ITEM_AMOUNT", basketItem.getItemAmount());
            intent.putExtra("ITEM_COST", basketItem.getItemCost());
            intent.putExtra("ITEM_DESC", basketItem.getItemDesc());
            context.startActivity(intent);
        });

//        Adds 1 to an items amount in the basket list
        holder.addItemBtn.setOnClickListener(view ->
        {
            int currentAmount = basketItem.getItemAmount();
            //TODO find other way to get available amount and fix over-counting
//            int availableAmount = ((BasketActivity) context).getAvailableItemAmount(basketItem);
//            if (currentAmount < availableAmount)
//            {
            int newAddAmount = currentAmount + 1;
            basketItem.setItemAmount(newAddAmount);
            notifyItemChanged(pos);



            Toast.makeText(context,
                    basketItem.getItemName() + " Has Been Added",
                    Toast.LENGTH_SHORT).show();
//            }

//            else
//            {
//                Toast.makeText(context,
//                        "There Isn't Any More " + basketItem.getItemName(),
//                        Toast.LENGTH_SHORT).show();
//            }
            if (context instanceof BasketActivity)
            {
                ((BasketActivity) context).updateTotalCost();
            }
        });

//        Subtracts 1 from the amount on an item in the basket list
        holder.subtractItemBtn.setOnClickListener(view ->
        {
            int currentAmount = basketItem.getItemAmount();
            if (currentAmount > 1)
            {
                int newSubAmount = currentAmount - 1;
                basketItem.setItemAmount(newSubAmount);
                notifyItemChanged(pos);
            }

            else
            {
                Toast.makeText(context,
                        "You Can't Buy Less Than 1 " +
                                basketItem.getItemName(),
                        Toast.LENGTH_SHORT).show();
            }

            if (context instanceof BasketActivity)
            {
                ((BasketActivity) context).updateTotalCost();
            }
        });

//        Removed 1 entire item from the basket list
        holder.removeBtn.setOnClickListener(view ->
        {
            basketItemList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, basketItemList.size());

            Toast.makeText(context,
                    basketItem.getItemName() + " Removed From Basket",
                    Toast.LENGTH_SHORT).show();

            if (context instanceof BasketActivity)
            {
                ((BasketActivity) context).updateTotalCost();
            }
        });
    }

    //    Returns number of items supposed to be displayed in list
    @Override
    public int getItemCount() {return basketItemList.size();}

    //    Holds references to views for the layout of items
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView
                basketItemNameText,
                basketItemAmountText,
                basketItemCostText;
        public ImageView addItemBtn,
                subtractItemBtn,
                removeBtn;

        //        Constructor to create a new item view
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

    //    To be used with adding higher amount to items in basket.
    public int getAvailableItemAmount(MagicItem basketItem)
    {
        return basketItem.getItemAmount();
    }
}