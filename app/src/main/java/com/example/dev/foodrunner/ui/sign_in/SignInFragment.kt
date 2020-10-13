package com.example.dev.foodrunner.ui.sign_in

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
import com.example.dev.foodrunner.databinding.FragmentSignInBinding
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.utils.ViewModelFactory
import com.example.dev.foodrunner.utils.moveToFrag
import com.example.dev.foodrunner.utils.setupErrorSnackbar
import com.example.dev.foodrunner.viewmodel.AuthViewModel
import javax.inject.Inject

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var _viewModelFactory: ViewModelFactory
    private lateinit var _viewModel: AuthViewModel
    private lateinit var _navController: NavController


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).get().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _navController = view.findNavController()

        _viewModel =
            ViewModelProvider(
                _navController.getViewModelStoreOwner(R.id.nav_graph),
                _viewModelFactory
            ).get(AuthViewModel::class.java)

        binding.apply {
            viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        attachObservers()

        binding.btnLogin.setOnClickListener {
            _viewModel.handleSignIn(
                binding.etMobileNumber.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun attachObservers() {
        _viewModel.userLoggedStatus.observe(viewLifecycleOwner, EventObserver {
            if (it) _navController.navigate(R.id.restaurant_nav_graph)
        })

        _viewModel.navigateToRestaurantNavGraph.observe(viewLifecycleOwner,
            EventObserver {
                if (it) _navController.navigate(R.id.action_signInFragment_to_restaurant_nav_graph)
            }
        )
        requireView().setupErrorSnackbar(viewLifecycleOwner, _viewModel.responseSnackBar)

        _viewModel.navigateToSignUpFragment.observe(viewLifecycleOwner, EventObserver {
            requireView().moveToFrag(R.id.signUpFragment, R.id.signInFragment, false)
        })


        _viewModel.navigateToSignUpFragment.observe(viewLifecycleOwner, EventObserver {
            requireView().moveToFrag(R.id.signUpFragment, R.id.signInFragment, false)
        })
        _viewModel.navigateToForgotPasswordFragment.observe(viewLifecycleOwner, EventObserver {
            _navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        })
    }

}