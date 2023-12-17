package com.example.curcumo.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.curcumo.R
import com.example.curcumo.model.Product

class ProductADP(
    private var productList: List<Product>,
    private val onDeleteClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductADP.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product, position: Int, onDeleteClick: (Product) -> Unit) {
            nameTextView.text = "${position + 1}. ${product.name}"
            deleteButton.setOnClickListener { onDeleteClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], position, onDeleteClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Product>) {
        val diffCallback = ProductDiffCallback(productList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        productList = newList
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}
