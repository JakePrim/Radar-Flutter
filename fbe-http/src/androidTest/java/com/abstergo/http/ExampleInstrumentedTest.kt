package com.abstergo.http

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fbe.http.BaseAPIResult
import com.fbe.http.FbeHttp
import com.fbe.http.onFailure
import com.fbe.http.onSuccess
import com.google.gson.JsonElement
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.http.GET

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    interface WanAndroidService {
        @GET("/test")
        suspend fun test(): BaseAPIResult<JsonElement>
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.abstergo.http.test", appContext.packageName)

        //初始化
        FbeHttp.getInstance().with()
            .baseUrl("https://www.wanandroid.com/")
            .isDebug(true)
            .build(appContext)

        runBlocking {
            FbeHttp.getInstance().create<WanAndroidService>().test()
                .onSuccess {
                    println("execute userInfo success ==> $it")
                }
                .onFailure {
                    println("execute test error ===> $it")
                }
        }
    }
}