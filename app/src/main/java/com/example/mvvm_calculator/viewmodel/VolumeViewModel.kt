package com.example.mvvm_calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel untuk mengelola perhitungan volume balok dan mempertahankan data saat rotasi layar
class VolumeViewModel : ViewModel() {

    // MutableLiveData untuk menyimpan hasil perhitungan volume dalam bentuk String
    private val _volume = MutableLiveData<String>()

    // LiveData agar UI dapat mengamati perubahan nilai volume tanpa bisa mengubahnya langsung
    val volume: LiveData<String> get() = _volume

    // Fungsi untuk menghitung volume balok
    fun calculateVolume(length: Double, width: Double, height: Double) {
        val result = length * width * height // Menghitung volume balok
        _volume.value = formatNumber(result) // Menyimpan hasil dalam format yang sesuai
    }

    // Fungsi untuk memformat hasil perhitungan volume
    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()  // Jika hasil bilangan bulat, tampilkan tanpa desimal (misal: 1000 bukan 1000.0)
        } else {
            String.format("%.2f", value)  // Jika ada desimal, batasi 2 angka di belakang koma (misal: 123.456 → 123.46)
        }
    }
}
