package com.example.flowsbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.flowsbasics.ui.theme.FlowsBasicsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        GlobalScope.launch {
            val data: Flow<Int> = produce()
            data.collect {
                Log.d("TAG-1", it.toString())
            }
        }
        GlobalScope.launch {
            val data: Flow<Int> = produce()
            data.collect {
                delay(2500)
                Log.d("TAG-2", it.toString())
            }
        }
        
//        GlobalScope.launch {
//            delay(3500)
//            job.cancel()
//        }


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

fun produce() = flow<Int> {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.forEach {
        delay(1000)
        emit(it)
    }
}