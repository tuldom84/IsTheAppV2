package com.dtz.netservice.ui.fragments.setting

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dtz.netservice.R
import com.dtz.netservice.preference.DataSharePreference.finishAppState
import com.dtz.netservice.preference.DataSharePreference.lockPin
import com.dtz.netservice.preference.DataSharePreference.lockState
import com.dtz.netservice.preference.DataSharePreference.timeFinishApp
import com.dtz.netservice.ui.fragments.base.BaseFragment
import com.dtz.netservice.ui.widget.CustomCheckBox
import com.dtz.netservice.ui.widget.CustomEditText
import com.dtz.netservice.ui.widget.CustomNestedScrollView
import com.dtz.netservice.ui.widget.toolbar.CustomToolbar
import com.dtz.netservice.utils.ConstFun.find
import com.dtz.netservice.utils.ConstFun.isShow
import com.google.android.material.appbar.AppBarLayout
import com.pawegio.kandroid.inflateLayout

/**
 * Created by luis rafael on 20/03/18.
 */
class SettingFragment : BaseFragment(R.layout.fragment_setting){

    companion object { const val TAG = "SettingFragment" }

    private fun getLockState() : Boolean = requireContext()!!.lockState
    private fun setLockState(state:Boolean) {requireContext()!!.lockState = state}
    private fun getLockPin() : String = requireContext().lockPin
    private fun setLockPin(pin:String) {requireContext()!!.lockPin = pin}
    private fun getFinishAppState() : Boolean = requireContext()!!.finishAppState
    private fun setFinishAppState(state:Boolean) {requireContext()!!.finishAppState = state}
    private fun getTimeFinishApp() : Int = requireContext()!!.timeFinishApp
    private fun setTimeFinishApp(time:Int) {requireContext()!!.timeFinishApp = time}

    private lateinit var enableCodeAccess : LinearLayout
    private lateinit var checkEnableCodeAccess : CustomCheckBox
    private lateinit var changeCodeAccess : LinearLayout
    private lateinit var enableAppFinish : LinearLayout
    private lateinit var checkEnableAppFinish : CustomCheckBox
    private lateinit var changeTimeAppFinish : LinearLayout
    private lateinit var txtTimeAppFinish: TextView
    private lateinit var toolbar : CustomToolbar
    private lateinit var appBar : AppBarLayout
    private lateinit var nested : CustomNestedScrollView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val v = requireView()
        enableCodeAccess = v.findViewById(R.id.btn_enable_code_access)
        checkEnableCodeAccess = v.findViewById(R.id.check_enable_code_access)
        changeCodeAccess = v.findViewById(R.id.btn_change_code_access)
        enableAppFinish = v.findViewById(R.id.btn_enable_app_finih)
        checkEnableAppFinish = v.findViewById(R.id.check_enable_app_finish)
        changeTimeAppFinish = v.findViewById(R.id.btn_change_time_app_finish)
        txtTimeAppFinish = v.findViewById(R.id.txt_time_finish_app)
        toolbar = v.findViewById(R.id.toolbar)
        appBar = v.findViewById(R.id.app_bar)
        nested = v.findViewById(R.id.nested_scroll)
        init()
    }

    private fun init(){

        setToolbar(toolbar,false,R.string.setting,0)
        nested.setAppBar(appBar)

        initValueTime()

        if (getLockState()) trueLockState() else falseLockState()
        if (getFinishAppState()) trueAppFinishState() else falseAppFinishState()

        enableCodeAccess.setOnClickListener {
            if (getLockState()) {
               falseLockState()
            } else{
                trueLockState()
            }
        }

        enableAppFinish.setOnClickListener {
            if (getFinishAppState()){
                falseAppFinishState()
            }else{
                trueAppFinishState()
            }
        }

        changeCodeAccess.setOnClickListener { showDialogCodeAccess() }
        changeTimeAppFinish.setOnClickListener { showDialogFinisApp() }
    }

    private fun falseLockState(){
        checkEnableCodeAccess.isChecked = false
        setLockState(false)
        changeCodeAccess.isEnabled = false
        changeCodeAccess.alpha = 0.3f
        //setLockPin("")
    }

    private fun trueLockState(){
        checkEnableCodeAccess.isChecked = true
        setLockState(true)
        changeCodeAccess.isEnabled = true
        changeCodeAccess.alpha = 1f
        if (getLockPin()=="") showDialogCodeAccess()
    }

    private fun falseAppFinishState(){
        checkEnableAppFinish.isChecked = false
        setFinishAppState(false)
        changeTimeAppFinish.isEnabled = false
        changeTimeAppFinish.alpha = 0.3f
    }

    private fun trueAppFinishState(){
        checkEnableAppFinish.isChecked = true
        setFinishAppState(true)
        changeTimeAppFinish.isEnabled = true
        changeTimeAppFinish.alpha = 1f
    }

    private fun initValueTime(){
        when(getTimeFinishApp()) {
            1000 -> txtTimeAppFinish.text = getString(R.string.one_minute)
            2000 -> txtTimeAppFinish.text = getString(R.string.two_minute)
            5000 -> txtTimeAppFinish.text = getString(R.string.five_minute)
        }
    }

    private fun showDialogCodeAccess(){
        val view = requireContext()!!.inflateLayout(R.layout.view_change_code_access)
        val edtCurrent = view.find<CustomEditText>(R.id.current_access_code)
        val edtNew = view.find<CustomEditText>(R.id.new_access_code)
        val edtRepeat = view.find<CustomEditText>(R.id.repeat_access_code)

        edtCurrent.isShow(getLockPin()!="")

        SweetAlertDialog(requireContext()!!,SweetAlertDialog.NORMAL_TYPE).apply {
            setCustomView(view)
            titleText = getString(R.string.change_code_access)
            confirmText = getString(R.string.change)
            cancelText = getString(android.R.string.cancel)
            showCancelButton(true)
            setCancelable(false)
            setConfirmClickListener {

                if (getLockPin()!="")
                if (edtCurrent.text != getLockPin()){
                    edtCurrent.errorFocus = R.string.error_current_code_access
                    return@setConfirmClickListener
                }
                if (!edtNew.isCharactersValid())edtNew.errorFocus = R.string.error_code_access
                else if (!edtRepeat.isCharactersValid()) edtRepeat.errorFocus = R.string.error_code_access
                else if (edtNew.text != edtRepeat.text){
                    showMessage(R.string.error_not_match_code_access)
                    edtNew.errorNotFocus(true)
                    edtRepeat.errorNotFocus(true)
                }else {
                    setLockPin(edtNew.text)
                    dismissWithAnimation()
                }

            }
            setCancelClickListener {
                dismissWithAnimation()
                if (getLockPin()=="") falseLockState()
            }
            show()
        }
    }

    private fun showDialogFinisApp(){
        val view = requireContext()!!.inflateLayout(R.layout.view_change_time_finish_app)
        val clickOneMinute = view.find<LinearLayout>(R.id.click_one_minute_finish_app)
        val clickTwoMinute = view.find<LinearLayout>(R.id.click_two_minute_finish_app)
        val clickFiveMinute = view.find<LinearLayout>(R.id.click_five_minute_finish_app)
        val checkOneMinute = view.find<CustomCheckBox>(R.id.check_one_minute_finish_app)
        val checkTwoMinute = view.find<CustomCheckBox>(R.id.check_two_minute_finish_app)
        val checkFiveMinute = view.find<CustomCheckBox>(R.id.check_five_minute_finish_app)

        val dialog = SweetAlertDialog(context,SweetAlertDialog.NORMAL_TYPE).apply {
            hideConfirmButton()
            setCustomView(view)
            titleText = getString(R.string.change_app_finish_time)
            show()
        }

        when(getTimeFinishApp()) {
            1000 -> checkOneMinute.isChecked = true
            2000 -> checkTwoMinute.isChecked = true
            5000 -> checkFiveMinute.isChecked = true
        }

        clickOneMinute.setOnClickListener { setTimeFinishApp(1000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
        clickTwoMinute.setOnClickListener { setTimeFinishApp(2000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
        clickFiveMinute.setOnClickListener { setTimeFinishApp(5000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode){
            CustomToolbar.BUTTON_CHILD_USER -> changeChild(TAG)
            else -> super.onButtonClicked(buttonCode)
        }
    }

}
