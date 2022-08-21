package com.dtz.netservice.ui.activities.base

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.dtz.netservice.data.model.DataDelete
import com.dtz.netservice.rxFirebase.InterfaceFirebase
import com.google.firebase.auth.FirebaseUser

/**
 * Created by luis rafael on 9/03/18.
 */
interface InterfaceInteractor<V : InterfaceView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getView(): V?

    fun isNullView() : Boolean

    fun getContext(): Context

    fun getSupportFragmentManager(): FragmentManager

    fun firebase(): InterfaceFirebase

    fun user(): FirebaseUser?

    fun setMultiSelected(selected : Boolean)

    fun getMultiSelected() : Boolean

    fun onDeleteData(data:MutableList<DataDelete>)

    fun setRecyclerAdapter()
    fun startRecyclerAdapter()
    fun stopRecyclerAdapter()
    fun notifyDataSetChanged()
    fun notifyItemChanged(position:Int)
    fun setSearchQuery(query:String)
    fun stopRecyclerAdapterQuery()

}