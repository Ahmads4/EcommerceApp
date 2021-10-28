package com.example.shoppingapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.data.local.Cart
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.ui.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.onItemClickListener {
    private val daoViewModel by viewModels<CartViewModel>()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentCartBinding.bind(view)
        val adapter = CartAdapter(this, daoViewModel)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            //Disable animations
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        daoViewModel.cart.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (adapter.itemCount == 0) {
                binding.recyclerView.isVisible = false
                binding.emptyText.isVisible = true
            } else {
                binding.recyclerView.isVisible = true
                binding.totalPrice.isVisible = true
                binding.button.isVisible = true
                binding.emptyText.isVisible = false
            }
        }

        daoViewModel.itemQuantity.observe(viewLifecycleOwner) {
        }


        daoViewModel.totalPrice.observe(viewLifecycleOwner) {
            val price: Double = it ?: 0.0
            val formattedPrice: String = NumberFormat.getCurrencyInstance().format(price)
            binding.totalPrice.text = "Total Price: ${formattedPrice}"
            updatePrice()
        }


        binding.button.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToCheckoutFragment()
            findNavController().navigate(action)
        }

        if (adapter.itemCount < 1) {
            binding.recyclerView.isVisible = false
            binding.emptyText.isVisible = true
        } else {
            binding.recyclerView.isVisible = true
            binding.totalPrice.isVisible = true
            binding.button.isVisible = true
        }
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(cart: Cart) {
        daoViewModel.removeProductFromCart(cart)
    }

    override fun onQuantityAdd(id: Int) {
        daoViewModel.addQuantity(id)
    }

    override fun onQuantitySubtract(id: Int) {
        daoViewModel.subtractQuantity(id)
    }

    private fun updatePrice() {
        daoViewModel.getTotalPrice()
    }
}