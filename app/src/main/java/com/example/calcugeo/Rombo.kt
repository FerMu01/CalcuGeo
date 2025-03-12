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

class Rombo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rombo)

        // Ajuste para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los EditText
        val editTextDiagonalMayor = findViewById<EditText>(R.id.editTextDiagonalMayor)
        val editTextDiagonalMenor = findViewById<EditText>(R.id.editTextDiagonalMenor)
        val editTextLado = findViewById<EditText>(R.id.editTextLado1)

        // Referencias a los TextView para Área y Perímetro
        val textViewArea = findViewById<TextView>(R.id.textView4)
        val textViewPerimetro = findViewById<TextView>(R.id.textView5)

        // Creamos un TextWatcher que se activará en cualquiera de los EditText
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateCalculations(
                    editTextDiagonalMayor.text.toString(),
                    editTextDiagonalMenor.text.toString(),
                    editTextLado.text.toString(),
                    textViewArea,
                    textViewPerimetro
                )
            }

            override fun afterTextChanged(s: Editable?) { }
        }

        // Agregar el TextWatcher a cada EditText
        editTextDiagonalMayor.addTextChangedListener(textWatcher)
        editTextDiagonalMenor.addTextChangedListener(textWatcher)
        editTextLado.addTextChangedListener(textWatcher)
    }

    // Función para actualizar los cálculos de área y perímetro
    private fun updateCalculations(
        diagMayorStr: String,
        diagMenorStr: String,
        ladoStr: String,
        textViewArea: TextView,
        textViewPerimetro: TextView
    ) {
        // Cálculo del área usando las diagonales
        val diagMayor = diagMayorStr.toDoubleOrNull()
        val diagMenor = diagMenorStr.toDoubleOrNull()
        if (diagMayor != null && diagMayor > 0 && diagMenor != null && diagMenor > 0) {
            val area = (diagMayor * diagMenor) / 2
            textViewArea.text = "Area: $area"
        } else {
            textViewArea.text = "Area: -"
        }

        // Cálculo del perímetro usando el lado
        val lado = ladoStr.toDoubleOrNull()
        if (lado != null && lado > 0) {
            val perimetro = 4 * lado
            textViewPerimetro.text = "Perimetro: $perimetro"
        } else {
            textViewPerimetro.text = "Perimetro: -"
        }
    }
}
