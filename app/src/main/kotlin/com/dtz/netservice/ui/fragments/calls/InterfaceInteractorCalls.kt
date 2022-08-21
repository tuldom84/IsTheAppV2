package com.dtz.netservice.ui.fragments.calls

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 12/03/18.
 */
@PerActivity
interface InterfaceInteractorCalls<V : InterfaceViewCalls> : InterfaceInteractor<V> {
    fun stopAudioCallHolder()
}