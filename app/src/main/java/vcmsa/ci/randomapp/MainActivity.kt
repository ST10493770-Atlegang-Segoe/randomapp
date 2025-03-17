package vcmsa.ci.randomapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var randomNumber : Int = 0
    private var guessNumber : EditText? = null
    private var outputTxt : TextView? = null
    private var backPressed = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        generateRandomNumber()

        guessNumber = findViewById(R.id.guessNumber)
        outputTxt = findViewById(R.id.outputTxt)

        val guessBtn = findViewById<Button>(R.id.guessBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)
        val exitBtn = findViewById<Button>(R.id.exitBtn)

        guessBtn.setOnClickListener {
            compare()
        }

        clearBtn.setOnClickListener {
            guessNumber?.text?.clear()
            generateRandomNumber()
            outputTxt?.text = ""
        }

        exitBtn.setOnClickListener {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }

    }


    private fun generateRandomNumber(){
        val randomNumber = Random.nextInt(1, 100)
    }

    private fun isNotEmpty() : Boolean {

        var b = true
        if(guessNumber?.text.toString().trim().isEmpty()){
            guessNumber?.error = "Required!"
            b = false
        }
        return  b
    }

    private fun compare() {

        if (isNotEmpty()) {

            val guessNumber = guessNumber?.text.toString().trim().toInt()

            if (guessNumber == randomNumber) {

                outputTxt?.text = "Great! $guessNumber is the correct number."
            } else
                if (guessNumber > randomNumber) {
                    outputTxt?.text = "Sorry, $guessNumber is too high."
                } else
                {
                    outputTxt?.text = "Sorry, $guessNumber is too low."
                }

        }
    }

    override fun onBackPressed(){
        if (backPressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
        }
        else{
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_LONG).show()
        }
        backPressed = System.currentTimeMillis()
    }


}


