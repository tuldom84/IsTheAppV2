package com.dtz.netservice.ui.fragments.notifications

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.notifyadapter.NotifyMessageRecyclerAdapter
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 17/03/19.
 */
interface InterfaceViewNotifyMessage : InterfaceView {

    fun setRecyclerAdapter(recyclerAdapter: NotifyMessageRecyclerAdapter)
    fun setValueState(dataSnapshot: DataSnapshot)

}