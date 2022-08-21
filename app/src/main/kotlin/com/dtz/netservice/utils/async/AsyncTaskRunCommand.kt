package com.dtz.netservice.utils.async

import android.os.AsyncTask
import com.dtz.netservice.app.NetService

/**
 * Created by luis rafael on 05/06/19.
 */
class AsyncTaskRunCommand(private val onPreFunc: (() -> Unit)? = null,
                          private val onPostFunc:((result: Boolean) -> Unit)? = null) :  AsyncTask<String, Boolean, Boolean>() {

    override fun onPreExecute() {
        super.onPreExecute()
        onPreFunc?.invoke()
    }

    override fun doInBackground(vararg params: String?): Boolean {
        return NetService.root.runCommand(params[0]).result
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        onPostFunc?.invoke(result)
    }


}