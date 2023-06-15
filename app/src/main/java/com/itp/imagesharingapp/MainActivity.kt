package com.itp.imagesharingapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var btn_loadImage: Button? =null
    var imageView:ImageView?=null
    var progressBar:ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_loadImage=findViewById(R.id.btn_loadImage)
        imageView=findViewById(R.id.imageView)
        progressBar=findViewById(R.id.progressBar)

         loadImage()
        btn_loadImage?.setOnClickListener {
            loadImage()
        }
        }

    private fun loadImage() {

        progressBar!!.visibility=View.VISIBLE
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show()

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                var url=response.getString("url")

                Glide.with(this).load(url).addListener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility= View.GONE
                        Toast.makeText(this@MainActivity,"unable to load the image",Toast.LENGTH_SHORT).show()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility= View.GONE
                        return false
                    }

                }).into(imageView!!)
            },
            Response.ErrorListener {
                Toast.makeText(this,"unable to get the response::"+it.localizedMessage,Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    //code to get the images from API calls.

}
