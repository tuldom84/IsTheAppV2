package com.dtz.netservice.ui.activities.login

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 9/03/18.
 */
@PerActivity
interface InterfaceInteractorLogin<V : InterfaceViewLogin> : InterfaceInteractor<V> {
    fun signInDisposable(email: String, pass: String)
}