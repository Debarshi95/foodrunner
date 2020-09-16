package com.example.dev.foodrunner.ui.restaurants

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev.foodrunner.R
import com.example.dev.foodrunner.databinding.FragmentRestaurantBinding
import com.example.dev.foodrunner.model.pojo.Restaurant
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.utils.ViewModelFactory
import com.example.dev.foodrunner.utils.setupSnackbar
import javax.inject.Inject


class RestaurantFragment : Fragment(), RestaurantAdapter.SetOnClickListener {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RestaurantViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).get().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java).apply {
                binding.lifecycleOwner = viewLifecycleOwner
            }

        init()
        attachObservers()

        binding.toolbar.setupWithNavController(findNavController(), binding.drawerLayout)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        viewModel.getRestaurants()
    }

    private fun attachObservers() {
        requireView().setupSnackbar(viewLifecycleOwner, viewModel.snackbarMsgEvent)

        viewModel.restaurantList.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                setupAdapter(it)
            }
        })
        viewModel.moveToDishFragEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                requireView().findNavController().navigate(
                    R.id.restaurantDishFragment,
                    bundleOf(Pair("restaurant", it))
                )
            }
        })

    }

    private fun setupAdapter(list: List<Restaurant>) {
        val adapter = RestaurantAdapter(list, this)
        binding.resRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }
    }

    override fun onClick(restaurant: Restaurant) {
        viewModel.setMoveToDishFragEvent(restaurant)
    }


}