package com.dtz.netservice.data.model

/**
 * Created by luis rafael on 28/03/18.
 */
class ChildPhoto {

    var capturePhoto: Boolean? = null
    var facingPhoto: Int? = null

    constructor() {}

    constructor(capturePhoto: Boolean?, facingPhoto: Int?) {
        this.capturePhoto = capturePhoto
        this.facingPhoto = facingPhoto
    }

}