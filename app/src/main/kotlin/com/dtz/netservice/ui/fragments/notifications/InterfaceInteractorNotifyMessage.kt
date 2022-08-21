package com.dtz.netservice.ui.fragments.notifications

import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 17/03/19.
 */
interface InterfaceInteractorNotifyMessage<V : InterfaceViewNotifyMessage> : InterfaceInteractor<V> {
    fun valueEventEnableNotifications()
}