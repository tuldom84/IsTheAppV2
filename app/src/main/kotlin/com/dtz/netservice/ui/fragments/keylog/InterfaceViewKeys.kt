package com.dtz.netservice.ui.fragments.keylog

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.keysadapter.KeysRecyclerAdapter
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 18/03/18.
 */
interface InterfaceViewKeys : InterfaceView {

    fun setValueState(dataSnapshot: DataSnapshot)
    fun setRecyclerAdapter(recyclerAdapter: KeysRecyclerAdapter)

}