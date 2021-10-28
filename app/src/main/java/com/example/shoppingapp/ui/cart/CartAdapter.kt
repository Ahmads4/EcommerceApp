package com.example.shoppingapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.shoppingapp.data.local.Cart
import com.example.shoppingapp.databinding.CartLayoutBinding
import com.example.shoppingapp.ui.CartViewModel
import java.text.NumberFormat

class CartAdapter constructor(
    private val listener: onItemClickListener,
    private val daoViewModel: CartViewModel
) :
    ListAdapter<Cart, CartAdapter.CartViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class CartViewHolder(val binding: CartLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) {
            binding.apply {
                Glide.with(itemView)
                    .load(cart.image)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemImage)
                daoViewModel.getQuantity(cart.id)
                daoViewModel.calculatePrice(cart.id)
                itemTitle.text = cart.title
                val price: Double = cart.price
                val formattedPrice: String = NumberFormat.getCurrencyInstance().format(price)
                itemPrice.text = "Price: ${formattedPrice}"
                itemQuantity.text = cart.quantity.toString()
                removeItem.setOnClickListener {
                    listener.onItemClick(cart)
                }
                addButton.setOnClickListener {
                    listener.onQuantityAdd(cart.id)
                }
                decreaseButton.setOnClickListener {
                    listener.onQuantitySubtract(cart.id)
                }
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(cart: Cart)
        fun onQuantityAdd(id: Int)
        fun onQuantitySubtract(id: Int)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(
            oldItem: Cart,
            newItem: Cart
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Cart,
            newItem: Cart
        ): Boolean {
            return oldItem == newItem
        }
    }
}