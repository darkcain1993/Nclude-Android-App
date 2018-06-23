//© D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import android.widget.Toast

import com.example.dnarialpressley.nclude2.GetToken
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stripe.android.model.Token
import com.stripe.android.view.CardInputWidget



class PaymentActivity : AppCompatActivity() {
    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val stripe = Stripe(this, "pk_test_IXywX6vVqlj5XlAQ3m8YoXtH");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val chargeAmount = intent.getStringExtra("ChargeAmount")
        val chargeText = findViewById<TextView>(R.id.chargeAmountText)
        chargeText.text = chargeAmount

        val payButton = findViewById<Button>(R.id.paymentButtom)
        payButton.setOnClickListener {
            val mCardInputWidget = findViewById<CardInputWidget>(R.id.card_input_widget)
            //val mShippingInfo = findViewById<ShippingInfoWidget>(R.id.shipping_info_widget)

            val cardToSave = mCardInputWidget.card
            if (cardToSave == null) {
                println("Invalid Card Data")
            }

            cardToSave?.name = "Brian Baker"

            fun getAndSaveToken(card: Card) {
                stripe.createToken(
                        card,
                        object : TokenCallback {
                            override fun onSuccess(token: Token) {
                                // Send token to your own web service
                                mDatabase.child("StripeToken").setValue(token)
                                println("You have saved your card token to the database :)")
                            }

                            override fun onError(error: Exception) {
                                Toast.makeText(this@PaymentActivity,
                                        error.localizedMessage,
                                        Toast.LENGTH_LONG).show()
                                println("You Failed.")
                            }
                        }
                )
            }


            getAndSaveToken(cardToSave as Card)
            GetToken2().execute("https://nclude-e-commerce-app.firebaseio.com/StripeToken/id.json")

        }

    }
}

