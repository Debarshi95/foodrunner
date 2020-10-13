package com.example.dev.foodrunner.ui.forgot_password

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
import com.example.dev.foodrunner.databinding.FragmentForgotPasswordBinding
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.utils.ViewModelFactory
import com.example.dev.foodrunner.utils.setupErrorSnackbar
import com.example.dev.foodrunner.viewmodel.AuthViewModel
import javax.inject.Inject


class ForgotPasswordFragment : Fragment() {

    @Inject
    lateinit var _viewModelFactory: ViewModelFactory

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
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

        binding.btnForgotPassword.setOnClickListener {
            _viewModel.handleForgotPassword(
                binding.etMobileNumber.text.toString(),
                binding.etEmail.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun attachObservers() {
        _viewModel.navigateToResetPasswordFragment.observe(
            viewLifecycleOwner,
            EventObserver {
                if (it) _navController.navigate(R.id.action_forgotPasswordFragment_to_resetPasswordFragment)
            })

        requireView().setupErrorSnackbar(
            viewLifecycleOwner,
            _viewModel.responseSnackBar
        )
    }

}