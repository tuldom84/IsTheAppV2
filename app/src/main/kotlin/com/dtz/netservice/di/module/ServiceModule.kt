package com.dtz.netservice.di.module

import android.app.Service
import android.content.Context
import com.dtz.netservice.di.PerService
import com.dtz.netservice.services.calls.InteractorCalls
import com.dtz.netservice.services.calls.InterfaceInteractorCalls
import com.dtz.netservice.services.calls.InterfaceServiceCalls
import com.dtz.netservice.services.sms.InteractorSms
import com.dtz.netservice.services.sms.InterfaceInteractorSms
import com.dtz.netservice.services.sms.InterfaceServiceSms
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 13/03/18.
 */
@Module
class ServiceModule(var service:Service) {

    @Provides
    fun provideContext(): Context = service.applicationContext

    @Provides
    @PerService
    fun provideInterfaceInteractorCalls(interactor: InteractorCalls<InterfaceServiceCalls>): InterfaceInteractorCalls<InterfaceServiceCalls> = interactor

    @Provides
    @PerService
    fun provideInterfaceInteractorSms(interactor: InteractorSms<InterfaceServiceSms>): InterfaceInteractorSms<InterfaceServiceSms> = interactor

}