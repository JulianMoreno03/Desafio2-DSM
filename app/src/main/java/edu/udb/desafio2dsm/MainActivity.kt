package edu.udb.desafio2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta);
        // Configura el click listener para navegar a otra actividad
        btnCrearCuenta.setOnClickListener {
            goToRegisterActivity()
        }
    }


    // Función para ir a la SecondActivity
    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}