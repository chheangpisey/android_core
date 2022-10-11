package ig.core.android.service.implement

import ig.core.android.service.model.PostResponse
import ig.core.android.service.model.RequestLogin
import ig.core.android.service.model.ResponseLogin
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.StateFlowResponseTest
import ig.core.android.webservice.ServiceApi
import ig.core.android.webservice.WebService
import java.util.concurrent.Flow
import javax.inject.Inject

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
class ApiServiceImpl @Inject constructor(private val webService: WebService) {

    suspend fun getPostTest():ArrayList<PostResponse> = webService.requestApi.getPostTest()

    /*** User Login*/
    suspend fun login(req: RequestLogin): ResponseLogin = webService.requestApi.lcLoginState(req)

    suspend fun loginTest(req: RequestLogin): StateFlowResponseTest<ResponseLogin> =
        webService.requestApi.lcLoginStateTest(req)


}