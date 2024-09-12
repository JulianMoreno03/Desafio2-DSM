package edu.udb.desafio2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Referencia a FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Instanciamos la autenticacion
        auth = FirebaseAuth.getInstance();

        //obtenemos inputs
        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)

        //obtenemos los btn
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta);

        //Funcion registro
        btnRegistro.setOnClickListener{
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
             if(email.isNotEmpty() && password.isNotEmpty()) {
                // Llamamos a la función de iniciar sesión
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            // Redirige al usuario al menu
                            goToMenuActivity()
                        } else {
                            Toast.makeText(this, "Error al iniciar sesión: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
        }




        // Configura el click listener para navegar a otra actividad
        btnCrearCuenta.setOnClickListener {
            goToRegisterActivity()
        }
    }


    // Función para ir a la RegisterActivity
    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    // Función para ir a la MenuActivity
    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

}