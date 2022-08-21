package com.dtz.netservice.ui.activities.mainparent

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 9/03/18.
 */
interface InterfaceViewMainParent : InterfaceView {
    fun signOutView()
    fun onFinishCount()
    fun setCheckedNavigationItem(id: Int)
    fun closeNavigationDrawer()
    fun requestApplyInsets()
    fun setDataAccounts(data: DataSnapshot)
}