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

import com.example.seseshop.InfoActivity;
import com.example.seseshop.R;
import com.example.seseshop.models.MagicItem;

import java.util.List;

public class MagicalItemWaresAdapter
        extends RecyclerView.Adapter<MagicalItemWaresAdapter.ViewHolder>
{
    private List<MagicItem> magicalItemList;
    private Context context;

    public MagicalItemWaresAdapter(Context context, List<MagicItem> magicalItemList)
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
        MagicItem magicalItem = magicalItemList.get(pos);
        holder.itemNameView.setText(magicalItem.getItemName());
        holder.itemAmountView.setText(magicalItem.getItemAmount() + " pcs.");
        holder.itemCostView.setText(String.format("%.2f", magicalItem.getItemCost()) + " Gp.");

        if(magicalItem.getItemImg() != null && !magicalItem.getItemImg().isEmpty())
        {
            //TODO. NOT DEPRICATED! Eventually find alternative to getIdentifier()
            int imgResId = context.getResources().getIdentifier(
                    magicalItem.getItemImg(), "drawable", context.getPackageName());

            if (imgResId != 0)
            {
                holder.itemImgView.setImageResource(imgResId);
            }
            else
            {
                holder.itemImgView.setImageResource(R.drawable.placeholder_img);
            }
        }
        else
        {
            holder.itemImgView.setImageResource(R.drawable.placeholder_img);
        }

        holder.itemNameView.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("ITEM_NAME", magicalItem.getItemName());
            intent.putExtra("ITEM_AMOUNT", magicalItem.getItemAmount());
            intent.putExtra("ITEM_COST", magicalItem.getItemCost());
            intent.putExtra("ITEM_DESC", magicalItem.getItemDesc());
            context.startActivity(intent);
        });

        holder.addItemBtn.setOnClickListener(view ->
                Toast.makeText(context,
                        magicalItem + "Added To Basket",
                        Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount()
    {
        return magicalItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView itemImgView;
        public TextView itemNameView;
        public TextView itemAmountView;
        public TextView itemCostView;
        public Button addItemBtn;

        public ViewHolder(View magicalItemView)
        {
            super(magicalItemView);
            itemImgView = magicalItemView.findViewById(R.id.wares_magical_item_img);
            itemNameView = magicalItemView.findViewById(R.id.wares_magical_item_name_textbtn);
            itemAmountView = magicalItemView.findViewById(R.id.wares_magical_item_amount_text);
            itemCostView = magicalItemView.findViewById(R.id.wares_magical_item_price_text);
            addItemBtn = magicalItemView.findViewById(R.id.wares_magical_item_list_add_btn);
        }
    }
}
