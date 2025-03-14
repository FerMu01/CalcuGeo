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
import kotlin.math.PI
import kotlin.math.sqrt

class Circulo : AppCompatActivity() {

    // Bandera para evitar ciclos de actualización
    private var updating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_circulo)

        // Ajuste de insets para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los EditText y al TextView de mensajes
        val editTextRadio = findViewById<EditText>(R.id.editTextNumberDecimal2)
        val editTextArea = findViewById<EditText>(R.id.editTextNumberDecimal3)
        val editTextPerimetro = findViewById<EditText>(R.id.editTextNumberDecimal4)
        val editTextDiametro = findViewById<EditText>(R.id.editTextNumberDecimal5)
        val textViewMensaje = findViewById<TextView>(R.id.textView7)

        // Función para actualizar el círculo a partir de los datos disponibles
        fun updateCirculo() {
            if (updating) return
            updating = true

            // Se obtienen los valores actuales de cada campo
            val radioVal = editTextRadio.text.toString().trim().toDoubleOrNull()
            val areaVal = editTextArea.text.toString().trim().toDoubleOrNull()
            val perimetroVal = editTextPerimetro.text.toString().trim().toDoubleOrNull()
            val diametroVal = editTextDiametro.text.toString().trim().toDoubleOrNull()

            // Se limpia el mensaje
            textViewMensaje.text = ""

            // Caso 1: Se ingresa el Radio
            if (radioVal != null) {
                val r = radioVal
                val computedArea = PI * r * r
                val computedPerimetro = 2 * PI * r
                val computedDiametro = 2 * r
                editTextArea.setText(computedArea.toString())
                editTextPerimetro.setText(computedPerimetro.toString())
                editTextDiametro.setText(computedDiametro.toString())
                updating = false
                return
            }

            // Caso 2: Se ingresa el Área
            if (areaVal != null) {
                val r = sqrt(areaVal / PI)
                val computedPerimetro = 2 * PI * r
                val computedDiametro = 2 * r
                editTextRadio.setText(r.toString())
                editTextPerimetro.setText(computedPerimetro.toString())
                editTextDiametro.setText(computedDiametro.toString())
                updating = false
                return
            }

            // Caso 3: Se ingresa el Perímetro
            if (perimetroVal != null) {
                val r = perimetroVal / (2 * PI)
                val computedArea = PI * r * r
                val computedDiametro = 2 * r
                editTextRadio.setText(r.toString())
                editTextArea.setText(computedArea.toString())
                editTextDiametro.setText(computedDiametro.toString())
                updating = false
                return
            }

            // Caso 4: Se ingresa el Diámetro
            if (diametroVal != null) {
                val r = diametroVal / 2
                val computedArea = PI * r * r
                val computedPerimetro = 2 * PI * r
                editTextRadio.setText(r.toString())
                editTextArea.setText(computedArea.toString())
                editTextPerimetro.setText(computedPerimetro.toString())
                updating = false
                return
            }

            // Si no hay datos suficientes se muestra un mensaje
            textViewMensaje.text = "Faltan datos para calcular."
            updating = false
        }

        // TextWatcher común para detectar cambios en cualquiera de los EditText
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                updateCirculo()
            }
        }

        // Se asocian los TextWatcher a cada campo
        editTextRadio.addTextChangedListener(watcher)
        editTextArea.addTextChangedListener(watcher)
        editTextPerimetro.addTextChangedListener(watcher)
        editTextDiametro.addTextChangedListener(watcher)
    }
}
