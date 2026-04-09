package com.example.nfctester

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private lateinit var btnOk: Button
    private lateinit var tvStatus: TextView
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOk = findViewById(R.id.btn_ok)
        tvStatus = findViewById(R.id.tv_status)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            tvStatus.text = getString(R.string.nfc_not_available)
        }

        btnOk.setOnClickListener { proceed() }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or
                NfcAdapter.FLAG_READER_NFC_B or
                NfcAdapter.FLAG_READER_NFC_F or
                NfcAdapter.FLAG_READER_NFC_V or
                NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }

    // Called on a background thread by the NFC stack
    override fun onTagDiscovered(tag: Tag?) {
        runOnUiThread { proceed() }
    }

    private fun proceed() {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}
