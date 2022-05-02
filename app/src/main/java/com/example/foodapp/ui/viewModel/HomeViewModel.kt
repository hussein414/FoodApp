package com.example.foodapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.ui.db.model.*
import com.example.foodapp.ui.db.retrofit.RetrofitInstance
import com.example.foodapp.ui.db.service.MealDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<PopularMeal>>()
    private var categoryItemLiveData = MutableLiveData<List<Category>>()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private val favoriteMealsLiveData = mealDatabase.mealDao().getAllMeal()

    fun getRandomMeal() {
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    Log.d("TEST", "meal id ${randomMeal.idMeal} name: ${randomMeal.strMeal}")
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItem() {
        RetrofitInstance.foodApi.getPopularItem("Seafood")
            .enqueue(object : Callback<PopularList> {
                override fun onResponse(
                    call: Call<PopularList>,
                    response: Response<PopularList>
                ) {
                    if (response.body() != null) {
                        popularItemLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<PopularList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    fun getCategoriesItem() {
        RetrofitInstance.foodApi.getCategoryItem().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoryItemLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getMealById(id: String) {

        RetrofitInstance.foodApi.getMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observerRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observerPopularItemLiveData(): LiveData<List<PopularMeal>> {
        return popularItemLiveData
    }

    fun observerCategoryItemLiveData(): LiveData<List<Category>> {
        return categoryItemLiveData
    }

    fun observerFavoriteMealsLivData(): LiveData<List<Meal>> {
        return favoriteMealsLiveData
    }

    fun observerMealBottomSheetLiveData(): LiveData<Meal> {
        return bottomSheetMealLiveData
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
        fun deleteMeal(meal: Meal) {
            viewModelScope.launch {
                mealDatabase.mealDao().delete(meal)
            }
        }
    }
}