package com.example.foodreceipe.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.model.FoodList
import com.example.foodreceipe.model.Step

class CookingStepAdapter(val context: Context, var items: ArrayList<Step>): BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cooking_step_view, parent, false)
        return CookingStepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is CookingStepViewHolder){
            val tv_step = holder.tv_step
            val tv_total = holder.tv_total

           tv_step.text = "Step ${position + 1}"
            Log.d("!@#$", item.step)
            tv_total.text = item.step
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CookingStepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_step: TextView
        val tv_total: TextView

        init {
            tv_step = view.findViewById(R.id.tv_step)
            tv_total = view.findViewById(R.id.tv_total)
        }
    }
}