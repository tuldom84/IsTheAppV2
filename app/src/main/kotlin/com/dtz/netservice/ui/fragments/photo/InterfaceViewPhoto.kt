package com.dtz.netservice.ui.fragments.photo

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.photoadapter.PhotoRecyclerAdapter
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceViewPhoto : InterfaceView{

    fun setRecyclerAdapter(recyclerAdapter: PhotoRecyclerAdapter)

    fun setValueState(dataSnapshot: DataSnapshot)
    fun setValuePermission(dataSnapshot: DataSnapshot)

}