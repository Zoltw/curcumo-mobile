package com.example.curcumo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.curcumo.MainActivity
import com.example.curcumo.MainViewModel
import com.example.curcumo.R
import com.example.curcumo.model.Product
import com.example.curcumo.databinding.ProductAdditionFragmentBinding

class AddProductFragment : Fragment(R.layout.product_addition_fragment) {

    private lateinit var binding: ProductAdditionFragmentBinding
    private val mainViewModel: MainViewModel by lazy {
        (requireActivity() as MainActivity).obtainViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ProductAdditionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddButtonListener()
    }

    @SuppressLint("StringFormatInvalid")
    private fun setupAddButtonListener() {
        binding.buttonAdd.setOnClickListener {
            val productName = binding.editTextProductName.text.toString()
            if (productName.isNotBlank()) {
                addProductAndClearInput(productName)
                hideKeyboard()
                showToast(getString(R.string.product_added, productName))
            }
        }
    }

    private fun addProductAndClearInput(productName: String) {
        val product = Product(productName)
        mainViewModel.addProduct(product)
        mainViewModel.saveProductList(requireContext())
        binding.editTextProductName.text.clear()
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
