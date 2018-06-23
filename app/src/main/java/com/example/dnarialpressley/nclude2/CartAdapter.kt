//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a


import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import io.realm.RealmResults
import io.realm.Realm
import android.app.Activity
import android.content.Intent



/**
 * Created by dnarial.pressley on 11/8/2017.
 */
//create a custom recycleview adapter that takes in the database results
class CartAdapter(private val productItem: RealmResults<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    // initiate the views that will be used inside each recycle view
    inner class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView){
        val priceTextView: TextView
        val nameTextView: TextView
        val pictureImageView: ImageView
        val removeButton : Button

        init {
            priceTextView = cardView.findViewById<TextView>(R.id.itemPriceTextView)
            nameTextView = cardView.findViewById<TextView>(R.id.itemNameTextView)
            pictureImageView = cardView.findViewById<ImageView>(R.id.itemImageView)
            removeButton = cardView.findViewById<Button>(R.id.removeButton)
        }
    }


    // use this function to bind the views of the items held in the database
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productItem[position]
        holder.priceTextView.setText(item?.price)
        holder.nameTextView.setText(item?.name)
        holder.pictureImageView.setImageResource(item!!.image)


        // use this to set the click listener for the buttons inside the recycleview
        holder.removeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                // open up a database instance and start a transaction
                val realm = Realm.getDefaultInstance()
                realm.executeTransaction(Realm.Transaction {
                    // remove a single object
                    val itemDelete = productItem[position]
                    itemDelete!!.deleteFromRealm()
                })

                //finish the activity and restart it to "refresh" the page
                (v.context as Activity).finish()
                v.context.startActivity(Intent(v.context, CartActivity::class.java).addFlags((Intent.FLAG_ACTIVITY_NO_ANIMATION)))
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
                .inflate(R.layout.cart_card_layout, parent, false)
        //val p = LayoutInflater.from(parent.context)
         //       .inflate(R.layo, parent, false)
        return ViewHolder(v)
    }
}


