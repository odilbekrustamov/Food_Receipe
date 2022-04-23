package com.example.foodreceipe.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.model.Product

class ProductAdapter(var context: Context, var items: ArrayList<Product>): BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chek_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ProductViewHolder){
            val tv_product_name = holder.tv_product_name
            val tv_product_cost = holder.tv_product_cost
            val tv_product_kg = holder.tv_product_kg

            tv_product_name.text = item.product_name
            tv_product_cost.text = item.product_prise
            tv_product_kg.text = item.product_kg
            Log.d("@#@#", item.product_prise)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_product_name: TextView
        val tv_product_cost: TextView
        val tv_product_kg: TextView

        init {
            tv_product_name = view.findViewById(R.id.tv_product_name)
            tv_product_cost = view.findViewById(R.id.tv_product_cost)
            tv_product_kg = view.findViewById(R.id.tv_product_kg)
        }
    }
}