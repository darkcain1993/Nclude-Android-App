//© 2018 D'Narial Brown-Pressley All Rights Reserved.
package com.example.dnarialpressley.nclude_a

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView



class IntroPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_page)

        // obtain the text and animate it into view
        val solganTextView = findViewById<TextView>(R.id.companySolganTextView)
        solganTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_delay1))

        // obtain the button and animate it into view
        val startButton = findViewById<Button>(R.id.getStartedButton)
        startButton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in_delay2))

        // set the button to go to the next activity
        startButton.setOnClickListener {
            val intent = Intent(this,MainProductActivity::class.java)
            this.finish()
            startActivity(intent)
        }

    }
}
