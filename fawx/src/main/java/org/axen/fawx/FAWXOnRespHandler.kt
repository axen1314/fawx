package org.axen.fawx

import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp

internal object FAWXOnRespHandler {
    private val mPendingCallback: MutableMap<String, (BaseResp)->Unit> = mutableMapOf()

    internal fun handleReqCallback(req: BaseReq, callback: (BaseResp)->Unit) {
        if (req.transaction.isNullOrBlank()) {
            req.transaction = System.currentTimeMillis().toString()
        }
        mPendingCallback[req.transaction] = callback
    }

    internal fun handleOnResp(resp: BaseResp) {
        val transaction = resp.transaction
        if (!transaction.isNullOrBlank()) {
            val callback = mPendingCallback[transaction]
            callback?.invoke(resp)
            mPendingCallback.remove(transaction)
        }
    }
}