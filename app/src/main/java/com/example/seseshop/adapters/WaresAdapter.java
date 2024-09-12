package com.example.seseshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seseshop.InfoActivity;
import com.example.seseshop.MainActivity;
import com.example.seseshop.R;
import com.example.seseshop.models.MagicItem;

import java.util.List;

public class WaresAdapter extends RecyclerView.Adapter<WaresAdapter.ViewHolder>
{
    private List<MagicItem> magicalItemList;
    private Context context;

    public WaresAdapter(Context context, List<MagicItem> magicalItemList)
    {
        this.context = context;
        this.magicalItemList = magicalItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wares_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos)
    {
        MagicItem magicItem = magicalItemList.get(pos);
        holder.waresItemNameView.setText(magicItem.getItemName());
        holder.waresItemAmountView.setText(magicItem.getItemAmount() + " pcs.");
        holder.waresItemCostView.setText(String.format("%.2f", magicItem.getItemCost()) + " Gp.");

        if(magicItem.getItemImg() != null && !magicItem.getItemImg().isEmpty())
        {
            //TODO. NOT DEPRICATED! Eventually find alternative to getIdentifier()
            int imgResId = context.getResources().getIdentifier(
                    magicItem.getItemImg(), "drawable", context.getPackageName());

            if (imgResId != 0)
            {
                holder.waresItemImgView.setImageResource(imgResId);
            }
            else
            {
                holder.waresItemImgView.setImageResource(R.drawable.placeholder_img);
            }
        }
        else
        {
            holder.waresItemImgView.setImageResource(R.drawable.placeholder_img);
        }

        holder.waresItemNameView.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("ITEM_NAME", magicItem.getItemName());
            intent.putExtra("ITEM_AMOUNT", magicItem.getItemAmount());
            intent.putExtra("ITEM_COST", magicItem.getItemCost());
            intent.putExtra("ITEM_DESC", magicItem.getItemDesc());
            context.startActivity(intent);
        });

        holder.addItemBtn.setOnClickListener(view ->
                {
                    if (context instanceof MainActivity)
                    {
                        ((MainActivity) context).addItemToBasket(magicItem);
                    }
                });
    }

    @Override
    public int getItemCount()
    {
        return magicalItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView waresItemImgView;
        public TextView waresItemNameView;
        public TextView waresItemAmountView;
        public TextView waresItemCostView;
        public Button addItemBtn;

        public ViewHolder(View magicalItemView)
        {
            super(magicalItemView);
            waresItemImgView = magicalItemView.findViewById(R.id.wares_magical_item_img);
            waresItemNameView = magicalItemView.findViewById(R.id.wares_magical_item_name_textbtn);
            waresItemAmountView = magicalItemView.findViewById(R.id.wares_magical_item_amount_text);
            waresItemCostView = magicalItemView.findViewById(R.id.wares_magical_item_price_text);
            addItemBtn = magicalItemView.findViewById(R.id.wares_magical_item_list_add_btn);
        }
    }
}
