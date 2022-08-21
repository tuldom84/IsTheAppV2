package com.dtz.netservice.di.component

import com.dtz.netservice.di.PerService
import com.dtz.netservice.di.module.ServiceModule
import com.dtz.netservice.services.calls.CallsService
import com.dtz.netservice.services.sms.SmsService
import com.dtz.netservice.services.social.MonitorService
import dagger.Component

/**
 * Created by luis rafael on 13/03/18.
 */
@PerService
@Component(dependencies = [AppComponent::class], modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(callsService: CallsService)
    fun inject(smsService: SmsService)
    fun inject(monitorService: MonitorService)

}