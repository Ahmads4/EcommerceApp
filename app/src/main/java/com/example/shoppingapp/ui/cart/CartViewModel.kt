package com.example.shoppingapp.ui

import androidx.lifecycle.*
import com.example.shoppingapp.data.local.Cart
import com.example.shoppingapp.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {
    val cart: LiveData<List<Cart>> = repository.cartItems.asLiveData()
    private val _itemQuantity = MutableLiveData<Int>()
    val itemQuantity: MutableLiveData<Int> = _itemQuantity
    private val _itemPrice = MutableLiveData<Double>()
    val itemPrice: MutableLiveData<Double> = _itemPrice
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: MutableLiveData<Double> = _totalPrice

    init {
        getTotalPrice()
    }


    fun addProductToCart(cart: Cart) {
        viewModelScope.launch {
            repository.insertProduct(cart)
        }

    }

    fun removeProductFromCart(cart: Cart) {
        viewModelScope.launch {
            repository.removeProduct(cart)
        }

    }

    fun updateQuantity(value: Int, id: Int) {
        viewModelScope.launch {
            repository.updateQuantity(value, id)
        }

    }


    fun getQuantity(id: Int) {
        viewModelScope.launch {
            _itemQuantity.setValue(repository.getQuantity(id))
        }

    }

    fun calculatePrice(id: Int) {
        viewModelScope.launch {
            repository.calculatePrice(id)
        }
    }

    fun getTotalPrice() {
        viewModelScope.launch {
            _totalPrice.value = repository.getTotalPrice()
        }
    }

    fun subtractQuantity(id: Int) {
        viewModelScope.launch {
            repository.subtractQuantity(id)
        }
    }

    fun addQuantity(id: Int) {
        viewModelScope.launch {
            repository.addQuantity(id)
        }
    }
    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}


class CartViewModelFactory @Inject constructor(
    private val repository: CartRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }


}