package com.example.foodreceipe.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodreceipe.R
import com.example.foodreceipe.fragments.ShoppingFragment
import com.example.foodreceipe.model.ShoppingList

class ShoppingAdapter(var fragment: ShoppingFragment, var items: ArrayList<ShoppingList>):BaseAdapter() {
    var summa: Int = 0
    var map1 = HashMap<String, Int>()
    var map2 = HashMap<String, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list_view, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = items[position]

        if (holder is ShopViewHolder){
            val ll_add = holder.ll_add
            val ll_count = holder.ll_count
            val tv_count = holder.tv_count
            val iv_plus = holder.iv_plus
            val iv_minus = holder.iv_minus
            val tv_name = holder.tv_name
            val tv_cost = holder.tv_cost
            val iv_photo = holder.iv_photo

            tv_name.text = item.name
            tv_cost.text = item.price

            Glide.with(fragment).load(item.postImg).into(iv_photo)

           shoppingCount(ll_add, ll_count, tv_count, iv_plus, iv_minus, item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_add: LinearLayout
        val ll_count: LinearLayout
        val iv_plus: ImageView
        val iv_minus: ImageView
        val iv_photo: ImageView
        val tv_count: TextView
        val tv_name: TextView
        val tv_cost: TextView

        init {
            ll_add = view.findViewById(R.id.ll_add)
            ll_count = view.findViewById(R.id.ll_count)
            iv_plus = view.findViewById(R.id.iv_pluc)
            iv_minus = view.findViewById(R.id.iv_minus)
            iv_photo = view.findViewById(R.id.iv_photo)
            tv_count = view.findViewById(R.id.tv_count)
            tv_name = view.findViewById(R.id.tv_name)
            tv_cost = view.findViewById(R.id.tv_cost)
        }
    }

    @SuppressLint("NewApi")
    private fun shoppingCount(llAdd: LinearLayout, llCount: LinearLayout, tvCount: TextView, ivPlus: ImageView, ivMinus: ImageView, item: ShoppingList) {
        var count = 1
        llAdd.setOnClickListener {
            llCount.visibility = View.VISIBLE
            llAdd.visibility = View.GONE
            summa  += item.price.toInt()
            map1.put(item.name, item.price.toInt())
            map2.put(item.name, 1)
            fragment.customerCosts(fragment.requireView(), summa, map1, map2)
        }

        ivPlus.setOnClickListener {
            count ++
            summa += item.price.toInt()
            tvCount.text = count.toString()
            map1.put(item.name,map1.getOrDefault(item.name,0) + item.price.toInt())
            map2.put(item.name,map2.getOrDefault(item.name,0)+1)
            fragment.customerCosts(fragment.requireView(), summa, map1, map2)
        }

        ivMinus.setOnClickListener {
            if (count > 1){
                count --
                summa -= item.price.toInt()
                tvCount.text = count.toString()
                map1.put(item.name,map1.getOrDefault(item.name,0) - item.price.toInt())
                map2.put(item.name,map2.getOrDefault(item.name,0) - 1)
                fragment.customerCosts(fragment.requireView(), summa, map1, map2)
            }else {
                count = 0
                summa -= item.price.toInt()
                map1.put(item.name,map1.getOrDefault(item.name,0) - item.price.toInt())
                map2.put(item.name,map2.getOrDefault(item.name,0) - 1)
                llAdd.visibility = View.VISIBLE
                llCount.visibility = View.GONE
                fragment.customerCosts(fragment.requireView(), summa, map1, map2)
            }
        }
    }
}