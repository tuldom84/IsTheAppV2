package com.dtz.netservice.di.module

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dtz.netservice.di.PerActivity
import com.dtz.netservice.ui.activities.login.InteractorLogin
import com.dtz.netservice.ui.activities.login.InterfaceInteractorLogin
import com.dtz.netservice.ui.activities.login.InterfaceViewLogin
import com.dtz.netservice.ui.activities.mainparent.InteractorMainParent
import com.dtz.netservice.ui.activities.mainparent.InterfaceInteractorMainParent
import com.dtz.netservice.ui.activities.mainparent.InterfaceViewMainParent
import com.dtz.netservice.ui.activities.register.InteractorRegister
import com.dtz.netservice.ui.activities.register.InterfaceInteractorRegister
import com.dtz.netservice.ui.activities.register.InterfaceViewRegister
import com.dtz.netservice.ui.fragments.calls.InteractorCalls
import com.dtz.netservice.ui.fragments.calls.InterfaceInteractorCalls
import com.dtz.netservice.ui.fragments.calls.InterfaceViewCalls
import com.dtz.netservice.ui.fragments.photo.InteractorPhoto
import com.dtz.netservice.ui.fragments.photo.InterfaceInteractorPhoto
import com.dtz.netservice.ui.fragments.photo.InterfaceViewPhoto
import com.dtz.netservice.ui.fragments.keylog.InteractorKeys
import com.dtz.netservice.ui.fragments.keylog.InterfaceInteractorKeys
import com.dtz.netservice.ui.fragments.keylog.InterfaceViewKeys
import com.dtz.netservice.ui.fragments.maps.InteractorMaps
import com.dtz.netservice.ui.fragments.maps.InterfaceInteractorMaps
import com.dtz.netservice.ui.fragments.maps.InterfaceViewMaps
import com.dtz.netservice.ui.fragments.message.InteractorMessage
import com.dtz.netservice.ui.fragments.message.InterfaceInteractorMessage
import com.dtz.netservice.ui.fragments.message.InterfaceViewMessage
import com.dtz.netservice.ui.fragments.notifications.InteractorNotifyMessage
import com.dtz.netservice.ui.fragments.notifications.InterfaceInteractorNotifyMessage
import com.dtz.netservice.ui.fragments.notifications.InterfaceViewNotifyMessage
import com.dtz.netservice.ui.fragments.recording.InteractorRecording
import com.dtz.netservice.ui.fragments.recording.InterfaceInteractorRecording
import com.dtz.netservice.ui.fragments.recording.InterfaceViewRecording
import com.dtz.netservice.ui.fragments.social.InteractorSocial
import com.dtz.netservice.ui.fragments.social.InterfaceInteractorSocial
import com.dtz.netservice.ui.fragments.social.InterfaceViewSocial
import com.google.android.gms.maps.SupportMapFragment
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 8/03/18.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context = activity.applicationContext

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideSupportFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    fun provideSupportMapFragment(): SupportMapFragment = SupportMapFragment.newInstance()

    @Provides
    fun provideLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(context)

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMain(interactorParent: InteractorMainParent<InterfaceViewMainParent>): InterfaceInteractorMainParent<InterfaceViewMainParent> = interactorParent

    @Provides
    @PerActivity
    fun provideInterfaceInteractorLogin(interactor: InteractorLogin<InterfaceViewLogin>): InterfaceInteractorLogin<InterfaceViewLogin> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorRegister(interactor: InteractorRegister<InterfaceViewRegister>): InterfaceInteractorRegister<InterfaceViewRegister> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMaps(interactor: InteractorMaps<InterfaceViewMaps>): InterfaceInteractorMaps<InterfaceViewMaps> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorCalls(interactor: InteractorCalls<InterfaceViewCalls>): InterfaceInteractorCalls<InterfaceViewCalls> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorKeys(interactor: InteractorKeys<InterfaceViewKeys>): InterfaceInteractorKeys<InterfaceViewKeys> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorMessage(interactor: InteractorMessage<InterfaceViewMessage>): InterfaceInteractorMessage<InterfaceViewMessage> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorPhoto(interactor: InteractorPhoto<InterfaceViewPhoto>): InterfaceInteractorPhoto<InterfaceViewPhoto> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorSocial(interactor: InteractorSocial<InterfaceViewSocial>): InterfaceInteractorSocial<InterfaceViewSocial> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorRecording(interactor: InteractorRecording<InterfaceViewRecording>): InterfaceInteractorRecording<InterfaceViewRecording> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceInteractorNotify(interactor: InteractorNotifyMessage<InterfaceViewNotifyMessage>): InterfaceInteractorNotifyMessage<InterfaceViewNotifyMessage> = interactor


}