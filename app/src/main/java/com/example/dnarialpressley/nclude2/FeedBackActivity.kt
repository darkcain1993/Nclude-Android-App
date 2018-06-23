//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_feed_back.*

class FeedBackActivity : AppCompatActivity() {
    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        // adds a custom toolbar to the activity
        val myToolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(myToolbar)


        // this will find the navigation view and handle the onclick events(move to new activity) of the items inside it
        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pantItem -> {
                    val intent = Intent(this@FeedBackActivity, PantsProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.techItem -> {
                    val intent = Intent(this@FeedBackActivity, MainProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.shoeItem -> {
                    val intent = Intent(this@FeedBackActivity, ShoeProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.clothItem -> {
                    val intent = Intent(this@FeedBackActivity, ClothingProductActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false

            }
        })



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



        val contact = findViewById<Button>(R.id.feedbackButton )

        contact.setOnClickListener {

            var customerInquiry = Feedback()
            customerInquiry.customerID = nameEditText.text.toString()
            customerInquiry.customerEmail = emailEditText.text.toString()
            customerInquiry.feedbackInformation = feedbackEditText.text.toString()

            println(customerInquiry.customerID)
            println(customerInquiry.customerEmail)
            println(customerInquiry.feedbackInformation)

            // this will add the items to the Firebase database
            mDatabase.child("Customer Inquiry/" + customerInquiry.customerID).setValue(customerInquiry)
        }
    }
}
