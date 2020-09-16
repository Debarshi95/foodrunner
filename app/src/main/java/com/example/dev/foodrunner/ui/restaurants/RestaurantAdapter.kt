package com.example.dev.foodrunner.ui.restaurants

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.dev.foodrunner.databinding.FragmentRestaurantBinding
import com.example.dev.foodrunner.databinding.RecyclerViewItemBinding
import com.example.dev.foodrunner.model.pojo.Restaurant

class RestaurantAdapter(
    private val restaurantList: List<Restaurant>,
    private val listener: SetOnClickListener
) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position])
        holder.itemView.setOnClickListener {
            listener.onClick(restaurantList[position])
        }
    }

    inner class RestaurantViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
            binding.executePendingBindings()
        }
    }

    interface SetOnClickListener {
        fun onClick(restaurant: Restaurant)
    }
}

