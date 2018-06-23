//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.realm.Realm

/**
 * Created by dnarial.pressley on 11/7/2017.
 */
//This is the custom adapter used to fill the list view
class ProductAdapter(context: Context?, resource: Int, objects: List<ProductInformation>) : ArrayAdapter<ProductInformation>(context, resource, objects) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cardView = inflater.inflate(R.layout.card_layout, parent,false)

        // obtain the views that will be used in this adapter
        val imageView = cardView.findViewById<ImageView>(R.id.itemImageView)
        val textViewN = cardView.findViewById<TextView>(R.id.itemNameTextView)
        val textViewP = cardView.findViewById<TextView>(R.id.itemPriceTextView)
        val buyButton = cardView.findViewById<Button>(R.id.removeButton)

        val productItem = getItem(position)

        // set the views to presaved items/text
        textViewN.text = productItem.productName
        textViewP.text = productItem.productPrice
        imageView.setImageResource(productItem.productImage)

        // set the click listener to the button to add items to the database for use in the cart
        buyButton.setOnClickListener {
            Realm.init(context)
            val realm = Realm.getDefaultInstance()

            realm.beginTransaction()
            val cartItem = realm.createObject(CartItem::class.java)
            cartItem.name = productItem.productName
            cartItem.price = productItem.productPrice
            cartItem.image = productItem.productImage
            realm.commitTransaction()
        }

        return cardView
    }
}