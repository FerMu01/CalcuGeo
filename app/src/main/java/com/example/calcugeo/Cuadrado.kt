package com.example.calcugeo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cuadrado : AppCompatActivity() {
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
        val editTextLado1 = findViewById<EditText>(R.id.editTextLado1)
        val editTextLado2 = findViewById<EditText>(R.id.editTextLado2)
        val editTextLado3 = findViewById<EditText>(R.id.editTextLado3)
        val editTextLado4 = findViewById<EditText>(R.id.editTextLado4)

        // Referencias a los TextView para Área y Perímetro
        val textViewArea = findViewById<TextView>(R.id.textView2)
        val textViewPerimetro = findViewById<TextView>(R.id.textView3)

        // Creamos un TextWatcher común que se activa al cambiar el contenido de cualquier EditText
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Usamos el valor del EditText que disparó el evento
                val sideStr = s.toString()
                updateCalculation(sideStr, textViewArea, textViewPerimetro)
            }

            override fun afterTextChanged(s: Editable?) { }
        }

        // Agregamos el mismo TextWatcher a los cuatro EditText
        editTextLado1.addTextChangedListener(textWatcher)
        editTextLado2.addTextChangedListener(textWatcher)
        editTextLado3.addTextChangedListener(textWatcher)
        editTextLado4.addTextChangedListener(textWatcher)
    }

    // Función que calcula y actualiza el área y perímetro
    private fun updateCalculation(sideStr: String, textViewArea: TextView, textViewPerimetro: TextView) {
        val side = sideStr.toDoubleOrNull()
        if (side != null && side > 0) {
            val area = side * side
            val perimetro = 4 * side
            textViewArea.text = "Area: $area"
            textViewPerimetro.text = "Perimetro: $perimetro"
        } else {
            textViewArea.text = "Area: -"
            textViewPerimetro.text = "Perimetro: -"
        }
    }
}
