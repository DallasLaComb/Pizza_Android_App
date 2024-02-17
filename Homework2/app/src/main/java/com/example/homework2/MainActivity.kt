package com.example.homework2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var totalPrice: Double = 0.00
    var sizeSelectionPrice: Double = 0.00
    var spicySelection: Double = 0.00
    var toppingsPrice: Double = 0.00
    var deliveryPrice: Double = 0.00
    val taxRate: Double = 1.0635
    var quantitySelection: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzaSizeSpinner: Spinner = findViewById(R.id.sp_choose_pizza_size)
        pizzaSizeSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listOf("Medium ($9.99)", "Large($13.99)", "Extra Large ($15.99)", "Party Size ($25.99)"))
        pizzaSizeSpinner.onItemSelectedListener = this

        findViewById<SeekBar>(R.id.sb_spiciness_level).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                findViewById<TextView>(R.id.tv_spiciness_level).text = "Spiciness Level: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        setupQuantityButtons()
    }

    private fun setupQuantityButtons() {
        val quantityTextView: TextView = findViewById(R.id.tv_quantity_number)
        val incrementButton: Button = findViewById(R.id.bu_increment_quantity)
        val decrementButton: Button = findViewById(R.id.bu_decrement_quantity)

        incrementButton.setOnClickListener {
            quantitySelection++
            updateTotalPrices()
            quantityTextView.text = quantitySelection.toString()
        }

        decrementButton.setOnClickListener {
            if (quantitySelection > 1) {
                quantitySelection--
                updateTotalPrices()
                quantityTextView.text = quantitySelection.toString()
            }
        }
    }



    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//        This function is what is messed up. This just adds on top of the previous totalPrice value.
        val item = parent.getItemAtPosition(position).toString()
        val matchResult = Regex("\\$(\\d+\\.\\d{2})").find(item)
        val priceString = matchResult?.value?.removePrefix("$")
        sizeSelectionPrice = priceString?.toDoubleOrNull() ?: 0.0

//        Need to create an in value for toppings, spicy, and quantity to find the totalPrice...
        updateTotalPrices()
    }

    fun updateTotalPrices() {
        var subTotalPrice = ((quantitySelection)*(sizeSelectionPrice+toppingsPrice))+spicySelection
        findViewById<TextView>(R.id.tv_subtotal_value).text = "$%.2f".format(subTotalPrice)

        var totalPriceCalculation = (((subTotalPrice)*(taxRate))+deliveryPrice)
        findViewById<TextView>(R.id.tv_total_price).text = "$%.2f".format(totalPriceCalculation)

    }
    fun updateToppingsSelected(view: View) {
        toppingsPrice = 0.0

        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_tomatoes).isChecked) 1.0 else 0.0
        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_mushrooms).isChecked) 2.3 else 0.0
        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_olives).isChecked) 1.7 else 0.0
        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_onions).isChecked) 1.25 else 0.0
        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_broccoli).isChecked) 1.8 else 0.0
        toppingsPrice += if (findViewById<CheckBox>(R.id.cb_spinach).isChecked) 2.0 else 0.0

        updateTotalPrices()

    }
    fun resetButton(view:View){
        totalPrice = 0.00
        sizeSelectionPrice = 9.99 //Medium Pizza
        spicySelection= 0.00
        toppingsPrice= 0.00
        deliveryPrice= 0.00
        quantitySelection = 1

        updateTotalPrices()
    }
    fun setPizzaImageView(view: View) {
        val imageIdOfSelectedPizza = when (view.id) {
            R.id.rb_pepperoni -> R.drawable.pepperoni
            R.id.rb_bbq_chicken -> R.drawable.bbq_chicken
            R.id.rb_margherita -> R.drawable.margheritta
            R.id.rb_hawaiian -> R.drawable.hawaiian
            else -> R.drawable.pizza_crust
        }
        findViewById<ImageView>(R.id.iv_default_pizza).setImageResource(imageIdOfSelectedPizza)
    }

    fun deliverySwitchClick(view: View) {
        val switchDelivery: Switch = findViewById(R.id.sw_delivery)

            if (switchDelivery.isChecked){
                deliveryPrice = 2.00
                updateTotalPrices()

                switchDelivery.text = "Yes, $2.00"
            }
            else{
                deliveryPrice = 0.00
                updateTotalPrices()

                switchDelivery.text = "No, $0.00"
            }
    }

    @SuppressLint("SuspiciousIndentation")
    fun spicySwitchClick(view: View) {
        val switchSpicy: Switch = findViewById(R.id.sw_extra_spicy)

            if (switchSpicy.isChecked){
                switchSpicy.text = "Yes, $1.00"
                spicySelection = 1.00
                updateTotalPrices()

            }
            else{
                switchSpicy.text = "No, $0.00"
                spicySelection = 0.00
                updateTotalPrices()

            }

        findViewById<SeekBar>(R.id.sb_spiciness_level).visibility = if (switchSpicy.isChecked) View.VISIBLE else View.INVISIBLE
        findViewById<TextView>(R.id.tv_spiciness_level).visibility = if (switchSpicy.isChecked) View.VISIBLE else View.INVISIBLE
    }
}
