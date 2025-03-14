package com.example.calcugeo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Rombo : AppCompatActivity() {

    private var updating = false // Bandera para evitar actualizaciones recíprocas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rombo)

        // Referencias a los elementos de la interfaz
        val editTextDiagonalMayor = findViewById<EditText>(R.id.editTextDiagonalMayor)
        val editTextDiagonalMenor = findViewById<EditText>(R.id.editTextDiagonalMenor)
        val editTextLado1 = findViewById<EditText>(R.id.editTextLado1)
        val textViewArea = findViewById<TextView>(R.id.textView4)
        val textViewPerimetro = findViewById<TextView>(R.id.textView5)
        val textViewMensaje = findViewById<TextView>(R.id.textView6)
        val buttonReiniciar = findViewById<Button>(R.id.button2)

        // Inicializar mensaje
        textViewMensaje.text = "Ingrese dos datos para empezar"
        buttonReiniciar.isEnabled = false // El botón reiniciar inicia deshabilitado

        fun updateRombo() {
            if (updating) return
            updating = true

            val dMStr = editTextDiagonalMayor.text.toString().trim()
            val dmStr = editTextDiagonalMenor.text.toString().trim()
            val ladoStr = editTextLado1.text.toString().trim()

            val dM = dMStr.toDoubleOrNull()
            val dm = dmStr.toDoubleOrNull()
            val L = ladoStr.toDoubleOrNull()

            var computedA: Double? = null
            var computedP: Double? = null
            var computedL: Double? = null
            var computedDM: Double? = null
            var computedDMenor: Double? = null

            textViewMensaje.text = ""

            when {
                dM != null && dm != null -> {
                    computedL = sqrt((dM / 2) * (dM / 2) + (dm / 2) * (dm / 2))
                    computedA = (dM * dm) / 2
                    computedP = 4 * computedL
                }
                dM != null && L != null -> {
                    if (L < dM / 2) {
                        textViewMensaje.text = "Error: Lado menor que la mitad de la diagonal mayor."
                        updating = false
                        return
                    }
                    computedDMenor = 2 * sqrt(L * L - (dM / 2) * (dM / 2))
                    computedA = (dM * computedDMenor) / 2
                    computedP = 4 * L
                }
                dm != null && L != null -> {
                    if (L < dm / 2) {
                        textViewMensaje.text = "Error: Lado menor que la mitad de la diagonal menor."
                        updating = false
                        return
                    }
                    computedDM = 2 * sqrt(L * L - (dm / 2) * (dm / 2))
                    computedA = (computedDM * dm) / 2
                    computedP = 4 * L
                }
                else -> {
                    textViewMensaje.text = "Faltan datos para calcular."
                    updating = false
                    return
                }
            }

            computedL?.let { editTextLado1.setText(it.toString()) }
            computedDM?.let { editTextDiagonalMayor.setText(it.toString()) }
            computedDMenor?.let { editTextDiagonalMenor.setText(it.toString()) }
            computedA?.let { textViewArea.text = "Área: $it" }
            computedP?.let { textViewPerimetro.text = "Perímetro: $it" }

            // Bloquear campos después del cálculo
            editTextDiagonalMayor.isEnabled = false
            editTextDiagonalMenor.isEnabled = false
            editTextLado1.isEnabled = false

            buttonReiniciar.isEnabled = true // Habilitar botón reiniciar
            updating = false
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateRombo()
            }
        }

        editTextDiagonalMayor.addTextChangedListener(watcher)
        editTextDiagonalMenor.addTextChangedListener(watcher)
        editTextLado1.addTextChangedListener(watcher)

        buttonReiniciar.setOnClickListener {
            editTextDiagonalMayor.text.clear()
            editTextDiagonalMenor.text.clear()
            editTextLado1.text.clear()
            textViewArea.text = "Área: -"
            textViewPerimetro.text = "Perímetro: -"
            textViewMensaje.text = "Ingrese dos datos para empezar"

            editTextDiagonalMayor.isEnabled = true
            editTextDiagonalMenor.isEnabled = true
            editTextLado1.isEnabled = true

            buttonReiniciar.isEnabled = false // Deshabilitar el botón de reinicio hasta un nuevo cálculo
        }
    }
}
