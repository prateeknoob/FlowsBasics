package com.example.flowsbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.flowsbasics.ui.theme.FlowsBasicsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        producer(channel)
        consumer(channel)
        setContent {
            FlowsBasicsTheme {

            }
        }
    }
}

fun producer(channel: Channel<Int>) {
    CoroutineScope(Dispatchers.Main).launch {
        channel.send(1)
        channel.send(2)
        channel.send(3)
    }
}

fun consumer(channel: Channel<Int>) {
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("TAG", channel.receive().toString())
        Log.d("TAG", channel.receive().toString())
        Log.d("TAG", channel.receive().toString())
    }
}