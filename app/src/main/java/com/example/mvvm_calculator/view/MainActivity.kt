package com.example.mvvm_calculator.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_calculator.R
import com.example.mvvm_calculator.viewmodel.VolumeViewModel

// MainActivity sebagai tampilan utama yang menggunakan MVVM untuk menghitung volume balok
class MainActivity : AppCompatActivity() {

    // Inisialisasi ViewModel menggunakan delegasi `by viewModels()`
    private val viewModel: VolumeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi elemen-elemen UI
        val etLength = findViewById<EditText>(R.id.etLength) // Input panjang balok
        val etWidth = findViewById<EditText>(R.id.etWidth)   // Input lebar balok
        val etHeight = findViewById<EditText>(R.id.etHeight) // Input tinggi balok
        val btnCalculate = findViewById<Button>(R.id.btnCalculate) // Tombol untuk menghitung volume
        val tvResult = findViewById<TextView>(R.id.tvResult) // TextView untuk menampilkan hasil perhitungan

        // Observasi LiveData `volume` dari ViewModel
        // Setiap kali `volume` berubah, UI akan diperbarui secara otomatis
        viewModel.volume.observe(this) { volume ->
            tvResult.text = volume // Menampilkan hasil perhitungan pada TextView
        }

        // Menangani klik tombol hitung
        btnCalculate.setOnClickListener {
            // Mengambil nilai input dan mengonversinya menjadi Double, default 0.0 jika kosong atau tidak valid
            val length = etLength.text.toString().toDoubleOrNull() ?: 0.0
            val width = etWidth.text.toString().toDoubleOrNull() ?: 0.0
            val height = etHeight.text.toString().toDoubleOrNull() ?: 0.0

            // Memanggil fungsi `calculateVolume()` di ViewModel untuk menghitung volume
            viewModel.calculateVolume(length, width, height)
        }
    }
}
