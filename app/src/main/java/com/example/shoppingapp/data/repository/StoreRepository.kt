package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.remote.StoreApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StoreRepository @Inject constructor(private val storeApi: StoreApi) {
    suspend fun getJewelryProducts(): List<Products> {
        return storeApi.getJewelry()

    }

    suspend fun getElectronicsProducts(): List<Products> {
        return storeApi.getElectronics()

    }

    suspend fun getClothingMen(): List<Products> {
        return storeApi.getMensClothing()

    }

    suspend fun getClothingWomen(): List<Products> {
        return storeApi.getWomensClothing()

    }


}