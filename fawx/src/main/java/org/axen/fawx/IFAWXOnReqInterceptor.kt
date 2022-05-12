package org.axen.fawx

import android.content.Context
import com.tencent.mm.opensdk.modelbase.BaseReq

interface IFAWXOnReqInterceptor {
    fun intercept(
        context: Context,
        req: BaseReq,
        processor: FAWXOnReqInterceptorProcessor
    )
}