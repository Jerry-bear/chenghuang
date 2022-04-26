package com.permissionx.xuewei.mqtt

interface MqttClientCallBack {
    fun onMessageReceive(message: String?)
}