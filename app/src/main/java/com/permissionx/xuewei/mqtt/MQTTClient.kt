package com.permissionx.xuewei.mqtt

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


@SuppressLint("MissingPermission")
class MQTTClient private constructor(context: Context) {

    /**
     * 连接MQTT服务器
     */
    private fun doClientConnection() {
        client?.let { client ->
            if (!client.isConnected) {
                try {
                    client.connect(conOpt, null, iMqttActionListener)
                } catch (e: MqttException) {
                    Log.e(TAG, "$e")
                }
            }
        }
    }

    /**
     * MQTT是否连接成功回调方法
     */
    private val iMqttActionListener: IMqttActionListener = object : IMqttActionListener {
        override fun onSuccess(arg0: IMqttToken) {
            Log.i(TAG, "连接成功 ")
            try { // 订阅myTopic话题
                client?.subscribe(Constant.MQ.subscribeTopic, 1)
            } catch (e: MqttException) {
                Log.e(TAG, "$e")
            }
        }

        override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
            Log.e(TAG, "$exception")
            // 连接失败，重连
        }
    }

    /**
     * MQTT接收消息回调
     */
    private val mqttCallback: MqttCallback = object : MqttCallback {
        @Throws(Exception::class)
        override fun messageArrived(topic: String, message: MqttMessage) {
            val str1 = String(message.payload)
            mMqttServiceCallback?.onMessageReceive(str1)
            val str2 = topic + ";qos:" + message.qos + ";retained:" + message.isRetained
            Log.i(TAG, "messageArrived:$str1")
            Log.i(TAG, str2)
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.i(TAG, "$token")
        }

        override fun connectionLost(cause: Throwable?) {
            // 失去连接，重连
            Log.e(TAG, "$cause")
        }
    }

    /**
     * 关闭MQTT
     */
    fun stop() {
        if (client == null || client?.isConnected == false) return
        try {
            client!!.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    /**
     * 设置回调监听
     */
    fun setOnCallback(mqttCallBack: MqttClientCallBack) {
        mMqttServiceCallback = mqttCallBack
    }

    /**
     * 发送消息
     */
    fun publish(msg: String) {
        if (client == null || msg.isBlank()) return
        try {
            client?.publish(Constant.MQ.publishTopic, msg.toByteArray(), 0, false)
        } catch (e: MqttException) {
            Log.e(TAG, "$e")
        }
    }

    companion object {
        fun newInstance(context: Context): MQTTClient {
            return MQTTClient(context)
        }

        private val TAG = MQTTClient::class.java.simpleName
        private var conOpt: MqttConnectOptions? = null
        private var client: MqttAndroidClient? = null
        private var mMqttServiceCallback: MqttClientCallBack? = null
    }

    init { // 服务器地址（协议+地址+端口号）
        val uri = Constant.MQ.host
        client = MqttAndroidClient(context, uri, Constant.MQ.clientId)
        // 设置MQTT监听并且接受消息
        client?.setCallback(mqttCallback)
        conOpt = MqttConnectOptions()
        conOpt?.apply {
            // 清除缓存
            isCleanSession = true
            // 设置超时时间，单位：秒
            connectionTimeout = 10
            // 心跳包发送间隔，单位：秒
            keepAliveInterval = 20
            // 用户名
            userName = Constant.MQ.userName
            // 密码
            password = Constant.MQ.passWord.toCharArray() //将字符串转换为字符串数组
        }
        // last will message
        var doConnect = true
        val message = "{\"terminal_uid\":\"${Constant.MQ.clientId}\"}"
        Log.e(TAG, "message是:$message")
        val topic = Constant.MQ.subscribeTopic
        val qos = 0
        val retained = false
        try {
            conOpt?.setWill(topic, message.toByteArray(), qos, retained)
        } catch (e: Exception) {
            Log.i(TAG, "Exception Occured", e)
            doConnect = false
            iMqttActionListener.onFailure(null, e)
        }
        if (doConnect) {
            doClientConnection()
        }
    }
}
