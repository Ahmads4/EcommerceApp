package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.local.Cart
import com.example.shoppingapp.data.local.StoreDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val storeDao: StoreDao) {
    val cartItems: Flow<List<Cart>> = storeDao.getCart()


    suspend fun insertProduct(cart: Cart) {
        storeDao.addToCart(cart)

    }

    suspend fun removeProduct(cart: Cart) {
        storeDao.removeFromCart(cart)

    }

    suspend fun getQuantity(id: Int): Int {
        return storeDao.getQuantity(id)

    }

    suspend fun updateQuantity(value: Int, id: Int) {
        return storeDao.updateQuantity(value, id)

    }

    suspend fun calculatePrice(id: Int) {
        return storeDao.calculatePrice(id)
    }

    suspend fun getTotalPrice(): Double? {
        return storeDao.getSum()
    }

    suspend fun subtractQuantity(id: Int): Int {
        return storeDao.subtractQuantity(id)
    }

    suspend fun addQuantity(id: Int): Int {
        return storeDao.addQuantity(id)
    }
    suspend fun clearCart() {
        storeDao.clearCart()
    }


}