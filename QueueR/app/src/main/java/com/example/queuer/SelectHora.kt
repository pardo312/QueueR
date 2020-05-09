package com.example.queuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_select_hora.*

class SelectHora : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_hora)

        generarQr.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.generarQr -> goHora()
        }
    }
    private fun goHora() {
        val intent = Intent(this, Ticket::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finish()
    }
}
