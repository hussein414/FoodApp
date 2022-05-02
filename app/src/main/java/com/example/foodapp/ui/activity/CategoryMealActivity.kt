package com.example.foodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityCategoryMealBinding
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.ui.adapter.CategoryMealAdapter
import com.example.foodapp.ui.fragment.HomeFragment
import com.example.foodapp.ui.viewModel.CategoryMealViewModel

class CategoryMealActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealBinding
    lateinit var categoryMealViewModel: CategoryMealViewModel
    lateinit var categoryMealAdapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
    }

    private fun bindViews() {
        categoryMealViewModel = ViewModelProvider(this)[CategoryMealViewModel::class.java]
        categoryMealViewModel.getCategoryByMeal(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealViewModel.observerMealByCategory().observe(this, Observer { mealList ->
            categoryMealAdapter.setMealList(mealList)
        })
        categoryMealAdapter = CategoryMealAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }


    }
}