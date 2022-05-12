package org.axen.fawx

import android.content.Context
import com.tencent.mm.opensdk.modelbase.BaseReq

internal object FAWXOnReqHandler {

    internal val interceptors: MutableList<IFAWXOnReqInterceptor> = mutableListOf()

    fun handleOnReq(context: Context, req: BaseReq) {
        val itr = interceptors.iterator()
        val processor = FAWXOnReqInterceptorProcessor(itr)
        processor.proceed(context, req)
    }


}