package edu.udb.desafio2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val btnComida = findViewById<Button>(R.id.btnComidas)
        val btnBebida = findViewById<Button>(R.id.btnBebidas);

        val btnOrdenes = findViewById<Button>(R.id.btnOrdenes);
        // Configura el click listener para navegar a otra actividad
        btnComida.setOnClickListener {
            goToComida()
        }

        btnBebida.setOnClickListener {
            goToBebida()
        }

        btnOrdenes.setOnClickListener {
            goToOrdenes()
        }
    }

    // Función para ir a la comida
    private fun goToComida() {
        val intent = Intent(this, ComidaActivity::class.java)
        startActivity(intent)
    }

    // Función para ir a la bebida
    private fun goToBebida() {
        val intent = Intent(this, BebidaActivity::class.java)
        startActivity(intent)
    }

    // Función para ir a las ordenes
    private fun goToOrdenes() {
        val intent = Intent(this, OrdenesActivity::class.java)
        startActivity(intent)
    }



}