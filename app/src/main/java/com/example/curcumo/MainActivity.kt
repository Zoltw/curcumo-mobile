package com.example.curcumo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.curcumo.fragment.AddProductFragment
import com.example.curcumo.fragment.ListShopFragment
import com.example.curcumo.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var activityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = MainActivityBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if (viewModel.isProductListEmpty()) {
            viewModel.loadProductList(this)
        }

        setupInitialFragment()
        setupButtonListeners()
    }

    fun obtainViewModel(): MainViewModel {
        return viewModel
    }

    private fun setupInitialFragment() {
        replaceFragment(AddProductFragment())
    }

    private fun setupButtonListeners() {
        activityBinding.apply {
            productAdditionFragmentButton.setOnClickListener {
                replaceFragment(AddProductFragment())
            }
            shoppingListFragmentButton.setOnClickListener {
                replaceFragment(ListShopFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}