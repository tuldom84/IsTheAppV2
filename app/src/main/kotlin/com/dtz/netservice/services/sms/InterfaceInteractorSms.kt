package com.dtz.netservice.services.sms

import com.dtz.netservice.di.PerService
import com.dtz.netservice.services.base.InterfaceInteractorService

/**
 * Created by luis rafael on 27/03/18.
 */
@PerService
interface InterfaceInteractorSms<S : InterfaceServiceSms> : InterfaceInteractorService<S> {

    fun setPushSms(smsAddress: String, smsBody: String,type: Int)

}