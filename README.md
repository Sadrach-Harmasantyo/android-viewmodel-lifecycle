# Android ViewModel & Lifecycle - Retaining TextView Value on Device Rotation

## Introduction
This project demonstrates how to use **Android ViewModel** and **Lifecycle** to retain the calculated value in a **TextView** when the device is rotated. The case study involves calculating the volume of a rectangular prism (block) based on user input.

## Features
- Uses **MVVM (Model-View-ViewModel) Architecture**.
- Retains the calculated volume even when the device is rotated.
- Utilizes **LiveData** to observe changes and update the UI.
- Prevents unnecessary recalculations when configuration changes occur.

## Tech Stack
- **Kotlin**
- **Android ViewModel**
- **LiveData**
- **Android Lifecycle Components**

## Project Structure
```
├── view
│   ├── MainActivity.kt  # UI and user interaction
│
├── viewmodel
│   ├── VolumeViewModel.kt  # Business logic and state handling
│
├── layout
│   ├── activity_main.xml  # UI layout for input and result display
│
├── AndroidManifest.xml
│
└── README.md
```

## How It Works
### 1. ViewModel (VolumeViewModel.kt)
```kotlin
package com.example.mvvm_calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VolumeViewModel : ViewModel() {
    private val _volume = MutableLiveData<String>()
    val volume: LiveData<String> get() = _volume

    fun calculateVolume(length: Double, width: Double, height: Double) {
        val result = length * width * height
        _volume.value = formatNumber(result)
    }

    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString() // Show whole number if integer
        } else {
            String.format("%.2f", value) // Show 2 decimal places if fractional
        }
    }
}
```

### 2. View (MainActivity.kt)
```kotlin
package com.example.mvvm_calculator.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_calculator.R
import com.example.mvvm_calculator.viewmodel.VolumeViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: VolumeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etLength = findViewById<EditText>(R.id.etLength)
        val etWidth = findViewById<EditText>(R.id.etWidth)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        viewModel.volume.observe(this) { volume ->
            tvResult.text = volume
        }

        btnCalculate.setOnClickListener {
            val length = etLength.text.toString().toDoubleOrNull() ?: 0.0
            val width = etWidth.text.toString().toDoubleOrNull() ?: 0.0
            val height = etHeight.text.toString().toDoubleOrNull() ?: 0.0

            viewModel.calculateVolume(length, width, height)
        }
    }
}
```

### 3. Layout (activity_main.xml)
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/etLength"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter Length"
        android:inputType="numberDecimal" />

    <EditText
        android:id="@+id/etWidth"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter Width"
        android:inputType="numberDecimal" />

    <EditText
        android:id="@+id/etHeight"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter Height"
        android:inputType="numberDecimal" />

    <Button
        android:id="@+id/btnCalculate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Calculate Volume" />

    <TextView
        android:id="@+id/tvResult"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:textStyle="bold"
        android:text="Result" />
</LinearLayout>
```

## Why Use ViewModel?
- **Retains Data on Configuration Changes:** The `ViewModel` survives activity recreation when the device is rotated.
- **Separates UI Logic from Business Logic:** The UI only observes changes, while all calculations happen inside the `ViewModel`.
- **Efficient UI Updates:** LiveData ensures that updates happen only when necessary, preventing unnecessary calculations.

## Running the Project
1. Clone the repository.
2. Open it in **Android Studio**.
3. Build and run the app on an emulator or physical device.
4. Enter values for **length, width, and height**, then press **Calculate Volume**.
5. Rotate the device and observe that the result is retained.

## Expected Output
### Example 1: Whole Number Result
**Input:** 10 × 10 × 10  
**Output:** 1000  

### Example 2: Decimal Result
**Input:** 5.5 × 3.2 × 2.1  
**Output:** 36.96  

## Conclusion
This project demonstrates how to use **Android ViewModel and Lifecycle** to retain important data across configuration changes. It follows best practices by keeping business logic separate from UI logic, ensuring a smooth user experience.
