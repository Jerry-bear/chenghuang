package com.permissionx.xuewei.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.permissionx.xuewei.R
import com.permissionx.xuewei.mqtt.MQTTClient
import com.permissionx.xuewei.mqtt.MqttClientCallBack

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
    val mPermissionList = ArrayList<String>()
    val mRequestCode = 0x1
    private lateinit var yde_btn:Button
    private lateinit var yadd_btn:Button
    private lateinit var xde_btn:Button
    private lateinit var xadd_btn:Button
    private lateinit var dyde_btn:Button
    private lateinit var dyadd_btn:Button
    private lateinit var dxde_btn:Button
    private lateinit var dxadd_btn:Button
    private lateinit var xychange_edt:EditText
    private lateinit var dxychange_edt:EditText
    private lateinit var mqtt:MQTTClient


    override fun onCreate(savedInstanceState: Bundle?) {
        initPermission()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yde_btn = findViewById(R.id.mainact_btn_yde)
        yadd_btn = findViewById(R.id.mainact_btn_yadd)
        xde_btn = findViewById(R.id.mainact_btn_xde)
        xadd_btn = findViewById(R.id.mainact_btn_xadd)
        dyde_btn = findViewById(R.id.mainact_btn_dyde)
        dyadd_btn = findViewById(R.id.mainact_btn_dyadd)
        dxde_btn = findViewById(R.id.mainact_btn_dxde)
        dxadd_btn = findViewById(R.id.mainact_btn_dxadd)
        xychange_edt = findViewById(R.id.mainact_edt_xychange)
        dxychange_edt = findViewById(R.id.mainact_edt_dxychange)
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

        yde_btn.setOnClickListener {
            val text=xychange_edt.text
            mqtt.publish("y-"+text)
        }
        yadd_btn.setOnClickListener {
            val text=xychange_edt.text
            mqtt.publish("y+"+text)
        }
        xde_btn.setOnClickListener {
            val text=xychange_edt.text
            mqtt.publish("x-"+text)
        }
        xadd_btn.setOnClickListener {
            val text=xychange_edt.text
            mqtt.publish("x+"+text)
        }
        dyde_btn.setOnClickListener {
            val text=dxychange_edt.text
            mqtt.publish("dy-"+text)
        }
        dyadd_btn.setOnClickListener {
            val text=dxychange_edt.text
            mqtt.publish("dy+"+text)
        }
        dxde_btn.setOnClickListener {
            val text=dxychange_edt.text
            mqtt.publish("dx-"+text)
        }
        dxadd_btn.setOnClickListener {
            val text=dxychange_edt.text
            mqtt.publish("dx+"+text)
        }
    }

    private fun initView(){
        yde_btn = findViewById(R.id.mainact_btn_yde)
    }
}