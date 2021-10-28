package com.example.shoppingapp.di

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.data.local.StoreRoomDatabase
import com.example.shoppingapp.data.remote.StoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //Create Retrofit Object
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StoreApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //Create an instance of the API
    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): StoreApi =
        retrofit.create(StoreApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: StoreRoomDatabase.Callback) =
        Room
            .databaseBuilder(application, StoreRoomDatabase::class.java, "cart_database")
            .addCallback(callback)
            .build()

    @Provides
    fun provideMyDao(db: StoreRoomDatabase) = db.storeDao()


}