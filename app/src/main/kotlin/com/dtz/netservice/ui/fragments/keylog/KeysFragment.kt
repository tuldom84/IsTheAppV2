package com.dtz.netservice.ui.fragments.keylog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.AppBarLayout
import com.github.clans.fab.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.dtz.netservice.R
import com.dtz.netservice.ui.fragments.base.BaseFragment
import com.dtz.netservice.ui.adapters.keysadapter.KeysRecyclerAdapter
import com.dtz.netservice.ui.widget.CustomRecyclerView
import com.dtz.netservice.ui.widget.OnScrollListener
import com.dtz.netservice.ui.widget.toolbar.CustomToolbar
import com.dtz.netservice.utils.ConstFun.contentGlobalLayout
import com.dtz.netservice.utils.ConstFun.isScrollToolbar
import com.google.firebase.database.*
import com.pawegio.kandroid.e
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import javax.inject.Inject

/**
 * Created by luis rafael on 17/03/18.
 */
class KeysFragment : BaseFragment(R.layout.fragment_key), InterfaceViewKeys{

    companion object { const val TAG = "KeysFragment" }

    private lateinit var viewProgress: LinearLayout
    private lateinit var viewNotHave: LinearLayout
    private lateinit var viewFailed: LinearLayout
    private lateinit var txtNotHave: TextView
    private lateinit var txtFailed: TextView
    private lateinit var list: CustomRecyclerView
    private lateinit var floatingButton: FloatingActionButton
    private lateinit var content: ConstraintLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var toolbar: CustomToolbar
    private lateinit var main: CoordinatorLayout

    private var recyclerAdapter : KeysRecyclerAdapter?=null

    @Inject lateinit var interactor: InterfaceInteractorKeys<InterfaceViewKeys>

    @Inject lateinit var layoutM : LinearLayoutManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val v = requireView()
        viewProgress = v.findViewById(R.id.progress_placeholder)
        viewNotHave = v.findViewById(R.id.not_have_placeholder)
        viewFailed = v.findViewById(R.id.failed_placeholder)
        txtNotHave = v.findViewById(R.id.txt_not_have_get)
        txtFailed = v.findViewById(R.id.txt_failed_get)
        list = v.findViewById(R.id.list)
        floatingButton = v.findViewById(R.id.floating_button)
        content = v.findViewById(R.id.content)
        appBar = v.findViewById(R.id.app_bar)
        toolbar = v.findViewById(R.id.toolbar)
        main = v.findViewById(R.id.main_view)
        setToolbar(toolbar,true,R.string.search_keys,R.id.nav_clear_keylogger)
        contentGlobalLayout(content,appBar)
        list.setAppBar(appBar)
        if (getComponent() != null){
            getComponent()!!.inject(this)
            interactor.onAttach(this)
            startData()
            timeConnection()
        }
    }

    private fun timeConnection() {
        runDelayedOnUiThread(13000) {
            if (viewProgress.isShown) failedResult(Throwable(getString(R.string.error_database_connection)))
        }
    }

    private fun startData() {
        interactor.setRecyclerAdapter()
        interactor.valueEventEnableKeys()
    }

    override fun onStart() {
        super.onStart()
        interactor.startRecyclerAdapter()
    }

    override fun setValueState(dataSnapshot: DataSnapshot) {
        toolbar.enableStatePermission = true
        try {
            if (dataSnapshot.exists()) toolbar.statePermission = dataSnapshot.value as Boolean
            else toolbar.statePermission = false
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }

    override fun successResult(result: Boolean, filter:Boolean) {
        isScrollToolbar(toolbar,result)
        if (result) {
            viewProgress.hide()
            viewNotHave.hide()
            viewFailed.hide()
            list.show()
            recyclerPosition()
        } else {
            floatingButton.hide(true)
            viewFailed.hide()
            list.hide()
            appBar.setExpanded(true)
            if (filter) { viewProgress.show() ; viewNotHave.hide() }
            else {
                viewProgress.hide()
                viewNotHave.show()
                txtNotHave.text = getString(R.string.not_have_key_text)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun failedResult(throwable: Throwable) {
        floatingButton.hide(true)
        viewProgress.hide()
        viewNotHave.hide()
        list.hide()
        isScrollToolbar(toolbar,false)
        appBar.setExpanded(true)
        viewFailed.show()
        txtFailed.text = "${getString(R.string.failed_keys_text)}, ${throwable.message}"
    }

    override fun setRecyclerAdapter(recyclerAdapter: KeysRecyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter
        layoutM.stackFromEnd = true
        layoutM.reverseLayout = true
        list.apply {
            layoutManager = layoutM
            adapter = recyclerAdapter
            recycledViewPool.clear()
            addOnScrollListener(OnScrollListener(floatingButton, layoutM))
        }
        floatingButton.setOnClickListener {
            recyclerPosition()
        }
    }

    private fun recyclerPosition(){
        appBar.setExpanded(true)
        if (recyclerAdapter!=null) list.scrollToPosition(recyclerAdapter!!.itemCount-1)
    }

    override fun onSearchConfirmed(text: CharSequence) {
        interactor.setSearchQuery(text.toString())
    }

    override fun onButtonClicked(buttonCode: Int) {
        when(buttonCode){
            CustomToolbar.BUTTON_BACK -> interactor.setSearchQuery("")
            CustomToolbar.BUTTON_STATE -> showSnackbar(if (toolbar.statePermission) R.string.enable_keylogger else R.string.disable_keylogger,main)
            CustomToolbar.BUTTON_CHILD_USER -> changeChild(TAG)
            else -> super.onButtonClicked(buttonCode)
        }
    }

    override fun onBackPressed(): Boolean {
        return when {
            toolbar.isSearchEnabled -> { toolbar.disableSearch() ; true }
            toolbar.isActionEnabled -> { toolbar.disableAction() ; true }
            else -> super.onBackPressed()
        }
    }

    override fun onChangeHeight() {
        contentGlobalLayout(content,appBar)
        recyclerPosition()
    }

    override fun onStop() {
        super.onStop()
        interactor.stopRecyclerAdapter()
    }

    override fun onDetach() {
        interactor.onDetach()
        super.onDetach()
    }

}
