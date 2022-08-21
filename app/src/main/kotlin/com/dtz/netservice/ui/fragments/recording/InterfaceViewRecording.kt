package com.dtz.netservice.ui.fragments.recording

import com.dtz.netservice.ui.activities.base.InterfaceView
import com.dtz.netservice.ui.adapters.recordingadapter.RecordingRecyclerAdapter
import com.google.firebase.database.DataSnapshot

/**
 * Created by luis rafael on 17/03/19.
 */
interface InterfaceViewRecording : InterfaceView {

    fun setValueState(dataSnapshot: DataSnapshot)
    fun setValueTimerRecording(timer: Long)
    fun setRecyclerAdapter(recyclerAdapter: RecordingRecyclerAdapter)

}