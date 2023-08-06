package com.laioffer.spotify.repository

import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

// 用于调用api，与数据进行交互
// Home <- NetworkApi <-(NetWorkModule) Retrofit
class HomeRepository @Inject constructor(private val networkApi: NetworkApi) {
    // 把api 放在IO Thread 执行
    suspend fun getHomeSections(): List<Section>  = withContext(Dispatchers.IO) {
        //  如果不加！！ -> 会报错
        //  需要提供的是Non-Null 但是这里提供的是Nullable
        //  我把Nullable 的 转换成 Non-Nullable
        //  如果出现错误，我来承担责任
        delay(3000)
        networkApi.getHomeFeed().execute().body()!!
    }

}