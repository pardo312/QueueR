package com.example.queueradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.queueradmin.TicketData
import com.example.queueradmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_nike.*

class Nike : AppCompatActivity() {
    private val REQUEST_CODE_QR_SCAN = 101;
    var scannedResult: String = ""
    val uid =arrayListOf<String?>();
    val tickets = arrayListOf<TicketData>()
    lateinit var db: FirebaseFirestore
    var listaTot:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nike)
        firebase();
        scan.setOnClickListener {
            run {
                IntentIntegrator(this@Nike).initiateScan();
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                scannedResult = result.contents

                if(tickets.any{it.ID == scannedResult})
                {
                    val intent = Intent(this, Camisa::class.java)
                    var i = 0

                    while (i<tickets.size) {

                        if(tickets[i].ID == scannedResult)
                        {
                            Log.d("Wenas",tickets[i].ID)
                            val b = Bundle()
                            b.putString("ID", tickets[i].ID)
                            b.putString("Horario", tickets[i].Horario)
                            intent.putExtras(b)
                        }
                        i++

                    }
                    startActivity(intent)
                }
                else
                {
                    textOut.text = "Ticket not found."
                }
            } else {
                textOut.text = "Scan failed"
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    fun comentariosSeccion(){
        val ref = FirebaseDatabase.getInstance().getReference("Codigos")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (camisaSnapshot in dataSnapshot.children) {
                    uid.add(camisaSnapshot.key)
                    val camisa = camisaSnapshot.getValue(TicketData::class.java)
                    tickets.add(camisa!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })

    }
    private fun firebase() {

            db = FirebaseFirestore.getInstance()


            db.collection("QueueR").document("Tickets").addSnapshotListener { document, e ->
                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    if (document != null) {
                        var i = 1
                        while (i < document.data?.size!! + 1) {
                            listaTot = document.data?.get("tck" + i) as MutableList<String>
                            //Local
                            var ticket = TicketData(listaTot.get(0),listaTot.get(1))
                            tickets.add(ticket)
                            i++
                        }

                    } else {
                        Log.d("InstruccionesActivity", "No such document")
                    }
                }
        }



}
