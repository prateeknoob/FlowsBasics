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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        GlobalScope.launch(Dispatchers.Main) {
            val result = prooo()
            delay(6000)
            result.collect{
                Log.d("FLOW", "Item- $it")
            }
        }



//        GlobalScope.launch(Dispatchers.Main) {
//            val result = produ()
//            result.collect {
//                Log.d("TAG", "Item- $it")
//            }
//        }
//
//        GlobalScope.launch(Dispatchers.Main) {
//            val result = produ()
//            delay(2500)
//            result.collect {
//                Log.d("TAG-2", "Item- $it")
//            }
//        }
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//
//                produceses()
//                    .map {
//                        delay(1000)
//                        it * 2
//                        Log.d("TAG", "Map Thread-${Thread.currentThread().name}")
//                    }
//                    .flowOn(Dispatchers.IO)
//                    .filter {
//                        delay(500)
//                        Log.d("TAG", "Filter Thread-${Thread.currentThread().name}")
//                        it < 8
//                    }
//                    .flowOn(Dispatchers.Main)
//                    .collect {
//                        Log.d("TAG", "Collector Thread-${Thread.currentThread().name}")
//                    }
//            } catch (e: Exception) {
//                Log.d("TAG", e.message.toString())
//            }
//        }


//        GlobalScope.launch(Dispatchers.Main) {
//            val time = measureTimeMillis {
//                produceses()
//                    .buffer(3)
//                    .collect {
//                        delay(1500)
//                        Log.d("TAG", it.toString())
//                    }
//            }
//            Log.d("TAG", time.toString())
//        }


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
            Log.d("TAG", "Emitter Thread-${Thread.currentThread().name}")
            emit(it)
            throw Exception("Emitter Error")
        }
    }.catch {
        Log.d("TAG5", "Emitter catch-${it.message.toString()}")
    }
}

fun produ(): Flow<Int> {
    var mutablesharedflo = MutableSharedFlow<Int>(1)
    GlobalScope.launch {
        val list = listOf<Int>(1, 2, 3, 4, 5)
        list.forEach {
            mutablesharedflo.emit(it)
            delay(1000)
        }
    }
    return mutablesharedflo
}

private fun prooo():Flow<Int>{
    var mutablesharedflo = MutableStateFlow(10)
    GlobalScope.launch {
        delay(2000)
        mutablesharedflo.emit(20)
        delay(2000)
        mutablesharedflo.emit(30)
    }
    return mutablesharedflo
}