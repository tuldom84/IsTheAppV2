package com.dtz.netservice.data.model


/**
 * Created by luis rafael on 27/03/18.
 */
class ChildRecording {

    var recordAudio: Boolean? = null
    var timeAudio: Long? = null

    constructor() {}

    constructor(recordAudio: Boolean?, timeAudio: Long?) {
        this.recordAudio = recordAudio
        this.timeAudio = timeAudio
    }

}