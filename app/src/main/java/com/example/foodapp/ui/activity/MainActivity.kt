package com.example.foodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.ui.db.service.MealDatabase
import com.example.foodapp.ui.viewModel.HomeViewModel
import com.example.foodapp.ui.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.Navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragments)
        val navController = navHostFragment?.findNavController()
        if (navController != null) {
            bottomNavigation.setupWithNavController(navController)
        }

    }

    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProvider = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProvider)[HomeViewModel::class.java]
    }

}