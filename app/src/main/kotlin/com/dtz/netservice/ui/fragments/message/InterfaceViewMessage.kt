package com.dtz.netservice.ui.fragments.message

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.smsadapter.SmsRecyclerAdapter

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceViewMessage : InterfaceView {

    fun setRecyclerAdapter(smsRecyclerAdapter: SmsRecyclerAdapter)

}