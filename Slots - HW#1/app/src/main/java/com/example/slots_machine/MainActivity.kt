package com.example.slots_machine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun resetButton(view:View){
//        There must be a reset button. Clicking this button should start the game over again.
//        (slots are set to their dault, the balance and bet amounts are set to default, any previous feedback is removed).
        findViewById<TextView>(R.id.FirstSlot).text = "1"
        findViewById<TextView>(R.id.SecondSlot).text = "2"
        findViewById<TextView>(R.id.ThirdSlot).text = "3"
        findViewById<TextView>(R.id.Balance).text = "$10"
        findViewById<TextView>(R.id.BetAmount).text = "$1"
        findViewById<TextView>(R.id.FeedbackMessage).text = ""

        findViewById<Button>(R.id.playButton).isEnabled = true
        findViewById<Button>(R.id.Increment).isEnabled = true
        findViewById<Button>(R.id.Decrement).isEnabled = true

    }


    fun updateBetAmount(view:View){
//        The user should be able to decide what the bet amount will be before clicking the play button.
//        Towards that, there should be 2 buttons to increment, and decrement bet amount.
//        Clicking the button with the (-) sign should decrement by 1 and (+) increment by would. Default amount should be $1.
        val betAmountTextView = findViewById<TextView>(R.id.BetAmount)
        val balance = findViewById<TextView>(R.id.Balance).text.toString().drop(1).toInt()
        // Extract the numeric part from the TextView, removing the dollar sign
        var betAmount: Int = betAmountTextView.text.toString().substring(1).toInt()


        when (view.id) {
            R.id.Increment -> if (betAmount < balance)betAmount++
            R.id.Decrement -> if (betAmount > 1) betAmount--
        }

        // Update the TextView to show the new bet amount with a dollar sign
        betAmountTextView.text = "$$betAmount"
    }

    fun playButton(view: View) {
//        When a user clicks the "play" button a randomly generated digit between 1 and 9 (incluseive) should appear in each of the 3 slots.
        val betAmountTextView = findViewById<TextView>(R.id.BetAmount)
        val betAmount = betAmountTextView.text.toString().drop(1).toInt()
        val balanceTextView = findViewById<TextView>(R.id.Balance)
        var balance = balanceTextView.text.toString().drop(1).toInt()

        if (betAmount > balance) {
            // Display an error message and exit the function without playing the game
            val feedbackTextView = findViewById<TextView>(R.id.FeedbackMessage) // Ensure this ID matches your layout
            feedbackTextView.text = "Insufficient funds for \$$betAmount bet"
            feedbackTextView.setTextColor(Color.parseColor("#FF0000")) // Set text color to red
            return // Exit the function
        }

        val firstSlot: TextView = findViewById(R.id.FirstSlot)
        val secondSlot: TextView = findViewById(R.id.SecondSlot)
        val thirdSlot: TextView = findViewById(R.id.ThirdSlot)

        firstSlot.text = (1..9).random().toString()
        secondSlot.text = (1..9).random().toString()
        thirdSlot.text = (1..9).random().toString()
        val feedbackTextView = findViewById<TextView>(R.id.FeedbackMessage)

        if (firstSlot.text == secondSlot.text && secondSlot.text == thirdSlot.text) {
            val winnings = 5 * betAmount
            balance += winnings
            feedbackTextView.setTextColor(Color.parseColor("#00FF00")) // Setting text color to Green

            feedbackTextView.text = "Hooray! You've Won!"
        } else {
            balance -= betAmount
            feedbackTextView.setTextColor(Color.parseColor("#FF0000")) // Setting text color to red

            feedbackTextView.text = "You lost! Try again!"
        }

        balanceTextView.text = "$$balance"
        gameOver();
    }
    private fun gameOver(){
//        If the user has no money left, the following text "You lost, game is over!" in red should be shown.
//        Additionally, if the balance is 0, the play and +/- buttons should be disabled.
        val balanceTextView = findViewById<TextView>(R.id.Balance)
        val balance = balanceTextView.text.toString().drop(1).toIntOrNull() ?: 0 // Extracting balance and converting to Int

        // Check if the balance is 0
        if (balance == 0) {
            val feedbackTextView = findViewById<TextView>(R.id.FeedbackMessage)
            feedbackTextView.text = "You lost, game is over!"
            feedbackTextView.setTextColor(Color.parseColor("#FF0000")) // Setting text color to red

            findViewById<Button>(R.id.playButton).isEnabled = false
            findViewById<Button>(R.id.Increment).isEnabled = false
            findViewById<Button>(R.id.Decrement).isEnabled = false
        }
    }
}