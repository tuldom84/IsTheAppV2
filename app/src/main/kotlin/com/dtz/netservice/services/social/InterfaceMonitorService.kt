package com.dtz.netservice.services.social

/**
 * Created by luis rafael on 27/03/18.
 */
interface InterfaceMonitorService {

    fun gerSocialStatus()
    fun setPermission(status:Boolean)

}