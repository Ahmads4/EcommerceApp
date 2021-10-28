package com.example.shoppingapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.shoppingapp.R
import com.example.shoppingapp.data.local.Cart
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.FragmentDetailsBinding
import com.example.shoppingapp.ui.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val args by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentDetailsBinding? = null
    private val daoViewModel by viewModels<CartViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)
        val product: Products = args.product

        binding.apply {
            spinnerQuantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var quantity = adapterView?.getItemAtPosition(position).toString().toInt()
                    daoViewModel.updateQuantity(quantity, product.id)

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }


            }
            val cartItem = Cart(
                product.id,
                product.title,
                product.price,
                product.description,
                product.image,
                product.quantity,
                product.itemPrice
            )
            Glide.with(this@DetailsFragment)
                .load(product.image)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(productImage)
            val price: Double = product.price
            val formattedPrice: String = NumberFormat.getCurrencyInstance().format(price)

            productTitle.text = product.title
            productPrice.text = "Price: ${formattedPrice}"
            productDescription.text = product.description
            button.setOnClickListener {
                daoViewModel.addProductToCart(cartItem)
                showToast("${cartItem.title} has been added to your cart")
            }
        }


    }

    private fun showToast(string: String) {
        Toast.makeText(view?.context, string, Toast.LENGTH_SHORT).show()

    }
}