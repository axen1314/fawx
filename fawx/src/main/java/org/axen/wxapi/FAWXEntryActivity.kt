package org.axen.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import org.axen.fawx.FAWXAPI
import org.axen.fawx.FAWXOnReqHandler
import org.axen.fawx.FAWXOnRespHandler

class FAWXEntryActivity: Activity(), IWXAPIEventHandler {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntentByFAWXAPI(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntentByFAWXAPI(intent)
    }

    private fun handleIntentByFAWXAPI(intent: Intent) {
        try {
            if (FAWXAPI.registered) {
                FAWXAPI.handleIntent(intent, this)
            } else {
                Log.d("FAWX", "FAWXAPI未初始化，请先调用setup方法进行初始化")
                finish()
            }
        } catch (e: Exception) {
            Log.d("FAWX", "Intent处理异常！")
            e.printStackTrace()
            finish()
        }
    }

    override fun onReq(req: BaseReq) {
        FAWXOnReqHandler.handleOnReq(this, req)
        finish()
    }

    override fun onResp(resp: BaseResp) {
        FAWXOnRespHandler.handleOnResp(resp)
        finish()
    }
}