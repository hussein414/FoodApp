package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.ui.db.model.Category


class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoryList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null
    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imageCategory)

        categoryList[position].strCategory.also { holder.binding.textCategoryName.text = it }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}