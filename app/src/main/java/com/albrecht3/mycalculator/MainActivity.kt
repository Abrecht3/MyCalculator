package com.albrecht3.mycalculator

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.albrecht3.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNumber = ""
    private var currentNumber = ""
    private var operator = ""
    private var point = ""
    private var operation = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        binding.apply {
            //Se inician los hijos(botones del teclado) del gridlayout
            binding.gridKeyboard.children.filterIsInstance<Button>().forEach { button->

                button.setOnClickListener {
                    //se guarda el texto de cada boton como un string en la variable
                    val btnTxt = button.text.toString()
                    when{
                        btnTxt.matches(Regex("[0-9]"))->{
                            if (operator.isEmpty()){
                                firstNumber+=btnTxt
                                tvOperation.text = firstNumber
                            }else{
                                currentNumber+=btnTxt
                                operation = "$firstNumber $operator $currentNumber"
                                tvOperation.text = operation
                                if (firstNumber.contains(".") || currentNumber.contains(".")){
                                    result = floatExpression(firstNumber,currentNumber,operator)
                                    tvResult.text=result
                                }else{
                                    result = evalExpression(firstNumber,currentNumber,operator)
                                    tvResult.text=result
                                }
                            }
                        }
                        btnTxt.matches(Regex("[+\\-x/]"))->{
                            if (tvOperation.text.toString().isNotEmpty()){
                                operator = btnTxt
                                tvOperation.text = firstNumber+operator
                            }
                        }
                        btnTxt=="="->{
                            if (currentNumber.isNotEmpty() && operator.isNotEmpty()){
                                if (firstNumber.contains(".") || currentNumber.contains(".")){
                                    result = floatExpression(firstNumber,currentNumber,operator)
                                    firstNumber=result
                                    currentNumber=""
                                    tvResult.text=result
                                    tvOperation.text = firstNumber
                                }else{
                                    result = evalExpression(firstNumber,currentNumber,operator)
                                    firstNumber=result
                                    currentNumber=""
                                    tvResult.text=result
                                    tvOperation.text = firstNumber
                                }
                            }
                        }
                        btnTxt=="C"->{
                            firstNumber = ""
                            currentNumber = ""
                            operator=""
                            result=""
                            tvResult.text=""
                            tvOperation.text=""
                        }
                        btnTxt=="."->{
                            if (operator.isEmpty()){
                                if (! firstNumber.contains(".")){
                                    if (firstNumber.isEmpty()) firstNumber+="0$btnTxt"
                                    else firstNumber+=btnTxt
                                    tvOperation.text = firstNumber
                                }
                            }else{
                                if (!currentNumber.contains(".")){
                                    if (currentNumber.isEmpty()) currentNumber+="0$btnTxt"
                                    else currentNumber+=btnTxt
                                    tvResult.text =currentNumber
                                }

                            }
                        }
                    }
                }

            }
        }
    }

    private fun floatExpression(num1: String, num2: String, operator: String): String {
        return when(operator){
            "+"->(num1.toFloat()+num2.toFloat()).toString()
            "-"->(num1.toFloat()-num2.toFloat()).toString()
            "x"->(num1.toFloat()*num2.toFloat()).toString()
            "/"->(num1.toFloat()/num2.toFloat()).toString()
            else -> ""
        }
    }

    private fun evalExpression(num1: String, num2: String, operator: String): String {
        return when(operator){
            "+"->(num1.toInt()+num2.toInt()).toString()
            "-"->(num1.toInt()-num2.toInt()).toString()
            "x"->(num1.toInt()*num2.toInt()).toString()
            "/"->(num1.toInt()/num2.toInt()).toString()
            else -> ""
        }
    }
}