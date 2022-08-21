package com.dtz.netservice.app

import android.app.Application
import androidx.multidex.MultiDex
import com.chrisplus.rootmanager.RootManager
import com.dtz.netservice.di.component.AppComponent
import com.dtz.netservice.di.module.AppModule
import com.dtz.netservice.di.module.FirebaseModule
import com.dtz.netservice.di.component.DaggerAppComponent
import com.dtz.netservice.utils.Consts.SIZE_CACHE_FIREBASE
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by luis rafael on 28/03/18.
 */
class NetService : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
        lateinit var root: RootManager
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).firebaseModule(
            FirebaseModule()
        ).build()
        appComponent.inject(this)

        root = RootManager.getInstance()

        if (FirebaseApp.getApps(this).isNotEmpty()) {
            val database = FirebaseDatabase.getInstance()
            database.setPersistenceEnabled(true)
            database.setPersistenceCacheSizeBytes(SIZE_CACHE_FIREBASE)
        }

    }

}