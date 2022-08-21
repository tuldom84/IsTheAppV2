package com.dtz.netservice.ui.fragments.recording

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 17/03/19.
 */
@PerActivity
interface InterfaceInteractorRecording <V : InterfaceViewRecording> : InterfaceInteractor<V> {
    fun stopAudioRecordHolder()
    fun getRecordAudio(time:Long)
    fun valueEventEnableRecording()
    fun valueEventTimerRecording()
}