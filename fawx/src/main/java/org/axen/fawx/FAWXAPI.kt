package org.axen.fawx

import android.content.Context
import android.content.Intent
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

object FAWXAPI {

    private var wxapi: IWXAPI? = null

    internal var registered: Boolean = false
        private set

    val interceptors = FAWXOnReqHandler.interceptors

    fun setup(context: Context, wechatAppId: String): Boolean {
        if (!registered) {
            wxapi = WXAPIFactory.createWXAPI(context, wechatAppId)
            registered = wxapi?.registerApp(wechatAppId) ?: false
        }
        return registered
    }

    fun setup(wxapi: IWXAPI) {
        if (!registered) {
            registered = true
            this.wxapi = wxapi
        }
    }

    fun isWechatAppInstalled(): Boolean = wxapi?.isWXAppInstalled?:false

    fun openWechatApp(): Boolean = wxapi?.openWXApp()?:false

    fun sendReq(req: BaseReq, callback: (BaseResp) -> Unit): Boolean {
        return wxapi?.let {
            FAWXOnRespHandler.handleReqCallback(req, callback)
            it.sendReq(req)
        } ?: false
    }

    internal fun handleIntent(intent: Intent, handler: IWXAPIEventHandler) = wxapi?.handleIntent(intent, handler)
}