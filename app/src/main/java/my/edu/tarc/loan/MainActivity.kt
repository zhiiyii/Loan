package my.edu.tarc.loan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import my.edu.tarc.loan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonCalculate.setOnClickListener {
            val sellingPrice = binding.editTextNumberDecimalSellingPrice.text.toString().toFloat()
            val downPayment = binding.editTextNumberDecimalDownPayment.text.toString().toFloat()
            val firstTimeBuyer = binding.checkBoxFirstTime.isChecked

            var legalFee: Float = 0.0f
            var stampDuty: Float = 0.0f

            if (firstTimeBuyer) {
                if (downPayment >= sellingPrice * 0.1) {
                    var loan = sellingPrice - downPayment
                    // loan only up to 90%, dp at least 10%
                    if (loan <= 500000) {
                        legalFee = (loan * 0.01).toFloat()
                    }
                    if (loan > 500000 && loan <= 1000000) {
                        legalFee += ((loan - 500000) * 0.008).toFloat()
                    }
                    if (loan > 1000000) {
                        legalFee += ((loan - 1000000) * 0.005).toFloat()
                    }

                    stampDuty = (loan * 0.005).toFloat()
                } else {
                    // display down payment not enough
                }
            } else {
                // display non first time buyer
            }

            val myLoan = Loan(legalFee.toString(), stampDuty.toString())
            binding.myLoan =myLoan
        }

        binding.buttonReset.setOnClickListener {
            val myLoan = Loan("", "")
            binding.myLoan = myLoan
            binding.checkBoxFirstTime.isChecked = false
        }

        binding.imageButtonPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:03456789")
            startActivity(intent)
        }

        binding.imageButtonWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://pbebank.com.my")
            startActivity(intent)
        }
    }

}