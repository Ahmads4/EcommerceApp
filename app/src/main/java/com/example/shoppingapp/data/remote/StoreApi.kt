package com.example.shoppingapp.data.remote

import retrofit2.http.GET


interface StoreApi {
    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }

    @GET("products/category/jewelery")
    suspend fun getJewelry(): List<Products>

    @GET("products/category/electronics")
    suspend fun getElectronics(): List<Products>

    @GET("products/category/men's clothing")
    suspend fun getMensClothing(): List<Products>

    @GET("products/category/women's clothing")
    suspend fun getWomensClothing(): List<Products>


}