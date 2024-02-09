package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pizzaSizeList = listOf("Medium ($9.99)", "Large($13.99)", "Extra Large ($15.99)", "Party Size ($25.99)")
        val pizzaSizeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pizzaSizeList)
        val pizzaSizeSpinner = findViewById<Spinner>(R.id.sp_choose_pizza_size)
        pizzaSizeSpinner.adapter = pizzaSizeAdapter
        pizzaSizeSpinner.onItemSelectedListener = this

        }
    override fun onNothingSelected(parent: AdapterView<*>?) {
//        Toast.makeText(this, "Nothing is selected!", Toast.LENGTH_SHORT).show()
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
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