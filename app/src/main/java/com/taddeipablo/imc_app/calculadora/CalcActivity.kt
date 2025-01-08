package com.taddeipablo.imc_app.calculadora

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.RangeSlider
import com.taddeipablo.imc_app.R

class CalcActivity : AppCompatActivity() {

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

    private var isSelectedOption: Boolean = true

    private val male: Int = 1
    private val female: Int = 2

    private var lastSSelected: Int = 1

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
            val result = df.format(value)
            tvHeight.text = "$result CM"
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

}