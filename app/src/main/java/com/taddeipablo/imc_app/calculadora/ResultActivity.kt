package com.taddeipablo.imc_app.calculadora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taddeipablo.imc_app.R
import com.taddeipablo.imc_app.calculadora.CalcActivity.Companion.IMC_KEY

class ResultActivity : AppCompatActivity() {
    private lateinit var typeimc: TextView
    private lateinit var IMCresult:TextView
    private lateinit var descriptionIMC: TextView
    private lateinit var recalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        initComponent()
        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initUI(result)
        initListener()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initComponent(){
        typeimc = findViewById(R.id.typeIMC)
        IMCresult = findViewById(R.id.IMCresult)
        descriptionIMC = findViewById(R.id.descriptionIMC)
        recalcular = findViewById(R.id.recalcular)
    }
    private fun initListener(){
        recalcular.setOnClickListener{
            onBackPressed()
        }
    }
    private fun initUI(result: Double){
        when(result){
            in 0.00 .. 18.50 -> {
                loadText(getString(R.string.bajoPeso), result.toString(), getString(R.string.descripcionBajoPeso), R.color.bajo_peso)
            }
            in 18.51 .. 24.99 -> {
                loadText(getString(R.string.pesoNormal), result.toString(),getString(R.string.descripcionPesoNormal), R.color.peso_normal)
            }
            in 25.00 .. 29.99 -> {
                loadText(getString(R.string.sobrePeso), result.toString(),getString(R.string.descripcionSobrePeso), R.color.sobre_peso)
            }
            in 30.00 .. 99.00 -> {
                loadText(getString(R.string.obesidad), result.toString(),getString(R.string.descripcionObesidad), R.color.obesidad)
            }
            else -> {

            }
        }
    }
    private fun loadText(typeImcstr: String, resultImcstr: String, descripstr: String, color: Int){
        typeimc.text = typeImcstr
        typeimc.setTextColor(ContextCompat.getColor(this, color))
        IMCresult.text = resultImcstr
        descriptionIMC.text = descripstr
    }
}