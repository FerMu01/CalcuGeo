package com.example.calcugeo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia al botón cuadrado
        val btnCuadrado = findViewById<ImageButton>(R.id.btnCuadrado)
        val btnRombo = findViewById<ImageButton>(R.id.btnRombo)
        val btnCirculo = findViewById<ImageButton>(R.id.btnCirculo)
        val btnRectangulo = findViewById<ImageButton>(R.id.btnRectangulo)


        // Configurar clic para abrir la actividad Cuadrado
        btnCuadrado.setOnClickListener {
            val intent = Intent(this, Cuadrado::class.java)
            startActivity(intent)
        }
        btnRombo.setOnClickListener {
            val intent = Intent(this, Rombo::class.java)
            startActivity(intent)
        }
        btnCirculo.setOnClickListener {
            val intent = Intent(this, Circulo::class.java)
            startActivity(intent)
        }
        btnRectangulo.setOnClickListener {
            val intent = Intent(this, Rectangulo::class.java)
            startActivity(intent)
        }
    }
}
