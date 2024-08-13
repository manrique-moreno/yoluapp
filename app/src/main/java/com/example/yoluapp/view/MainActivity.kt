package com.example.yoluapp.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.yoluapp.R
import com.example.yoluapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var sound : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sound = MediaPlayer.create(this, R.raw.sonido_logo)
        paintSplash()

    }

    private fun animateImage() {
        binding.imageView2.visibility = View.VISIBLE
        binding.clLoading.visibility = View.GONE
        binding.imageView2.animate().apply {
            duration = 2000
            rotationYBy(360f)
            sound.start()
        }.withEndAction{
            binding.imageView2.animate().apply {
                duration = 2000
                rotationXBy(10f)
            }
            animateText();

        }
    }

    private fun animateText(){
        binding.idText.animate().apply {
            duration = 2000
            translationYBy(-100f)
        }.withEndAction {
            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
        }
    }

    private fun paintSplash(){
        val database = FirebaseDatabase.getInstance()
        val splashRef = database.getReference("Splash")

        splashRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val imageUrl = snapshot.child("url").getValue(String::class.java)
                if (imageUrl != null) {
                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(object : CustomTarget<Drawable>() {

                            override fun onResourceReady(
                                resource: Drawable,
                                transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                            ) {
                                // Establece la imagen en el ImageView
                                binding.clMain.background = resource
                                animateImage()
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}

                            override fun onLoadFailed(errorDrawable: Drawable?) {
                                // Maneja el caso si la carga falla
                                Toast.makeText(this@MainActivity, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
                            }
                        })
                } else {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}