package com.devtk.bmicalculator

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.text.Html
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToLong

class bmiActivity : AppCompatActivity() {
    var heightInt = 0
    var weightInt = 0
    var heightVal = 0.0
    var weightVal = 0.0
    var bmi = 0.0
    var category = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val constlayout = findViewById<ConstraintLayout>(R.id.activity_bmi)
        val animationDrawable = constlayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()

        val heightGroup = findViewById<RadioGroup>(R.id.height_grp)
        val weightGroup = findViewById<RadioGroup>(R.id.weight_grp)
        val calc_btn = findViewById<Button>(R.id.calc_btn)
        val cm_et = findViewById<EditText>(R.id.cm_et)
        val weight_et = findViewById<EditText>(R.id.weight_et)
        val inch_et = findViewById<EditText>(R.id.inch_et)
        val ft_et = findViewById<EditText>(R.id.ft_et)

        heightGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            if (radioButton.id == R.id.radio_cm){
                heightInt = 0
                inch_et.visibility = View.GONE
                ft_et.visibility = View.GONE
                cm_et.visibility = View.VISIBLE
            }else{
                heightInt = 1
                inch_et.visibility = View.VISIBLE
                ft_et.visibility = View.VISIBLE
                cm_et.visibility = View.GONE
            }
        }
        val alertDialog = AlertDialog.Builder(this,R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        alertDialog.setTitle("BMI")
        alertDialog.setNeutralButton("OK") { dialog, which ->
        }
        weightGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            weightInt = if (radioButton.id == R.id.radio_kg){
                0
            }else{
                1
            }
        }
        calc_btn.setOnClickListener {
            if (heightInt == 0){
                if (cm_et.text.isBlank() or weight_et.text.isBlank()){
                    Toast.makeText(this,"Please Enter A Value",Toast.LENGTH_SHORT).show()
                } else{
                    heightVal = cm_et.text.toString().toDouble()
                    weightVal = weight_et.text.toString().toDouble()
                    if (weightInt == 1){
                        weightVal /=  2.205
                    }
                    heightVal /= 100
                    Log.d("Vals","weight:${weightVal} heigh:${heightVal} ${weightInt}")
                    bmi = weightVal/(heightVal.pow(2))
                    bmi = bmi.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()
                    if (bmi<=17){
                        category = "Underweight"
                    } else if(16<=bmi && bmi<18.5){
                        category = "Slightly Underweight"
                    } else if(bmi>=18.5 && bmi<25){
                        category = "Normal"
                    } else if(bmi>=25 && bmi<30){
                        category = "Slightly Overweight"
                    } else if(bmi>=30){
                        category = "Overweight"
                    }
                    alertDialog.setMessage("Your BMI is: $bmi kg/m${Html.fromHtml("<sup><small>2</small></sup>")} \nWhich is $category")
                    alertDialog.show()
                }
            } else if (heightInt == 1){
                if (ft_et.text.isBlank() or weight_et.text.isBlank()){
                    Toast.makeText(this,"Please Enter A Value",Toast.LENGTH_SHORT).show()
                } else{
                    var inchVal = 0.0
                    if (inch_et.text.isBlank()){
                        inchVal = 0.0
                    } else inch_et.text.toString().toDouble()
                    heightVal = ft_et.text.toString().toDouble()
                    weightVal = weight_et.text.toString().toDouble()
                    if (weightInt == 1){
                        weightVal /=  2.205
                    }
                    heightVal = ((heightVal*12) + inchVal)/39.37
                    Log.d("Vals","weight:${weightVal} heigh:${heightVal} ${weightInt}")
                    bmi = weightVal/(heightVal.pow(2))
                    bmi = bmi.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()

                    if (bmi<=17){
                            category = "Underweight"
                    } else if(16<=bmi && bmi<18.5){
                        category = "Slightly Underweight"
                    } else if(bmi>=18.5 && bmi<25){
                        category = "Normal"
                    } else if(bmi>=25 && bmi<30){
                        category = "Slightly Overweight"
                    } else if(bmi>=30){
                        category = "Overweight"
                    }
                    alertDialog.setMessage("Your BMI is: $bmi kg/m${Html.fromHtml("<sup>2</sup>")} \nWhich is $category")
                    alertDialog.show()
                    }


                }
            }
        }


    }
