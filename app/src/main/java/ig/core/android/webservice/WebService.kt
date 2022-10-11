package ig.core.android.webservice

import android.annotation.SuppressLint
import ig.core.android.utils.retrofit.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/****
 *
 * Created by TUON BONDOL on 11/18/17.
 *
 */

object WebService {

    private val baseLiveUrl: String = "http://139.59.109.135/public/api/"
    // private const val BASE_LOCAL_URL: String = "https://jsonplaceholder.typicode.com/"
    const val BASE_LOCAL_URL: String = "http://159.138.102.36:8082/LMSDemoBranch/api/v1/"
    //const val BASE_LOCAL_URL: String = "http://192.168.1.168:1337"
    //const val BASE_LOCAL_URL: String = "http://192.168.1.240:1337"
    private val baseUrlMockUp: String = "http://private-72f8f-androidcoreprojectkotlindocs.apiary-mock.com/"

    private const val mContentType: String = "Content-Type"
    private const val mContentTypeValue: String = "application/json"

    private const val mAuthorizationType: String = "Authorization"
    private const val mAuthorizationValue: String = "Bearer "

    private const val mUserToken: String = "token"

    private fun customOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val mSSLContext = SSLContext.getInstance("SSL")
            mSSLContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = mSSLContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

            return builder.build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_LOCAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
    }

    val requestApi: ServiceApi by lazy {
        val mOkHttpClientBuilder = customOkHttpClient().newBuilder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val mOkHttpClient = mOkHttpClientBuilder
                .addInterceptor { chain ->
                    var mRequest = chain.request()
                    mRequest = mRequest.newBuilder()
                            .header(mContentType, mContentTypeValue)
                            .build()
                    chain.proceed(mRequest)
                }
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build()

        retrofitBuilder.client(mOkHttpClient)
                .build()
                .create(ServiceApi::class.java)
    }

    fun getXMessage(errorThrowable: Throwable): ErrorMessage {
        var mErrorMessage: ErrorMessage? = null
        if (errorThrowable is HttpException) {
            println(errorThrowable.message())
            try {
                if (errorThrowable.code() != 200) {
                    mErrorMessage = ErrorMessage(errorThrowable.code(), errorThrowable.response()!!.headers().get("XMessage")!!)
                }
            } catch (e: Exception) {
                mErrorMessage = ErrorMessage(1000, "Can not get XMessage from server!")
                e.printStackTrace()
            }
        } else {
            return ErrorMessage(1000, "Can not get XMessage from server!")
        }
        return mErrorMessage!!
    }
}

data class ErrorMessage(var mStatusCode: Int, var xmessage: String = "")