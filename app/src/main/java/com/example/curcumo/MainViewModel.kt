package com.example.curcumo

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.curcumo.model.Product
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class MainViewModel : ViewModel() {
    private var productList = mutableListOf<Product>()
    private val gson = Gson()

    fun addProduct(product: Product) {
        productList.add(product)
    }

    fun getProductList(): List<Product> = productList

    fun removeProduct(product: Product) {
        productList.remove(product)
    }

    fun isProductListEmpty(): Boolean = productList.isEmpty()

    fun saveProductList(context: Context) {
        val jsonList = gson.toJson(productList)
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(PRODUCT_LIST_KEY, jsonList)
            apply()
        }
    }

    fun loadProductList(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val jsonList = sharedPreferences.getString(PRODUCT_LIST_KEY, null)

        jsonList?.let {
            try {
                val type = object : TypeToken<List<Product>>() {}.type
                val loadedList: List<Product> = gson.fromJson(it, type)
                productList.clear()
                productList.addAll(loadedList)
            } catch (e: JsonSyntaxException) {
                throw JsonSyntaxException("Json syntax exception $e")
            }
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "ProductList"
        private const val PRODUCT_LIST_KEY = "products"
    }
}
