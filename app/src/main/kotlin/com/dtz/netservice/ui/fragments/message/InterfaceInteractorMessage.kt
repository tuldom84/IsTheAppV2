package com.dtz.netservice.ui.fragments.message

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 20/03/18.
 */
@PerActivity
interface InterfaceInteractorMessage<V : InterfaceViewMessage> : InterfaceInteractor<V>