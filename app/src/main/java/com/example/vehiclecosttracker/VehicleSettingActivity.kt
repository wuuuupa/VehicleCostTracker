package com.example.vehiclecosttracker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VehicleSettingActivity : AppCompatActivity() {

    private lateinit var etVehicleName: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_settings)

        etVehicleName = findViewById(R.id.etVehicleName)
        btnSave = findViewById(R.id.btnSaveVehicle)

        // 加载之前保存的车辆名
        val prefs = getSharedPreferences("vehicle_prefs", Context.MODE_PRIVATE)
        val savedName = prefs.getString("vehicle_name", "")
        etVehicleName.setText(savedName)

        btnSave.setOnClickListener {
            val name = etVehicleName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "请输入车辆名称", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 保存车辆名到 SharedPreferences
            prefs.edit().putString("vehicle_name", name).apply()
            Toast.makeText(this, "保存成功：$name", Toast.LENGTH_SHORT).show()

            // 可选：返回主界面
            finish()
        }
    }
}
