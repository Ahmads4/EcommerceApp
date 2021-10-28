package com.example.shoppingapp.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.ProductLayoutBinding
import java.text.NumberFormat

class ProductsAdapter constructor(
    private val listener: onItemClickListener,
) : ListAdapter<Products, ProductsAdapter.ProductsViewHolder>(
    DiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ProductsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currentItem = getItem(position)



        if (currentItem != null) {
            holder.bind(currentItem)
        }


    }


    inner class ProductsViewHolder(val binding: ProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemClick(item)
                }
            }

        }


        fun bind(product: Products) {
            binding.apply {
                Glide.with(itemView)
                    .load(product.image)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(productImage)
                val price: Double = product.price
                val formattedPrice: String = NumberFormat.getCurrencyInstance().format(price)
                productTitle.text = product.title
                productPrice.text = "Price: ${formattedPrice} "
            }

        }


    }

    interface onItemClickListener {
        fun onItemClick(product: Products)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(
            oldItem: Products,
            newItem: Products
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Products,
            newItem: Products
        ): Boolean {
            return oldItem == newItem
        }
    }


}