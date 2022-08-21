package com.dtz.netservice.services.base

import android.content.Context
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser

/**
 * Created by luis rafael on 22/03/18.
 */
interface InterfaceInteractorService<S : InterfaceService> {

    fun onAttach(service: S)

    fun onDetach()

    fun getService(): S?

    fun isNullService() : Boolean

    fun getContext(): Context

    fun firebase(): InterfaceFirebase

    fun user(): FirebaseUser?

}