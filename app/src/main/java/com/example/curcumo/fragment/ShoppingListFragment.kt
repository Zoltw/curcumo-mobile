package com.example.curcumo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curcumo.MainActivity
import com.example.curcumo.MainViewModel
import com.example.curcumo.R
import com.example.curcumo.model.Product
import com.example.curcumo.utils.ProductADP
import com.example.curcumo.databinding.ShoppingListFragmentBinding

class ShoppingListFragment : Fragment(R.layout.shopping_list_fragment) {

    private lateinit var binding: ShoppingListFragmentBinding
    private lateinit var productAdapter: ProductADP
    private val mainViewModel: MainViewModel by lazy {
        (requireActivity() as MainActivity).obtainViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ShoppingListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        updateProductList()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductADP(mainViewModel.getProductList(), onDeleteClickListener)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun updateProductList() {
        productAdapter.updateList(mainViewModel.getProductList())
    }

    @SuppressLint("StringFormatInvalid")
    private val onDeleteClickListener: (Product) -> Unit = { deletedProduct ->
        mainViewModel.removeProduct(deletedProduct)
        mainViewModel.saveProductList(requireContext())
        showToast(getString(R.string.product_deleted, deletedProduct.name))

        updateProductList()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
