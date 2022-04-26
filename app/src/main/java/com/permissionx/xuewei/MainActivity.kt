package com.permissionx.xuewei

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.permissionx.xuewei.mqtt.MQTTClient
import com.permissionx.xuewei.mqtt.MqttClientCallBack

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
    val mPermissionList = ArrayList<String>()
    val mRequestCode = 0x1
    private lateinit var mqttSend:Button
    private lateinit var mqtt:MQTTClient


    override fun onCreate(savedInstanceState: Bundle?) {
        initPermission()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mqttSend = findViewById(R.id.mqtt_send)
        initListener()
        //mqtt使用例子
        mqtt= MQTTClient.newInstance(this)
        mqtt.setOnCallback(object : MqttClientCallBack {
            override fun onMessageReceive(message: String?) {
                Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    //动态权限设置
    private fun initPermission() {
        mPermissionList.clear()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                mPermissionList.add(permission)
            }
        }
        if (mPermissionList.isNotEmpty()) {
            // 后续操作...c
            ActivityCompat.requestPermissions(this@MainActivity, permissions, mRequestCode)
            Toast.makeText(this@MainActivity,"请授予权限", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListener(){
        mqttSend.setOnClickListener {
            mqtt.publish("jerry sb")
        }
    }

    private fun initView(){
        mqttSend = findViewById(R.id.mqtt_send)
    }
}