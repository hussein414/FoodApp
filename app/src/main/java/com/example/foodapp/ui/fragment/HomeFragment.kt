package com.example.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.ui.activity.CategoryMealActivity
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.activity.MealActivity
import com.example.foodapp.ui.adapter.CategoriesAdapter
import com.example.foodapp.ui.adapter.MostPopularAdapter
import com.example.foodapp.ui.db.model.PopularMeal
import com.example.foodapp.ui.db.model.Meal
import com.example.foodapp.ui.fragment.bottomshit.MealBottomSheetFragment
import com.example.foodapp.ui.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.foodapp.ui.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.ui.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.ui.fragment.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodapp.ui.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
        popularAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

    }

    private fun bindViews() {
        homeViewModel.getRandomMeal()
        homeViewModel.getPopularItem()
        homeViewModel.getCategoriesItem()
        setPopularRecyclerView()
        setCategoryRecyclerView()
        observer()
        onListener()

    }

    private fun setPopularRecyclerView() {
        binding.RecyclerMealPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun setCategoryRecyclerView() {

        binding.RecyclerCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observer() {
        observerRandomMeal()
        observerPopularItem()
        observerCategory()
    }

    private fun observerRandomMeal() {
        homeViewModel.observerRandomMealLiveData()
            .observe(viewLifecycleOwner) { t ->
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imageRandomMeal)
                this.randomMeal = t
            }
    }

    private fun observerPopularItem() {
        homeViewModel.observerPopularItemLiveData()
            .observe(viewLifecycleOwner) { t ->
                popularAdapter.setMeals(mealList = t as ArrayList<PopularMeal>)
            }
    }

    private fun observerCategory() {
        homeViewModel.observerCategoryItemLiveData()
            .observe(viewLifecycleOwner) { categories ->
                categoriesAdapter.setCategoryList(categories)
            }
    }

    private fun onListener() {
        onRandomMealClick()
        onPopularClickListener()
        onPopularItemLongClick()
        onCategoryClickListener()

    }

    private fun onPopularItemLongClick() {
        popularAdapter.onLongItemClick={meal->
            val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"meal info")
        }
    }

    private fun onRandomMealClick() {
        binding.imageRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onPopularClickListener() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun onCategoryClickListener() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }
}