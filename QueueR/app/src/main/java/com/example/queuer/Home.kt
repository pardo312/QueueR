package com.example.queuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class Home : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ticketButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.ticketButton -> goTicket()
        }
    }
    private fun goTicket() {
        val intent = Intent(this, SelectStore::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finish()
    }
}
