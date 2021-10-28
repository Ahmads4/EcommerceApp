package com.example.shoppingapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "cart_database")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "description") val description: String,
    @Json(name = "image") val image: String,
    var quantity: Int?,
    var itemPrice: Double?
)