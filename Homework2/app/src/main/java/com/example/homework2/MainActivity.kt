package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun setPizzaImageView(view:View){
//      Changes the image of Pizza based off of radio button selection.
        val imageIdOfSelectedPizza = when (view.id){
            R.id.rb_pepperoni -> R.drawable.pepperoni
            R.id.rb_bbq_chicken -> R.drawable.bbq_chicken
            R.id.rb_margherita -> R.drawable.margheritta
            R.id.rb_hawaiian -> R.drawable.hawaiian
            else -> R.drawable.pizza_crust
        }
        findViewById<ImageView>(R.id.iv_default_pizza).setImageResource(imageIdOfSelectedPizza)
    }

}