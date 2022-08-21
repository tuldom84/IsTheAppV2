package com.dtz.netservice.services.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.dtz.netservice.app.NetService
import com.dtz.netservice.di.component.DaggerServiceComponent
import com.dtz.netservice.di.component.ServiceComponent
import com.dtz.netservice.di.module.ServiceModule
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 13/03/18.
 */
abstract class BaseService : Service(), InterfaceService {


    private lateinit var compositeDisposable: CompositeDisposable

    companion object {
        @JvmStatic
        lateinit var serviceComponent: ServiceComponent
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(ServiceModule(this))
                .appComponent(NetService.appComponent).build()
        compositeDisposable = CompositeDisposable()
    }

    override fun getComponent(): ServiceComponent? = serviceComponent


    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clearDisposable() = compositeDisposable.clear()


}