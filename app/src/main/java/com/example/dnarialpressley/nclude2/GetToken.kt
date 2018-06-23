//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude2


import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import com.example.dnarialpressley.nclude_a.CartItem
import com.example.dnarialpressley.nclude_a.PaymentActivity
import com.google.gson.Gson
import com.stripe.Stripe
import com.stripe.model.Charge
import java.io.*

import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.util.EntityUtils
import io.realm.Realm



/**
 * Created by dnarial.pressley on 11/28/2017.
 */

 class GetToken: AsyncTask<String, String, String>() {


    override fun doInBackground(vararg uri: String): String? {




        // get a realm instance and obtain the results added to the database after the buy button is used
        val realm = Realm.getDefaultInstance()
        val query = realm.where(CartItem::class.java)
        val results = query.findAll()

        // this will take the results and sort out the price of the items so it may be stored in the textview
        var priceD = 0.00
        for (cost in results){
            priceD = priceD + cost.toString().replace("$","").replaceBefore("price","").replaceAfter("}", "").replace("price:","").replace("}","").toDouble()
        }




        val responseString: String? = null
        val httpclient = HttpClientBuilder.create().build();
        val httpget = HttpGet("https://nclude-e-commerce-app.firebaseio.com/StripeToken/id.json")

        val response = httpclient.execute(httpget)

        if (response.statusLine.statusCode == 200) {
            Log.i("Server response", "You obtained the token and it is being returned.")
        } else {
            Log.i("Server response", "Failed to get server response")
        }

        val server_response = EntityUtils.toString(response.entity)
        // Token is created using Checkout or Elements!
        // Get the payment token ID submitted by the form:
        val token = Gson().fromJson<String>(server_response,String::class.java)
        ChargeCard(token, priceD)
        return token
    }

 }

fun ChargeCard(token: String, chargeAmount: Double) {

    com.stripe.Stripe.apiKey = "sk_test_g6yGEQf4El90Cki6JI24jkBm"
    // Charge the user's card:
    val params = HashMap<String, Any>()
    params.put("amount", chargeAmount)
    params.put("currency", "usd")
    params.put("description", "Example charge")
    params.put("source", token)

    val charge = Charge.create(params)
}

