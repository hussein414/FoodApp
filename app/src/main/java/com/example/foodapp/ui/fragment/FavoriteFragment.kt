package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FragmentFavoriteBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.adapter.FavoriteMealAdapter
import com.example.foodapp.ui.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealAdapter: FavoriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        favoriteMealAdapter = FavoriteMealAdapter()

        viewModel.observerFavoriteMealsLivData().observe(requireActivity()) { meals ->
            favoriteMealAdapter.differ.submitList(meals)
        }

        binding.RecyclerFavorite.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteMealAdapter
        }


        deleteItemFavorite()

    }

    private fun deleteItemFavorite() {

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteMealAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal Delete", Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(favoriteMealAdapter.differ.currentList[position])
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.RecyclerFavorite)
    }

}