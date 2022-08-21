package com.dtz.netservice.di.component

import com.dtz.netservice.app.NetService
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.dtz.netservice.di.module.AppModule
import com.dtz.netservice.di.module.FirebaseModule
import com.dtz.netservice.services.accessibilityData.AccessibilityDataService
import com.dtz.netservice.services.notificationService.NotificationService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by luis rafael on 13/03/18.
 */
@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class])
interface AppComponent {

    fun inject(app: NetService)
    fun inject(accessibilityDataService: AccessibilityDataService)
    fun inject(notificationService: NotificationService)
    fun getInterfaceFirebase(): InterfaceFirebase

}