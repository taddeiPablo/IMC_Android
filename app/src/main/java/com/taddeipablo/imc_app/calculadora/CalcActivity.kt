package com.taddeipablo.imc_app.calculadora

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.taddeipablo.imc_app.R
import kotlin.math.pow
import kotlin.time.toDuration

class CalcActivity : AppCompatActivity() {

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnLess_weight: FloatingActionButton
    private lateinit var btnPlus_weight: FloatingActionButton
    private lateinit var btnLess_age: FloatingActionButton
    private lateinit var btnPlus_age: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView
    private lateinit var btnCalcular: Button

    private var isSelectedOption: Boolean = true
    private val male: Int = 1
    private val female: Int = 2
    private var lastSSelected: Int = 1
    private var currentWeight: Int = 60
    private var currentAge: Int = 30
    private var currentHeight: Int = 0

    companion object{
        const val IMC_KEY = "imcKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calc)

        initComponent() //aqui inicializo los controles
        initListeners() //aqui inicializo los eventos

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initComponent(){
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        rsHeight = findViewById(R.id.rsAltura)
        tvHeight = findViewById(R.id.tvAltura)
        viewMale.setCardBackgroundColor(setGenderColor(isSelectedOption)) //TRUE (ESTA SELECCIONADO)
        viewFemale.setCardBackgroundColor(setGenderColor(!isSelectedOption)) //FALSE (NO ESTA SELECCIONADO)
        btnLess_weight = findViewById(R.id.restarpeso)
        btnPlus_weight = findViewById(R.id.sumapeso)
        btnLess_age = findViewById(R.id.restarEdad)
        btnPlus_age = findViewById(R.id.sumaEdad)
        tvWeight = findViewById(R.id.tvpeso)
        tvWeight.text = "$currentWeight Kg"
        tvAge = findViewById(R.id.tvedad)
        tvAge.text = "$currentAge"
        btnCalcular = findViewById(R.id.calcular)
    }

    private fun initListeners(){
        viewMale.setOnClickListener{
            changeOptionColor(male)
        }
        viewFemale.setOnClickListener{
            changeOptionColor(female)
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            //val result = df.format(value)
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight CM"
        }
        btnLess_weight.setOnClickListener{
            changeWeight(false)
        }
        btnPlus_weight.setOnClickListener{
            changeWeight(true)
        }
        btnLess_age.setOnClickListener{
            changeAge(false)
        }
        btnPlus_age.setOnClickListener{
            changeAge(true)
        }
        btnCalcular.setOnClickListener{
            calculateIMC()
        }
    }

    private fun changeOptionColor(gender: Int){
        if(lastSSelected != gender){
            isSelectedOption = !isSelectedOption
            viewMale.setCardBackgroundColor(setGenderColor(isSelectedOption))
            viewFemale.setCardBackgroundColor(setGenderColor(!isSelectedOption))
            lastSSelected = gender
        }
    }
    private fun setGenderColor(isSelected: Boolean): Int{
        var colorSelected = if(isSelected){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }
        return ContextCompat.getColor(this, colorSelected)
    }
    private fun changeWeight(operation: Boolean){
        if(operation){
            currentWeight += 1
        }else{
            currentWeight -= 1
        }
        tvWeight.text = "$currentWeight Kg"
    }
    private fun changeAge(operation: Boolean){
        if(operation){
            currentAge += 1
        }else{
            currentAge -= 1
        }
        tvAge.text = "$currentAge"
    }
    private fun calculateIMC(){
        val df = DecimalFormat("#.##")
        var imc = currentWeight / (currentHeight.toDouble() /100 * currentHeight.toDouble() /100)
        var result = df.format(imc).toDouble()
        navigateToResult(result)
    }
    private fun navigateToResult(imc_result: Double){
        val resultIntent = Intent(this, ResultActivity::class.java)
        resultIntent.putExtra(IMC_KEY, imc_result)
        startActivity(resultIntent)
    }
}