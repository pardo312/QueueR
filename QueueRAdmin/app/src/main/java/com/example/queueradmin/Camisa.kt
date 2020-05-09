package com.example.queueradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_camisa.*


class Camisa : AppCompatActivity() {

    var uid: String = ""
    var Horario: String = ""
    var ID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camisa)

        val b = intent.extras

        if (b != null) {

            uid = b.getString("uid").toString()
            ID = b.getString("ID").toString()
            Horario = b.getString("Horario").toString()
        }

        descripcionTxt.text = Horario
    }


    fun comprar(view: View){
        val camisas = arrayListOf<TicketData>()
        val ref = FirebaseDatabase.getInstance().getReference("Codigos")
        val childUpdates = HashMap<String, Any>()

        val post = TicketData(Horario,ID)
        val postValues = post.toMap()

        childUpdates["/$uid"] = postValues
        ref.updateChildren(childUpdates)
    }





}
