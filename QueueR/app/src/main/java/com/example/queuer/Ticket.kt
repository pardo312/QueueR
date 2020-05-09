package com.example.queuer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class Ticket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        val qrCode: String = (1000..9999).random().toString()
        val qrTranslation:String ="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=2"
        val qrImage = qrTranslation+qrCode


        val imageView = findViewById<ImageView>(R.id.qrCode)
        Log.i("Holaaaaaaa",qrImage)
        DownLoadImageTask(imageView)
            .execute(qrImage)


    }
    // Class to download an image from url and display it into an image view
    private class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            return try {
                val inputStream = URL(urlOfImage).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
                null
            }
        }
        override fun onPostExecute(result: Bitmap?) {
            if(result!=null){
                // Display the downloaded image into image view
                Toast.makeText(imageView.context,"download success",Toast.LENGTH_SHORT).show()
                imageView.setImageBitmap(result)
            }else{
                Toast.makeText(imageView.context,"Error downloading",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
