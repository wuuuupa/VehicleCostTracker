package com.example.vehiclecosttracker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VehicleSettingsActivity : AppCompatActivity() {

    private lateinit var etVehicleName: EditText
    private lateinit var btnSaveVehicle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_settings)

        etVehicleName = findViewById(R.id.etVehicleName)
        btnSaveVehicle = findViewById(R.id.btnSaveVehicle)

        // 获取SharedPreferences
        val sharedPref = getSharedPreferences("VehiclePrefs", Context.MODE_PRIVATE)

        // 如果之前有保存的车辆名称，就显示
        val savedVehicleName = sharedPref.getString("vehicle_name", "")
        etVehicleName.setText(savedVehicleName)

        btnSaveVehicle.setOnClickListener {
            val vehicleName = etVehicleName.text.toString().trim()

            if (vehicleName.isEmpty()) {
                Toast.makeText(this, "请输入车辆名称", Toast.LENGTH_SHORT).show()
            } else {
                // 保存到 SharedPreferences
                sharedPref.edit().putString("vehicle_name", vehicleName).apply()
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
