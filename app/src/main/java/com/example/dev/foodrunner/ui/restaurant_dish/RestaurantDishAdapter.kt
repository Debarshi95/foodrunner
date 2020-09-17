package com.example.dev.foodrunner.ui.restaurant_dish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dev.foodrunner.databinding.RecyclerDishItemBinding
import com.example.dev.foodrunner.model.pojo.RestaurantDish
import kotlinx.android.synthetic.main.recycler_dish_item.view.*

class RestaurantDishAdapter(
    private val listener: SetOnClickListener,
    private val restaurantDishList: List<RestaurantDish>
) :
    RecyclerView.Adapter<RestaurantDishAdapter.RestaurantDishViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantDishViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDishItemBinding.inflate(inflater,parent,false)
        return RestaurantDishViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return restaurantDishList.size
    }

    override fun onBindViewHolder(holder: RestaurantDishViewHolder, position: Int) {
        holder.bind(restaurantDishList[position])
        holder.itemView.btnAdd.setOnClickListener {
            listener.onClick(restaurantDishList[position],it)
        }
    }

    inner class RestaurantDishViewHolder(private val binding: RecyclerDishItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantDish: RestaurantDish) {
            binding.restaurantDish = restaurantDish
            binding.executePendingBindings()
        }
    }

    interface SetOnClickListener {
        fun onClick(restaurantDish: RestaurantDish,view:View)
    }
}