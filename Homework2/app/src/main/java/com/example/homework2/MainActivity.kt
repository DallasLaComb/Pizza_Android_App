package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
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

        val seekBarSpicyLevelLabel = findViewById<TextView>(R.id.tv_spiciness_level)
        findViewById<SeekBar>(R.id.sb_spiciness_level).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                seekBarSpicyLevelLabel.text = "Spiciness Level: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//              Don't need this function
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//              Don't need this function

            }
        })

        updateQuantity()

        }

    private fun updateQuantity() {
        val tvQuantity = findViewById<TextView>(R.id.tv_quantity_number)
        val buIncrementButton = findViewById<Button>(R.id.bu_increment_quantity)
        val buDecrementButton = findViewById<Button>(R.id.bu_decrement_quantity)

        // Set an OnClickListener for the Increment button
        buIncrementButton.setOnClickListener {
            // Parse the current quantity to an integer, increment it, and set back
            var quantity = tvQuantity.text.toString().toInt()
            quantity++
            tvQuantity.text = quantity.toString()
        }

        // Set an OnClickListener for the Decrement button
        buDecrementButton.setOnClickListener {
            // Parse the current quantity to an integer, decrement it, and ensure it does not go below a certain value (e.g., 0)
            var quantity = tvQuantity.text.toString().toInt()
            if (quantity > 0) { // Prevents quantity from going below 0
                quantity--
                tvQuantity.text = quantity.toString()
            }
        }
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
    fun spicySwitchClick(view:View){
        val switchSpicy = findViewById<Switch>(R.id.sw_extra_spicy)
        val switchText: String
        if (switchSpicy.isChecked){
            switchText = "Yes, $1.00"
            findViewById<SeekBar>(R.id.sb_spiciness_level).visibility = View.VISIBLE;
            findViewById<TextView>(R.id.tv_spiciness_level).visibility = View.VISIBLE;
        } else{
            switchText = "No, $0.00"
            findViewById<SeekBar>(R.id.sb_spiciness_level).visibility = View.INVISIBLE;
            findViewById<TextView>(R.id.tv_spiciness_level).visibility = View.INVISIBLE;
        }
        switchSpicy.text=switchText
    }

}