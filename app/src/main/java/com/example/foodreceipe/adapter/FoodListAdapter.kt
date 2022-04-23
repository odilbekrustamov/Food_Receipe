package com.example.foodreceipe.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodreceipe.R
import com.example.foodreceipe.model.FoodList

class FoodListAdapter(var context: Context, var items: ArrayList<FoodList>): BaseAdapter(){

    lateinit var imageClick: ((FoodList) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_view, parent, false)
        return FoodListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is FoodListViewHolder){
            val tv_more = holder.tv_more
            val iv_photo = holder.iv_photo
            val tv_name = holder.tv_name
            val tv_title = holder.tv_title
            val iv_favorite = holder.iv_favorite
            val iv_favorite_not = holder.iv_favorite_not

            Glide.with(context).load(item.foodImg).into(iv_photo)
            tv_name.text = item.name
            tv_title.text = item.note

            tv_more.setOnClickListener {
                imageClick.invoke(item)
            }


            iv_favorite.setOnClickListener {
                iv_favorite_not.visibility = View.VISIBLE
                iv_favorite.visibility = View.GONE
//                val repository = FavoriteRepository(Application())
//                val favorite = Favorite(path = item.path, id = item.id)
//                repository.saveFavorite(favorite)
            }

            iv_favorite_not.setOnClickListener {
                iv_favorite.visibility = View.VISIBLE
                iv_favorite_not.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FoodListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_more: TextView
        val iv_photo: ImageView
        val tv_name: TextView
        val tv_title: TextView
        val iv_favorite: ImageView
        val iv_favorite_not: ImageView

        init {
            tv_more = view.findViewById(R.id.tv_more)
            iv_photo = view.findViewById(R.id.iv_photo)
            tv_name = view.findViewById(R.id.tv_name)
            tv_title = view.findViewById(R.id.tv_title)
            iv_favorite = view.findViewById(R.id.iv_favorite)
            iv_favorite_not = view.findViewById(R.id.iv_favorite_not)
        }
    }
}