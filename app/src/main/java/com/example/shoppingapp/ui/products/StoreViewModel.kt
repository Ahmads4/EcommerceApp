package com.example.shoppingapp.ui

import androidx.lifecycle.*
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class StoreApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class StoreViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {
    private val _jewelryProducts = MutableLiveData<List<Products>>()
    val jewelryProducts: LiveData<List<Products>> = _jewelryProducts
    private val _electronicsProducts = MutableLiveData<List<Products>>()
    val electronicsProducts: LiveData<List<Products>> = _electronicsProducts
    private val _mensClothing = MutableLiveData<List<Products>>()
    val mensClothing: LiveData<List<Products>> = _mensClothing
    private val _womensClothing = MutableLiveData<List<Products>>()
    val womensClothing: LiveData<List<Products>> = _womensClothing
    private val _networkState = MutableLiveData<StoreApiStatus>()
    val networkState: LiveData<StoreApiStatus> = _networkState


    init {
        getProducts()
    }


    private fun getProducts() {
        viewModelScope.launch {
            _networkState.value = StoreApiStatus.LOADING
            try {
                _jewelryProducts.value = repository.getJewelryProducts()
                _electronicsProducts.value = repository.getElectronicsProducts()
                _mensClothing.value = repository.getClothingMen()
                _womensClothing.value = repository.getClothingWomen()
                _networkState.value = StoreApiStatus.DONE
            } catch (e: Exception) {
                _jewelryProducts.value = listOf()
                _electronicsProducts.value = listOf()
                _mensClothing.value = listOf()
                _womensClothing.value = listOf()
                _networkState.value = StoreApiStatus.ERROR
            }
        }


    }

    class StoreViewModelFactory @Inject constructor(private val repository: StoreRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoreViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }


    }


}