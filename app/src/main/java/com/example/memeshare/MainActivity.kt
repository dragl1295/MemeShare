package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme()
    }

    private fun loadMeme() {

        imageUrl = "https://meme-api.herokuapp.com/gimme"
       binding.progressBar.visibility = View.VISIBLE

       val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, imageUrl, null,
           { response ->
               imageUrl = response.getString("url")
               Glide.with(this).load(imageUrl).listener(object : RequestListener<Drawable> {
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       binding.progressBar.visibility = View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       binding.progressBar.visibility = View.GONE
                       return false
                   }

               }).into(binding.imageView)
           },
           {
               Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
           })


      MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
   }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey,checkout this new meme $imageUrl")
        val chooser = Intent.createChooser(intent,"share this meme using ...")
        startActivity(chooser)
    }


    fun nextMeme(view: View) {
        loadMeme()
    }


}