package com.dtz.netservice.ui.fragments.social

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceViewSocial : InterfaceView {

    fun setValuePermission(dataSnapshot: DataSnapshot)
    fun successResult(dataSnapshot: DataSnapshot)

}