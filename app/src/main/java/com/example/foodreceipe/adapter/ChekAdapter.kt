package com.example.foodreceipe.adapter

import android.graphics.Point
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.fragments.ProfileFragment
import com.example.foodreceipe.model.Chek

class ChekAdapter(var fragment: ProfileFragment, var items: ArrayList<Chek>): BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chek_history, parent, false)
        return ChekViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var chek = items[items.size - 1 - position]
        if (holder is ChekViewHolder){
            val tv_total = holder.tv_total
            val tv_date = holder.tv_date
            val iv_qr_code = holder.iv_qr_code

            tv_total.text = chek.total
            tv_date.text = chek.time
            iv_qr_code.setImageBitmap(chek.image)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ChekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_total: TextView
        val tv_date: TextView
        val iv_qr_code: ImageView

        init {
            tv_total = view.findViewById(R.id.tv_total)
            tv_date = view.findViewById(R.id.tv_date)
            iv_qr_code = view.findViewById(R.id.iv_qr_code)
        }
    }
}