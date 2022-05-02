package com.example.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.ui.db.model.MealByCategory

class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {
    private var mealList = ArrayList<MealByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealList(mealList: List<MealByCategory>) {
        this.mealList = mealList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder{
        return CategoryMealViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imageMeal)

        mealList[position].strMeal.also { holder.binding.textMealName.text = it }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class CategoryMealViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}