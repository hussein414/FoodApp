package com.example.foodapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.ui.db.model.MealByCategory
import com.example.foodapp.ui.db.model.MealByCategoryList
import com.example.foodapp.ui.db.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {
    val mealLiveData = MutableLiveData<List<MealByCategory>>()
    fun getCategoryByMeal(categoryName: String) {
        RetrofitInstance.foodApi.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    response.body()?.let { mealList ->
                        mealLiveData.postValue(mealList.meals)
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    Log.e("CategoryMealViewModel", t.message.toString())
                }
            })
    }

    fun observerMealByCategory(): LiveData<List<MealByCategory>> {
        return mealLiveData
    }
}