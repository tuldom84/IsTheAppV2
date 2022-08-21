package com.dtz.netservice.ui.fragments.calls

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.callsadapter.CallsRecyclerAdapter

/**
 * Created by luis rafael on 12/03/18.
 */
interface InterfaceViewCalls : InterfaceView {

    fun setRecyclerAdapter(recyclerAdapter: CallsRecyclerAdapter)

}