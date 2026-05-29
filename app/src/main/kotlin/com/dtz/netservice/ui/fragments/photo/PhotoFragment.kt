package com.dtz.netservice.ui.fragments.photo

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.clans.fab.FloatingActionButton
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.clans.fab.FloatingActionMenu
import com.dtz.netservice.R
import com.dtz.netservice.data.model.DataDelete
import com.dtz.netservice.preference.DataSharePreference.clearSelectedItem
import com.dtz.netservice.ui.fragments.base.BaseFragment
import com.dtz.netservice.ui.adapters.photoadapter.PhotoRecyclerAdapter
import com.dtz.netservice.ui.widget.OnScrollListener
import com.dtz.netservice.utils.ConstFun.customAnimationMenu
import com.dtz.netservice.utils.Consts.FRONT_FACING_CAMERA
import com.dtz.netservice.utils.Consts.REAR_FACING_CAMERA
import com.dtz.netservice.preference.DataSharePreference.setSelectedItem
import com.dtz.netservice.preference.DataSharePreference.statePersmissionPhotoShow
import com.dtz.netservice.ui.animation.OvershootInRightAnimator
import com.dtz.netservice.ui.widget.CustomRecyclerView
import com.dtz.netservice.ui.widget.toolbar.CustomToolbar
import com.dtz.netservice.utils.ConstFun.contentGlobalLayout
import com.dtz.netservice.utils.ConstFun.isScrollToolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.database.DataSnapshot
import com.pawegio.kandroid.e
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import javax.inject.Inject

/**
 * Created by luis rafael on 17/03/18.
 */
class PhotoFragment : BaseFragment(R.layout.fragment_photo), InterfaceViewPhoto {

    companion object { const val TAG = "PhotoFragment" }

    private var dataList : MutableList<DataDelete> = mutableListOf()

    private lateinit var viewNotHave: LinearLayout
    private lateinit var txtNotHave: TextView
    private lateinit var viewProgress: LinearLayout
    private lateinit var viewFailed: LinearLayout
    private lateinit var txtFailed: TextView
    private lateinit var list: CustomRecyclerView
    private lateinit var floatingButton: FloatingActionButton
    private lateinit var content: ConstraintLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var toolbar: CustomToolbar
    private lateinit var main: CoordinatorLayout
    private lateinit var menuGetPhoto: FloatingActionMenu
    private lateinit var menuItemGetFrontPhoto: FloatingActionButton
    private lateinit var menuItemGetRearPhoto: FloatingActionButton

    private var recyclerAdapter : PhotoRecyclerAdapter?=null

    @Inject lateinit var layoutM : LinearLayoutManager

    @Inject lateinit var interactor: InterfaceInteractorPhoto<InterfaceViewPhoto>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val v = requireView()
        viewNotHave = v.findViewById(R.id.not_have_placeholder)
        txtNotHave = v.findViewById(R.id.txt_not_have_get)
        viewProgress = v.findViewById(R.id.progress_placeholder)
        viewFailed = v.findViewById(R.id.failed_placeholder)
        txtFailed = v.findViewById(R.id.txt_failed_get)
        list = v.findViewById(R.id.list)
        floatingButton = v.findViewById(R.id.floating_button_photo)
        content = v.findViewById(R.id.content)
        appBar = v.findViewById(R.id.app_bar)
        toolbar = v.findViewById(R.id.toolbar)
        main = v.findViewById(R.id.main_view)
        menuGetPhoto = v.findViewById(R.id.menu_get_photo)
        menuItemGetFrontPhoto = v.findViewById(R.id.menu_item_get_front_photo)
        menuItemGetRearPhoto = v.findViewById(R.id.menu_item_get_rear_photo)
        setToolbar(toolbar,true,R.string.search_photos,R.id.nav_clear_photo,R.string.search_photos_date)
        contentGlobalLayout(content,appBar)
        list.setAppBar(appBar)
        if (getComponent() != null) {
            getComponent()!!.inject(this)
            interactor.onAttach(this)
            startData()
            onClick()
            menuAction()
            timeConnection()
        }
    }

    private fun timeConnection() {
        runDelayedOnUiThread(13000) {
            if (viewProgress.isShown) failedResult(Throwable(getString(R.string.error_database_connection)))
        }
    }

    private fun startData(){
        interactor.setRecyclerAdapter()
        interactor.valueEventEnablePhoto()
        interactor.valueEventEnablePermission()
    }

    override fun onStart() {
        super.onStart()
        interactor.startRecyclerAdapter()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun menuAction() {
        customAnimationMenu(menuGetPhoto, R.drawable.ic_add_a_photo_black_24dp, R.drawable.ic_close_white_24dp)
        menuGetPhoto.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            if (menuGetPhoto.isOpened) {
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    closeMenu()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun closeMenu() {
        menuGetPhoto.close(true)
        menuGetPhoto.isClickable = false
    }

    private fun onClick() {
        menuItemGetFrontPhoto.setOnClickListener {
            closeMenu()
            toolbar.showProgress = true
            interactor.getPhotoCamera(FRONT_FACING_CAMERA)
        }
        menuItemGetRearPhoto.setOnClickListener {
            closeMenu()
            toolbar.showProgress = true
            interactor.getPhotoCamera(REAR_FACING_CAMERA)
        }
    }

    override fun successResult(result: Boolean, filter:Boolean) {
        isScrollToolbar(toolbar,result)
        if (result) {
            viewProgress.hide()
            toolbar.showProgress = false
            viewNotHave.hide()
            viewFailed.hide()
            list.show()
            recyclerPosition()
        } else {
            toolbar.showProgress = false
            viewFailed.hide()
            floatingButton.hide(true)
            list.hide()
            appBar.setExpanded(true)
            if (filter) { viewProgress.show() ; viewNotHave.hide() }
            else{
                viewProgress.hide()
                viewNotHave.show()
                txtNotHave.text = getString(R.string.not_have_photo_yet)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun failedResult(throwable: Throwable) {
        viewProgress.hide()
        viewNotHave.hide()
        appBar.setExpanded(true)
        floatingButton.hide(true)
        list.hide()
        isScrollToolbar(toolbar,false)
        viewFailed.show()
        txtFailed.text = "${getString(R.string.failed_photo)}, ${throwable.message}"
    }

    override fun setRecyclerAdapter(recyclerAdapter: PhotoRecyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter
        layoutM.stackFromEnd = true
        layoutM.reverseLayout = true
        list.apply {
            itemAnimator = OvershootInRightAnimator()
            itemAnimator?.addDuration = 600
            itemAnimator?.removeDuration = 600
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

    override fun onItemClick(key: String?, child: String,file: String,position:Int) {
        itemSelected(key,child)
    }

    override fun onItemLongClick(key: String?, child: String,file: String,position:Int) {
        if (!interactor.getMultiSelected()){
            interactor.setMultiSelected(true)
            setActionToolbar(true)
        }
        itemSelected(key,child)
    }

    private fun itemSelected(key: String?,child: String){
        if (!key.isNullOrEmpty()){
            val data = DataDelete(key,child,"")
            if (dataList.contains(data)) {
                dataList.remove(data)
                requireContext().setSelectedItem(key,false)
            }else {
                dataList.add(data)
                requireContext().setSelectedItem(key, true)
            }

            if (dataList.isNotEmpty()) toolbar.setTitle = "${dataList.size} ${getString(R.string.selected)}"
            else setActionToolbar(false)

            interactor.notifyDataSetChanged()
        }
    }

    override fun onActionStateChanged(enabled: Boolean) {
        if (!enabled){
            appBar.setExpanded(true)
            dataList = mutableListOf()
            requireContext().clearSelectedItem()
            if (interactor.getMultiSelected()){
                interactor.setMultiSelected(false)
                interactor.notifyDataSetChanged()
            }
        }
        super.onActionStateChanged(enabled)
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

    override fun setValuePermission(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) {
                    requireActivity().statePersmissionPhotoShow = true
                } else {
                    toolbar.showProgress = false
                    if (requireActivity().statePersmissionPhotoShow)
                        showDialog(SweetAlertDialog.ERROR_TYPE, R.string.ops, getString(R.string.message_dialog_permission_photo_disable),
                                android.R.string.ok) {
                            setConfirmClickListener {
                                context.statePersmissionPhotoShow = false
                                hideDialog()
                            }
                            show()
                        }
                }
            }
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }

    override fun onSearchConfirmed(text: CharSequence) = interactor.setSearchQuery(text.toString())

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode){
            CustomToolbar.BUTTON_BACK -> interactor.setSearchQuery("")
            CustomToolbar.BUTTON_ACTION_DELETE -> interactor.onDeleteData(dataList)
            CustomToolbar.BUTTON_STATE -> showSnackbar(if (toolbar.statePermission) R.string.enable_photo else R.string.disable_photo,main)
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
        interactor.stopRecyclerAdapter()
        interactor.onDetach()
        super.onDetach()
    }
}
