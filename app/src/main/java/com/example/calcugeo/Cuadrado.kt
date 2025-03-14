package com.example.calcugeo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sqrt

class Cuadrado : AppCompatActivity() {

    // Bandera para evitar actualizaciones recíprocas
    private var updating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuadrado)

        // Ajuste de insets para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los EditText
        val editTextLado = findViewById<EditText>(R.id.editTextLado)
        val editTextArea = findViewById<EditText>(R.id.textView2)
        val editTextPerimetro = findViewById<EditText>(R.id.textView3)
        val editTextDiagonal = findViewById<EditText>(R.id.editTextNumberDecimal)

        // Función auxiliar para actualizar los valores a partir del lado
        fun actualizarDesdeLado(lado: Double) {
            val area = lado * lado
            val perimetro = 4 * lado
            val diagonal = lado * sqrt(2.0)
            updating = true
            editTextArea.setText(area.toString())
            editTextPerimetro.setText(perimetro.toString())
            editTextDiagonal.setText(diagonal.toString())
            updating = false
        }

        // TextWatcher para el EditText del Lado
        editTextLado.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (updating) return
                val str = s.toString()
                if (str.isEmpty()) {
                    updating = true
                    editTextArea.setText("")
                    editTextPerimetro.setText("")
                    editTextDiagonal.setText("")
                    updating = false
                    return
                }
                try {
                    val lado = str.toDouble()
                    actualizarDesdeLado(lado)
                } catch (e: NumberFormatException) {
                    // En caso de error de conversión, se puede notificar o ignorar.
                }
            }
        })

        // TextWatcher para el EditText del Área
        editTextArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (updating) return
                val str = s.toString()
                if (str.isEmpty()) {
                    updating = true
                    editTextLado.setText("")
                    editTextPerimetro.setText("")
                    editTextDiagonal.setText("")
                    updating = false
                    return
                }
                try {
                    val area = str.toDouble()
                    val lado = sqrt(area)
                    updating = true
                    editTextLado.setText(lado.toString())
                    val perimetro = 4 * lado
                    val diagonal = lado * sqrt(2.0)
                    editTextPerimetro.setText(perimetro.toString())
                    editTextDiagonal.setText(diagonal.toString())
                    updating = false
                } catch (e: NumberFormatException) {
                    // Manejar error
                }
            }
        })

        // TextWatcher para el EditText del Perímetro
        editTextPerimetro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (updating) return
                val str = s.toString()
                if (str.isEmpty()) {
                    updating = true
                    editTextLado.setText("")
                    editTextArea.setText("")
                    editTextDiagonal.setText("")
                    updating = false
                    return
                }
                try {
                    val perimetro = str.toDouble()
                    val lado = perimetro / 4.0
                    updating = true
                    editTextLado.setText(lado.toString())
                    val area = lado * lado
                    val diagonal = lado * sqrt(2.0)
                    editTextArea.setText(area.toString())
                    editTextDiagonal.setText(diagonal.toString())
                    updating = false
                } catch (e: NumberFormatException) {
                    // Manejar error
                }
            }
        })

        // TextWatcher para el EditText de la Diagonal
        editTextDiagonal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (updating) return
                val str = s.toString()
                if (str.isEmpty()) {
                    updating = true
                    editTextLado.setText("")
                    editTextArea.setText("")
                    editTextPerimetro.setText("")
                    updating = false
                    return
                }
                try {
                    val diagonal = str.toDouble()
                    val lado = diagonal / sqrt(2.0)
                    updating = true
                    editTextLado.setText(lado.toString())
                    val area = lado * lado
                    val perimetro = 4 * lado
                    editTextArea.setText(area.toString())
                    editTextPerimetro.setText(perimetro.toString())
                    updating = false
                } catch (e: NumberFormatException) {
                    // Manejar error
                }
            }
        })
    }
}
