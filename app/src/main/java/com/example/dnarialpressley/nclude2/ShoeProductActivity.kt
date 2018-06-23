//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.*
import io.realm.Realm

class ShoeProductActivity : AppCompatActivity() {

    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_product)

        // adds a custom toolbar to the activity
        val myToolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(myToolbar)


        // this will find the navigation view and handle the onclick events(move to new activity) of the items inside it
        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pantItem -> {
                    //val intent = Intent(this@PantsProductActivity, PantsProductActivity::class.java)
                    //startActivity(intent)
                    true
                }
                R.id.techItem -> {
                    val intent = Intent(this@ShoeProductActivity, MainProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.shoeItem -> {
                    val intent = Intent(this@ShoeProductActivity, ShoeProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.clothItem -> {
                    val intent = Intent(this@ShoeProductActivity, ClothingProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.contactUs -> {
                    val intent = Intent(this@ShoeProductActivity, FeedBackActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false

            }
        })

        //this adds a button to open the navigation drawer to the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

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


        // this is the function that if called will add items to the Firebase database (items already in, not called)
        fun addToDatabase() {
            val item1 = ProductInformation()
            val item2 = ProductInformation()
            val item3 = ProductInformation()

            item1.productName = "Candy"
            item1.productPrice = "$5.64"
            item1.productImage = R.drawable.d1
            item2.productName = "Robot"
            item2.productPrice = "$70.56"
            item2.productImage = R.drawable.d2
            item3.productName = "Car"
            item3.productPrice = "$70500.56"
            item3.productImage = R.drawable.d3

            // This creates a list of products we currently have to offer
            var productListIn = listOf<ProductInformation>(item1, item2, item3)

            // this will add the items to the Firebase database
            mDatabase.child("Technology Products").setValue(productListIn)
        }
        //addToDatabase()


        // this creates an empty product list
        var productListOut = mutableListOf<ProductInformation>()

        // this will query the database and add the items to the empty product list (Still need to revisit to look at the picture storage. Must store actual images on the database)
        val itemQuery: Query = mDatabase.child("Technology Products")
        itemQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Goes through each item in this part of the database and add it to the empty list
                for (singleSnapshot in dataSnapshot.children) {
                    val item = ProductInformation()
                    item.productName = singleSnapshot.child("productName").value.toString()
                    item.productPrice = singleSnapshot.child("productPrice").value.toString()
                    item.productImage = singleSnapshot.child("productImage").value.toString().toInt()
                    productListOut.add(item)

                }

                // this initiates a Realm database instance (phone-side database) (used inside the custom adapter)
                Realm.init(this@ShoeProductActivity)

                // This takes the listview and draws it into a variable by its id
                val recycleView = findViewById<RecyclerView>(R.id.productRecycleView)

                val layoutManager = LinearLayoutManager(this@ShoeProductActivity)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                recycleView.layoutManager = layoutManager

                // This takes our list and adapts it to the layout we prefer set up in our custom adapter
                val adapter = ProductAdapter_A(productListOut)
                // This implements the recycleview shown in the UI as its setup in the adapter
                recycleView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Server Response", "onCancelled", databaseError.toException())
            }
        })

    }

    // add the actionbar menu to the action bar (this contains the cart button)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    // find the item ids inside the menu and handle their respective click actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the buttons, in this case we handle the movement to the cart activity
        val id = item.itemId
        return if (id == R.id.cartButton) {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            true
        } else super.onOptionsItemSelected(item)
    }
}
