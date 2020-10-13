package com.example.dev.foodrunner.ui.sign_up

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.dev.foodrunner.EventObserver
import com.example.dev.foodrunner.R
import com.example.dev.foodrunner.databinding.FragmentSignUpBinding
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.utils.ViewModelFactory
import com.example.dev.foodrunner.utils.setupErrorSnackbar
import com.example.dev.foodrunner.viewmodel.AuthViewModel
import javax.inject.Inject


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var _viewModel: AuthViewModel
    private lateinit var _navController: NavController

    @Inject
    lateinit var _viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).get().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _navController = view.findNavController()

        _viewModel = ViewModelProvider(
            _navController.getViewModelStoreOwner(R.id.nav_graph),
            _viewModelFactory
        ).get(
            AuthViewModel::class.java
        )
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = _viewModel
        }
        attachObservers()

        binding.btnSignUp.setOnClickListener {
            val data = arrayOf(
                binding.etSignUpName.text.toString(),
                binding.etSignUpEmail.text.toString(),
                binding.etSignUpEmail.text.toString(),
                binding.etSignUpAddress.text.toString(),
                binding.etSignUpPassword.text.toString(),
                binding.etSignUpCnfPassword.text.toString()
            )
            _viewModel.handleSignUp(*data)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun attachObservers() {
        _viewModel.userLoggedStatus.observe(viewLifecycleOwner, EventObserver {
            if (it) _navController.navigate(R.id.action_signUpFragment_to_restaurant_nav_graph)
        })

        requireView().setupErrorSnackbar(viewLifecycleOwner, _viewModel.responseSnackBar)

        _viewModel.navigateToSignInFragment.observe(viewLifecycleOwner, EventObserver {
            _navController.apply {
                popBackStack(R.id.signInFragment, true)
                navigate(R.id.signInFragment)
            }

        })

        _viewModel.navigateToRestaurantNavGraph.observe(
            viewLifecycleOwner,
            EventObserver {
                if (it) _navController.navigate(R.id.action_signUpFragment_to_restaurant_nav_graph)

            }
        )

    }
}