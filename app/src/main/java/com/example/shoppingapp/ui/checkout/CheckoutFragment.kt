package com.example.shoppingapp.ui.checkout

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentCheckoutBinding
import com.example.shoppingapp.ui.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment(R.layout.fragment_checkout) {
    private val viewModel by viewModels<CartViewModel>()
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCheckoutBinding.bind(view)

        binding.button.setOnClickListener {
            viewModel.clearCart()
            showFilterDialog()
        }
        binding.apply {
            nameEditText.addTextChangedListener(textWatcher)
            phoneNumberEditText.addTextChangedListener(textWatcher)
            emailEditText.addTextChangedListener(textWatcher)
            cityEditText.addTextChangedListener(textWatcher)
            areaEditText.addTextChangedListener(textWatcher)
            streetEditText.addTextChangedListener(textWatcher)
            buildingEditText.addTextChangedListener(textWatcher)
            apartmentEditText.addTextChangedListener(textWatcher)
        }
        binding.constraintLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                closeKeyboard(p0)
                return true
            }
        })
    }

    private fun showFilterDialog() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.fragment_checkout_dialog)

        dialog.findViewById<TextView>(R.id.home_button).setOnClickListener {
            val action = CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment()
            findNavController().navigate(action)
            dialog.dismiss()
        }

        dialog.show()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val nameInput = binding.nameEditText.text.toString().trim()
            val phoneInput = binding.phoneNumberEditText.text.toString().trim()
            val emailInput = binding.emailEditText.text.toString().trim()
            val cityInput = binding.cityEditText.text.toString().trim()
            val areaInput = binding.areaEditText.text.toString().trim()
            val streetInput = binding.streetEditText.text.toString().trim()
            val buildingInput = binding.buildingEditText.text.toString().trim()
            val apartmentInput = binding.apartmentEditText.text.toString().trim()
            binding.button.isEnabled =
                nameInput.isNotEmpty() && phoneInput.isNotEmpty() && emailInput.isNotEmpty() && cityInput.isNotEmpty() && areaInput.isNotEmpty() && streetInput.isNotEmpty() && buildingInput.isNotEmpty() && apartmentInput.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun closeKeyboard(view: View?) {
        val imm =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
