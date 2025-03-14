package com.example.calcugeo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sqrt

class Rectangulo : AppCompatActivity() {

    // Bandera para evitar ciclos de actualización
    private var updating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rectangulo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los componentes
        val editTextArea = findViewById<EditText>(R.id.editTextNumberDecimal6)
        val editTextPerimetro = findViewById<EditText>(R.id.editTextNumberDecimal7)
        val editTextLadoB = findViewById<EditText>(R.id.editTextNumberDecimal8)
        val editTextLadoA = findViewById<EditText>(R.id.editTextNumberDecimal9)
        val editTextDiagonal = findViewById<EditText>(R.id.editTextNumberDecimal10)
        val textViewMensaje = findViewById<TextView>(R.id.textView8)
        val btnReset = findViewById<Button>(R.id.button)

        // Inicialmente, el botón Reiniciar está deshabilitado
        btnReset.isEnabled = false

        // Función que actualiza los cálculos según los datos ingresados
        fun updateRectangulo() {
            if (updating) return
            updating = true

            // Leer valores: si algún campo está vacío, toDoubleOrNull devuelve null
            val areaVal = editTextArea.text.toString().trim().toDoubleOrNull()
            val perimVal = editTextPerimetro.text.toString().trim().toDoubleOrNull()
            val ladoAVal = editTextLadoA.text.toString().trim().toDoubleOrNull()
            val ladoBVal = editTextLadoB.text.toString().trim().toDoubleOrNull()
            val diagVal = editTextDiagonal.text.toString().trim().toDoubleOrNull()

            textViewMensaje.text = ""

            // Verificar cuántos campos tienen datos
            val provided = mutableListOf<String>()
            if (areaVal != null) provided.add("Area")
            if (perimVal != null) provided.add("Perimetro")
            if (ladoAVal != null) provided.add("LadoA")
            if (ladoBVal != null) provided.add("LadoB")
            if (diagVal != null) provided.add("Diagonal")

            if (provided.size < 2) {
                val missing = listOf("Area", "Perimetro", "LadoA", "LadoB", "Diagonal").filter { it !in provided }
                textViewMensaje.text = "Ingrese dos valores para empezar. Falta: ${missing.joinToString(", ")}"
                btnReset.isEnabled = false
                updating = false
                return
            }

            // Caso 1: LadoA y LadoB → calcular Área, Perímetro y Diagonal
            if (ladoAVal != null && ladoBVal != null) {
                val computedArea = ladoAVal * ladoBVal
                val computedPerim = 2 * (ladoAVal + ladoBVal)
                val computedDiag = sqrt(ladoAVal * ladoAVal + ladoBVal * ladoBVal)
                editTextArea.setText(computedArea.toString())
                editTextPerimetro.setText(computedPerim.toString())
                editTextDiagonal.setText(computedDiag.toString())

                // Bloquear todos los campos y habilitar el botón de reinicio
                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 2: LadoA y Área → calcular LadoB, Perímetro y Diagonal
            if (ladoAVal != null && areaVal != null) {
                if (ladoAVal == 0.0) {
                    textViewMensaje.text = "LadoA no puede ser 0."
                    updating = false
                    return
                }
                val computedLadoB = areaVal / ladoAVal
                val computedPerim = 2 * (ladoAVal + computedLadoB)
                val computedDiag = sqrt(ladoAVal * ladoAVal + computedLadoB * computedLadoB)
                editTextLadoB.setText(computedLadoB.toString())
                editTextPerimetro.setText(computedPerim.toString())
                editTextDiagonal.setText(computedDiag.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 3: LadoB y Área → calcular LadoA, Perímetro y Diagonal
            if (ladoBVal != null && areaVal != null) {
                if (ladoBVal == 0.0) {
                    textViewMensaje.text = "LadoB no puede ser 0."
                    updating = false
                    return
                }
                val computedLadoA = areaVal / ladoBVal
                val computedPerim = 2 * (computedLadoA + ladoBVal)
                val computedDiag = sqrt(computedLadoA * computedLadoA + ladoBVal * ladoBVal)
                editTextLadoA.setText(computedLadoA.toString())
                editTextPerimetro.setText(computedPerim.toString())
                editTextDiagonal.setText(computedDiag.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 4: LadoA y Perímetro → calcular LadoB, Área y Diagonal
            if (ladoAVal != null && perimVal != null) {
                val computedLadoB = (perimVal / 2) - ladoAVal
                if (computedLadoB <= 0) {
                    textViewMensaje.text = "Datos incorrectos: LadoB calculado es menor o igual a 0."
                    updating = false
                    return
                }
                val computedArea = ladoAVal * computedLadoB
                val computedDiag = sqrt(ladoAVal * ladoAVal + computedLadoB * computedLadoB)
                editTextLadoB.setText(computedLadoB.toString())
                editTextArea.setText(computedArea.toString())
                editTextDiagonal.setText(computedDiag.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 5: LadoB y Perímetro → calcular LadoA, Área y Diagonal
            if (ladoBVal != null && perimVal != null) {
                val computedLadoA = (perimVal / 2) - ladoBVal
                if (computedLadoA <= 0) {
                    textViewMensaje.text = "Datos incorrectos: LadoA calculado es menor o igual a 0."
                    updating = false
                    return
                }
                val computedArea = ladoBVal * computedLadoA
                val computedDiag = sqrt(computedLadoA * computedLadoA + ladoBVal * ladoBVal)
                editTextLadoA.setText(computedLadoA.toString())
                editTextArea.setText(computedArea.toString())
                editTextDiagonal.setText(computedDiag.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 6: Área y Perímetro → resolver sistema para hallar LadoA y LadoB y calcular Diagonal
            if (areaVal != null && perimVal != null) {
                val s = perimVal / 2.0
                val discriminant = s * s - 4 * areaVal
                if (discriminant < 0) {
                    textViewMensaje.text = "Datos inconsistentes: discriminante negativo."
                    updating = false
                    return
                }
                val sqrtDisc = sqrt(discriminant)
                val computedLadoA = (s + sqrtDisc) / 2.0
                val computedLadoB = s - computedLadoA
                if (computedLadoA <= 0 || computedLadoB <= 0) {
                    textViewMensaje.text = "Datos inconsistentes: lados no positivos."
                    updating = false
                    return
                }
                val computedDiag = sqrt(computedLadoA * computedLadoA + computedLadoB * computedLadoB)
                editTextLadoA.setText(computedLadoA.toString())
                editTextLadoB.setText(computedLadoB.toString())
                editTextDiagonal.setText(computedDiag.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 7: LadoA y Diagonal → calcular LadoB, Área y Perímetro
            if (ladoAVal != null && diagVal != null) {
                if (diagVal * diagVal < ladoAVal * ladoAVal) {
                    textViewMensaje.text = "Datos incorrectos: Diagonal menor que LadoA."
                    updating = false
                    return
                }
                val computedLadoB = sqrt(diagVal * diagVal - ladoAVal * ladoAVal)
                val computedArea = ladoAVal * computedLadoB
                val computedPerim = 2 * (ladoAVal + computedLadoB)
                editTextLadoB.setText(computedLadoB.toString())
                editTextArea.setText(computedArea.toString())
                editTextPerimetro.setText(computedPerim.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 8: LadoB y Diagonal → calcular LadoA, Área y Perímetro
            if (ladoBVal != null && diagVal != null) {
                if (diagVal * diagVal < ladoBVal * ladoBVal) {
                    textViewMensaje.text = "Datos incorrectos: Diagonal menor que LadoB."
                    updating = false
                    return
                }
                val computedLadoA = sqrt(diagVal * diagVal - ladoBVal * ladoBVal)
                val computedArea = ladoBVal * computedLadoA
                val computedPerim = 2 * (computedLadoA + ladoBVal)
                editTextLadoA.setText(computedLadoA.toString())
                editTextArea.setText(computedArea.toString())
                editTextPerimetro.setText(computedPerim.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 9: Área y Diagonal → resolver para hallar LadoA y LadoB y calcular Perímetro
            if (areaVal != null && diagVal != null) {
                val disc = diagVal * diagVal * diagVal * diagVal - 4 * areaVal * areaVal
                if (disc < 0) {
                    textViewMensaje.text = "Datos inconsistentes: no se pueden calcular lados con Área y Diagonal."
                    updating = false
                    return
                }
                val sqrtDisc = sqrt(disc)
                val aSquared1 = (diagVal * diagVal + sqrtDisc) / 2.0
                val aSquared2 = (diagVal * diagVal - sqrtDisc) / 2.0
                var computedLadoA = if (aSquared1 > 0) sqrt(aSquared1) else 0.0
                var computedLadoB = if (computedLadoA != 0.0) areaVal / computedLadoA else 0.0
                if (computedLadoA <= 0 || computedLadoB <= 0) {
                    computedLadoA = if (aSquared2 > 0) sqrt(aSquared2) else 0.0
                    computedLadoB = if (computedLadoA != 0.0) areaVal / computedLadoA else 0.0
                }
                if (computedLadoA <= 0 || computedLadoB <= 0) {
                    textViewMensaje.text = "Datos inconsistentes: no se pueden obtener lados válidos con Área y Diagonal."
                    updating = false
                    return
                }
                val computedPerim = 2 * (computedLadoA + computedLadoB)
                editTextLadoA.setText(computedLadoA.toString())
                editTextLadoB.setText(computedLadoB.toString())
                editTextPerimetro.setText(computedPerim.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            // Caso 10: Perímetro y Diagonal → resolver para hallar LadoA y LadoB y calcular Área
            if (perimVal != null && diagVal != null) {
                val s = perimVal / 2.0
                val product = (s * s - diagVal * diagVal) / 2.0
                if (product <= 0) {
                    textViewMensaje.text = "Datos inconsistentes: no se puede calcular lados con Perímetro y Diagonal."
                    updating = false
                    return
                }
                val discriminant = s * s - 4 * product
                if (discriminant < 0) {
                    textViewMensaje.text = "Datos inconsistentes: discriminante negativo con Perímetro y Diagonal."
                    updating = false
                    return
                }
                val sqrtDisc = sqrt(discriminant)
                val computedLadoA = (s + sqrtDisc) / 2.0
                val computedLadoB = s - computedLadoA
                if (computedLadoA <= 0 || computedLadoB <= 0) {
                    textViewMensaje.text = "Datos inconsistentes: lados no positivos con Perímetro y Diagonal."
                    updating = false
                    return
                }
                val computedArea = computedLadoA * computedLadoB
                editTextLadoA.setText(computedLadoA.toString())
                editTextLadoB.setText(computedLadoB.toString())
                editTextArea.setText(computedArea.toString())

                editTextArea.isEnabled = false
                editTextPerimetro.isEnabled = false
                editTextLadoA.isEnabled = false
                editTextLadoB.isEnabled = false
                editTextDiagonal.isEnabled = false
                btnReset.isEnabled = true
                updating = false
                return
            }

            textViewMensaje.text = "Datos insuficientes o inválidos para calcular."
            updating = false
        }

        // Definir el TextWatcher que invoca updateRectangulo() en cada cambio
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                updateRectangulo()
            }
        }

        // Asignar el watcher a cada EditText
        editTextArea.addTextChangedListener(watcher)
        editTextPerimetro.addTextChangedListener(watcher)
        editTextLadoA.addTextChangedListener(watcher)
        editTextLadoB.addTextChangedListener(watcher)
        editTextDiagonal.addTextChangedListener(watcher)

        // Función para reiniciar: limpiar TODOS los EditText y habilitarlos para nuevos datos
        fun resetFields() {
            // Remover temporalmente el watcher para evitar disparar cálculos durante el reinicio
            editTextArea.removeTextChangedListener(watcher)
            editTextPerimetro.removeTextChangedListener(watcher)
            editTextLadoA.removeTextChangedListener(watcher)
            editTextLadoB.removeTextChangedListener(watcher)
            editTextDiagonal.removeTextChangedListener(watcher)

            // Limpiar los campos
            editTextArea.text.clear()
            editTextPerimetro.text.clear()
            editTextLadoA.text.clear()
            editTextLadoB.text.clear()
            editTextDiagonal.text.clear()

            // Habilitar los campos para nuevos datos
            editTextArea.isEnabled = true
            editTextPerimetro.isEnabled = true
            editTextLadoA.isEnabled = true
            editTextLadoB.isEnabled = true
            editTextDiagonal.isEnabled = true

            btnReset.isEnabled = false
            textViewMensaje.text = "Ingrese dos valores para empezar."

            // Re-agregar el watcher a cada EditText
            editTextArea.addTextChangedListener(watcher)
            editTextPerimetro.addTextChangedListener(watcher)
            editTextLadoA.addTextChangedListener(watcher)
            editTextLadoB.addTextChangedListener(watcher)
            editTextDiagonal.addTextChangedListener(watcher)
        }

        btnReset.setOnClickListener { resetFields() }
    }
}
