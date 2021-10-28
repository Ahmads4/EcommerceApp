package com.example.shoppingapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface StoreDao {
    @Query("SELECT * FROM cart_database")
    fun getCart(): Flow<List<Cart>>

    @Query("UPDATE cart_database SET quantity = :value where id=:id")
    suspend fun updateQuantity(value: Int, id: Int)

    @Query("SELECT quantity from cart_database where id = :id ")
    suspend fun getQuantity(id: Int): Int

    @Query("UPDATE cart_database SET itemPrice = price*quantity where id = :id ")
    suspend fun calculatePrice(id: Int)

    @Query("SELECT SUM(itemPrice) from cart_database")
    suspend fun getSum(): Double?

    @Query("UPDATE cart_database SET quantity = max(quantity - 1,0) where id = :id ")
    suspend fun subtractQuantity(id: Int): Int

    @Query("UPDATE cart_database SET quantity = min(quantity + 1,10) where id= :id ")
    suspend fun addQuantity(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(cart: Cart)

    @Delete
    suspend fun removeFromCart(cart: Cart)

    @Query("DELETE FROM cart_database")
    suspend fun clearCart()

}