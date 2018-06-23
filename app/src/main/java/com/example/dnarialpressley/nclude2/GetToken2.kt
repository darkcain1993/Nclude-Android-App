//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a

import java.net.HttpURLConnection.HTTP_OK
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.stripe.Stripe
import com.stripe.model.Charge
import io.realm.Realm
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader



class GetToken2 : AsyncTask<String, Void, String>() {
    internal var server_response: String? = null


    override fun doInBackground(vararg strings: String): String?  {

        // get a realm instance and obtain the results added to the database after the buy button is used
        val realm = Realm.getDefaultInstance()
        val query = realm.where(CartItem::class.java)
        val results = query.findAll()

        // this will take the results and sort out the price of the items so it may be stored in the textview
        var priceD = 0.00
        for (cost in results){
            priceD = priceD + cost.toString().replace("$","").replaceBefore("price","").replaceAfter("}", "").replace("price:","").replace("}","").toDouble()
        }

        val price = priceD.toString().replace(".","")

        val url: URL

        try {
            url = URL(strings[0])
           val urlConnection = url.openConnection() as HttpURLConnection

            val responseCode = urlConnection.getResponseCode()

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.v("Server Response", "You obtained the token and it is being returned.")
            }

            server_response = readStream(urlConnection.getInputStream())
            val token = Gson().fromJson<String>(server_response,String::class.java)
            ChargeCard(token, price.toInt())



        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}

@Throws(IOException::class)
private fun readStream(`is`: InputStream): String {
    val sb = StringBuilder()
    val r = BufferedReader(InputStreamReader(`is`), 50)
    var line = r.readLine()
    while (line != null) {
        sb.append(line)
        line = r.readLine()
    }
    `is`.close()
    return sb.toString()
}

fun ChargeCard(token: String, chargeAmount: Int) {

    com.stripe.Stripe.apiKey = "enter key here"
    // Charge the user's card:
    val params = HashMap<String, Any>()
    params.put("amount", chargeAmount)
    params.put("currency", "usd")
    params.put("description", "Example charge")
    params.put("source", token)

    val charge = Charge.create(params)
}

