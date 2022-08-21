package com.dtz.netservice.ui.activities.register

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 10/03/18.
 */
@PerActivity
interface InterfaceInteractorRegister<V : InterfaceViewRegister> : InterfaceInteractor<V> {

    fun signUpDisposable(email: String, pass: String)

}