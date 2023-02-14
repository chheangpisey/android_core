package ig.core.android.view.ui.activity.hilt.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author piseychheang
 * Created on 2/14/23 at 11:21 AM
 * Modified By piseychheang on 2/14/23 at 11:21 AM
 * File name: NetworkAPIKey.kt
 */

@Singleton
class NetworkAPIKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ")
            .build()

        return chain.proceed(newRequest)
    }
}