package com.example.shoppingapp.data.remote

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class
Products(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "description") val description: String,
    @Json(name = "image") val image: String,
    var quantity: Int?,
    var itemPrice: Double?
) : Parcelable {
}