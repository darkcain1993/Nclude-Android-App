//© 2018 2018 D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.realm.Realm



class ProductAdapter_A(private val productItem: List<ProductInformation>) : RecyclerView.Adapter<ProductAdapter_A.ViewHolder>() {

    // initiate the views that will be used inside each recycle view
    inner class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView){
        val itemPriceTextView: TextView
        val itemNameTextView: TextView
        val itemImageView: ImageView
        val buyButton : Button

        init {
            itemPriceTextView = cardView.findViewById<TextView>(R.id.itemPriceTextView)
            itemNameTextView = cardView.findViewById<TextView>(R.id.itemNameTextView)
            itemImageView = cardView.findViewById<ImageView>(R.id.itemImageView)
            buyButton = cardView.findViewById<Button>(R.id.removeButton)
        }
    }

    // use this function to bind the views of the items held in the database
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productItem[position]
        holder.itemPriceTextView.setText(item.productPrice)
        holder.itemNameTextView.setText(item.productName)
        holder.itemImageView.setImageResource(item.productImage)

        // use this to set the click listener for the buttons inside the recycleview
        holder.buyButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

                val realm = Realm.getDefaultInstance()

                realm.beginTransaction()
                val cartItem = realm.createObject(CartItem::class.java)
                cartItem.name = item.productName
                cartItem.price = item.productPrice
                cartItem.image = item.productImage
                realm.commitTransaction()

            }
        })
    }

    // use this function so the adapter knows how many views to create
    override fun getItemCount(): Int {
        return productItem.size
    }

    // use this function to inflate the views as placed in the pre-created layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }
}
