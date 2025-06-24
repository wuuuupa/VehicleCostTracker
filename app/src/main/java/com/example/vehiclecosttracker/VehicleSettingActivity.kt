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

        // 加载已有的车辆名
        val prefs = getSharedPreferences("vehicle_prefs", Context.MODE_PRIVATE)
        val savedName = prefs.getString("vehicle_name", "")
        etVehicleName.setText(savedName)

        btnSaveVehicle.setOnClickListener {
            val name = etVehicleName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "请输入车辆名称", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 保存到 SharedPreferences
            prefs.edit().putString("vehicle_name", name).apply()
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()

            finish() // 返回上一页
        }
    }
}
