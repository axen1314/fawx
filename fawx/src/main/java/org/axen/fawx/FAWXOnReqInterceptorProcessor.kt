package org.axen.fawx

import android.content.Context
import com.tencent.mm.opensdk.modelbase.BaseReq

class FAWXOnReqInterceptorProcessor internal constructor(
    private val iterator: Iterator<IFAWXOnReqInterceptor>
) {
    fun proceed(context: Context, req: BaseReq) {
        if (iterator.hasNext()) {
            val handler = iterator.next()
            handler.intercept(context, req, this)
        }
    }
}