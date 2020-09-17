package com.example.dev.foodrunner.ui.restaurant_dish

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev.foodrunner.R
import com.example.dev.foodrunner.databinding.FragmentRestaurantDishBinding
import com.example.dev.foodrunner.model.pojo.Restaurant
import com.example.dev.foodrunner.model.pojo.RestaurantDish
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.utils.ViewModelFactory
import com.example.dev.foodrunner.utils.bindImageLoading
import javax.inject.Inject


class RestaurantDishFragment : Fragment(), RestaurantDishAdapter.SetOnClickListener {
    private var _binding: FragmentRestaurantDishBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RestaurantDishViewModel
    private lateinit var arg: Restaurant


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arg = arguments?.let {
            it.get("restaurant") as Restaurant
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        _binding = FragmentRestaurantDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(RestaurantDishViewModel::class.java)
        init()
        attachObservers()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        viewModel.getDishes(arg.id.toInt())
    }

    private fun attachObservers() {
        viewModel.restaurantDish.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                setupAdapter(it)
            }

        })
    }

    private fun setupImgView() {
        binding.imgDish.bindImageLoading(arg.imgUrl)
        binding.restaurantName.text = arg.name
    }

    private fun setupAdapter(list: List<RestaurantDish>) {
        setupImgView()
        val adapter = RestaurantDishAdapter(this, list)
        binding.dishRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }
    }

    override fun onClick(restaurantDish: RestaurantDish, view: View) {
        (view as Button).apply {
            if (text.startsWith("A")) {
                textSize = 12f
                text = getString(R.string.remove)
            } else {
                textSize = 14f
                text = getString(R.string.add)
            }

        }
    }

}