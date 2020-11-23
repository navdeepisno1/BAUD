package com.suvidha.bazaaratyourdwaar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

public class RVAdapter_Result extends RecyclerView.Adapter<RVAdapter_Result.RVView>{
    Context context;
    List<SearchProducts.Product> products;
    public RVAdapter_Result()
    {

    }

    public RVAdapter_Result(Context context, List<SearchProducts.Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public RVView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search_result, parent, false);
        return new RVView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVView holder, int position) {
        final SearchProducts.Product product = products.get(position);
        holder.textView_name.setText(product.getProductName());
        holder.textView_category.setText(product.getProductCategory());

        holder.textView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewItem.class);
                intent.putExtra("itemId",product.getProductId());
                context.startActivity(intent);
            }
        });

        holder.textView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewItem.class);
                intent.putExtra("itemId",product.getProductId());
                context.startActivity(intent);
            }
        });

        holder.imageView_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewItem.class);
                intent.putExtra("itemId",product.getProductId());
                context.startActivity(intent);
            }
        });

        Glide.with(context).load(product.getProductIcon()).into(holder.imageView_icon);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class RVView extends RecyclerView.ViewHolder
    {
        TextView textView_name,textView_category;
        ImageView imageView_icon;
        public RVView(@NonNull View itemView) {
            super(itemView);

            textView_category = itemView.findViewById(R.id.rv_item_search_result_tv_item_categroy);
            textView_name = itemView.findViewById(R.id.rv_item_search_result_tv_item_name);
            imageView_icon = itemView.findViewById(R.id.rv_item_search_result_iv_icon);

        }
    }
}
