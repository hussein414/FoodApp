package com.example.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.ui.db.model.MealByCategory
import com.example.foodapp.ui.db.model.PopularMeal

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {
    private var mealList = ArrayList<PopularMeal>()
    lateinit var onItemClick: ((PopularMeal) -> Unit)
    var onLongItemClick: ((PopularMeal) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealList: ArrayList<PopularMeal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder(
            PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.ImagePopularMeal)

        mealList[position].strMeal.also { holder.binding.TextMealName.text = it }

        holder.binding.ImagePopularMeal.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class MostPopularViewHolder(var binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}