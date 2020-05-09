package com.example.queuer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URL

class Ticket : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    val qrCode: String = (1000..9999).random().toString()
    val qrTranslation:String ="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

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
                Toast.makeText(imageView.context,"Turno Correctamente Asignado",Toast.LENGTH_SHORT).show()
                imageView.setImageBitmap(result)

            }else{
                Toast.makeText(imageView.context,"Error reservando turno. Porfavor Revisa tu conexion.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun writeFirebase() {

        db = FirebaseFirestore.getInstance()
        var lista:MutableList<String> = mutableListOf<String>()


        db.collection("QueueR").document("Tickets").get()
            .addOnSuccessListener { document ->

                val p = document.data?.size!!;
                val ticket = document.data?.get("tck$p") as MutableList<String>

                lista.add(ticket.get(0))
                lista.add(qrCode)
                db.collection("QueueR").document("Tickets").update(mapOf(
                        "tcl"+(p+1) to lista
                    ))


            }
            .addOnFailureListener { exception ->
                Log.d("InstruccionesActivity", "get failed with ", exception)
            }

    }
}
