package com.example.test1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.productName)
        val category = view.findViewById<TextView>(R.id.productCategory)
        val price = view.findViewById<TextView>(R.id.productPrice)
        val quantity = view.findViewById<TextView>(R.id.quantity)
        val plus = view.findViewById<ImageButton>(R.id.btnPlus)
        val minus = view.findViewById<ImageButton>(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = "$${product.price}"
        holder.quantity.text = product.quantity.toString()

        holder.plus.setOnClickListener {
            product.quantity++
            holder.quantity.text = product.quantity.toString()
        }

        holder.minus.setOnClickListener {
            if (product.quantity > 0) {
                product.quantity--
                holder.quantity.text = product.quantity.toString()
            }
        }
    }
}