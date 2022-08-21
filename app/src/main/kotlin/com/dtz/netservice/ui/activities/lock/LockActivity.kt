package com.dtz.netservice.ui.activities.lock
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.dtz.netservice.R
import com.dtz.netservice.ui.activities.base.BaseActivity
import com.dtz.netservice.preference.DataSharePreference.lockPin
import com.dtz.netservice.ui.widget.pinlockview.CustomPinLockView
import com.dtz.netservice.ui.widget.pinlockview.IndicatorDots
import com.dtz.netservice.ui.widget.pinlockview.PinLockListener
import com.dtz.netservice.utils.ConstFun.setVibrate
import com.dtz.netservice.utils.ConstFun.viewAnimation
import kotterknife.bindView

/**
 * Created by luis rafael on 28/03/18.
 */
class LockActivity : BaseActivity(R.layout.activity_lock), PinLockListener {

    private val indicators: IndicatorDots by bindView(R.id.indicator_dots)
    private val lockView: CustomPinLockView by bindView(R.id.pin_lock_view)
    private val txtMsg: TextView by bindView(R.id.txt_msg_lock)

    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        startLock()
        initializeVibrator()
    }

    private fun initializeVibrator() {
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun startLock() {
        lockView.attachIndicatorDots(indicators)
        lockView.setPinLockListener(this)
    }

    override fun onComplete(pin: String) {
        if (lockPin == pin) finish()
        else {
            setVibrate(150)
            txtMsg.viewAnimation(AnimationUtils.makeInAnimation(this,true),44)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) return false
        return super.onKeyDown(keyCode, event)
    }

}
