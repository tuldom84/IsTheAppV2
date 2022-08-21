package com.dtz.netservice.ui.fragments.photo

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 20/03/18.
 */
@PerActivity
interface InterfaceInteractorPhoto<V : InterfaceViewPhoto> : InterfaceInteractor<V> {
    fun getPhotoCamera(facing:Int)
    fun valueEventEnablePhoto()
    fun valueEventEnablePermission()
}