package com.example.shoppingapp.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.FragmentMensClothingBinding
import com.example.shoppingapp.ui.StoreApiStatus
import com.example.shoppingapp.ui.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MensClothingFragment : Fragment(R.layout.fragment_mens_clothing),
    ProductsAdapter.onItemClickListener {
    private val viewModel by viewModels<StoreViewModel>()
    private var _binding: FragmentMensClothingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mens_clothing, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentMensClothingBinding.bind(view)
        val adapter = ProductsAdapter(this)

        viewModel.networkState.observe(viewLifecycleOwner, {
            binding.errorTextView.isVisible = if (it == StoreApiStatus.ERROR) true else view.isGone
            binding.recyclerView.isVisible = if (it == StoreApiStatus.DONE) true else view.isGone
        })


        viewModel.mensClothing.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            //Disable animations
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }


    }

    override fun onItemClick(product: Products) {
        val action = MensClothingFragmentDirections.actionMensClothingFragmentToDetailsFragment(
            product
        )
        findNavController().navigate(action)
    }
}