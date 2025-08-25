package com.example.flowsbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    // val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        GlobalScope.launch(Dispatchers.Main) {
            val time = measureTimeMillis {
                produceses()
                    .buffer(3)
                    .collect {
                        delay(1500)
                        Log.d("TAG", it.toString())
                    }
            }
            Log.d("TAG", time.toString())
        }


//        GlobalScope.launch {
//            produce()
//                .map {
//                    it * 2
//                }
//                .filter {
//                    it < 8
//                }
//                .collect {
//                    Log.d("TAG", it.toString())
//                }
//        }


//        GlobalScope.launch {
//            val data = produce().toList()
//            Log.d("TAG", data.toString())
//        }
//        GlobalScope.launch {
//            produceses().onStart {
//                emit(-1)
//                Log.d("TAG", "Starting")
//            }.onCompletion {
//                emit(11)
//                Log.d("TAG", "Completed")
//            }.onEach {
//                Log.d("TAG", "Emitting $it")
//            }.collect {
//                Log.d("TAG", it.toString())
//            }
//        }


//        GlobalScope.launch {
//            val data: Flow<Int> = produce()
//            data.collect {
//                Log.d("TAG-1", it.toString())
//            }
//        }


//        GlobalScope.launch {
//            delay(3500)
//            job.cancel()
//        }
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

fun produceses(): Flow<Int> {
    return flow<Int> {
        val list = listOf(1, 2, 3, 4, 5, 6, 7)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }
}