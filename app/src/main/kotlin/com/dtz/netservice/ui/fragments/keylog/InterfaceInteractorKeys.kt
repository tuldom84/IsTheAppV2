package com.dtz.netservice.ui.fragments.keylog

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 18/03/18.
 */
@PerActivity
interface InterfaceInteractorKeys<V : InterfaceViewKeys> : InterfaceInteractor<V> {
    fun valueEventEnableKeys()
}