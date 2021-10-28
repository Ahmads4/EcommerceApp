package com.example.shoppingapp

import android.app.Application
import com.example.shoppingapp.data.local.StoreRoomDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class StoreApplication: Application() {
    val database: StoreRoomDatabase by lazy {StoreRoomDatabase.getDatabase(this)}
}