//© 2018 D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import io.realm.Realm
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.R.attr.key
import android.widget.TextView


class CartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // adds a custom toolbar to the activity
        val myToolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(myToolbar)



        // this will find the navigation view and handle the onclick events(move to new activity) of the items inside it
        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pantItem -> {
                    val intent = Intent(this@CartActivity, PantsProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.techItem -> {
                    val intent = Intent(this@CartActivity, MainProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.shoeItem -> {
                    val intent = Intent(this@CartActivity, ShoeProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.clothItem -> {
                    val intent = Intent(this@CartActivity, ClothingProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false

            }
        })

        //this adds a button to open the navigation drawer to the action bar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeButtonEnabled(true)

        val drawerLayout = findViewById<DrawerLayout>(R.id.firstLayout)
        val mDrawerToggle = object : ActionBarDrawerToggle(
                this, /* host Activity */
                drawerLayout, /* DrawerLayout object */
                myToolbar, /* the toolbar*/
                R.string.Open, /* "open drawer" description */
                R.string.Close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state.  */
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
            }

            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(view: View) {
                super.onDrawerOpened(view);
            }
        }

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()


        // initiate the phone side database used to store cart items
        Realm.init(this)

        // get a realm instance and obtain the results added to the database after the buy button is used
        val realm = Realm.getDefaultInstance()
        val query = realm.where(CartItem::class.java)
        val results = query.findAll()

        // this will take the results and sort out the price of the items so it may be stored in the textview
        var priceD = 0.00
        for (cost in results){
            priceD = priceD + cost.toString().replace("$","").replaceBefore("price","").replaceAfter("}", "").replace("price:","").replace("}","").toDouble()
        }

        val priceView = findViewById<TextView>(R.id.priceText)
        priceView.text = priceD.toString()


        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)

        //create a layoutmanager and use it to create a horizontal recycle view
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager

        // pass the database results to the adapter
        val adapter = CartAdapter(results)
        // apply the adapter to the recycleview for use
        recyclerView.adapter = adapter



        val orderButton = findViewById<Button>(R.id.orderButton)

        orderButton.setOnClickListener {
            val intent = Intent(this,PaymentActivity::class.java)
            intent.putExtra("ChargeAmount", priceD.toString())
            startActivity(intent)
        }
    }


}




