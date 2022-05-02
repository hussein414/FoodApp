package com.example.foodapp.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.ui.db.model.Meal
import com.example.foodapp.ui.db.service.MealDatabase
import com.example.foodapp.ui.fragment.HomeFragment
import com.example.foodapp.ui.viewModel.MealViewModel
import com.example.foodapp.ui.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTubLink: String
    private lateinit var mealViewModel: MealViewModel


    private var mealToSave: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val mealDatabase = MealDatabase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealViewModel = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]


        getMealInformation()
        bindViews()
    }

    private fun bindViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imageMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        mealViewModel.getMealDetail(mealId)
        observerMealDetail()
        onListener()

    }

    private fun onListener() {
        binding.imageYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubLink))
            startActivity(intent)
        }
        binding.buttonFavorite.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this, "meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerMealDetail() {
        mealViewModel.observerMealDetailLiveData().observe(this) { t ->

            val meal = t
            mealToSave = meal

            "Category :${t!!.strCategory}".also { binding.textCategoryInfo.text = it }
            "Area :${t.strArea}".also { binding.textAreaInfo.text = it }
            "instructions :${t.strInstructions}".also { binding.textInstructions.text = it }
            youTubLink = t.strYoutube
        }
    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}