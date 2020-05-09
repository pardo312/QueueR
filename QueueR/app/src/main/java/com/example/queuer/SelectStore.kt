package com.example.queuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select_store.*

class SelectStore : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_store)

        araButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.araButton -> goAra()
        }
    }
    private fun goAra() {
        val intent = Intent(this, Ticket::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finish()
    }
}
