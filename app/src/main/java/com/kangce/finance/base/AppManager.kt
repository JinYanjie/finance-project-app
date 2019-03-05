package com.kangce.finance.base

import android.app.Activity
import android.content.Context
import com.kangce.finance.utils.L.init
import java.util.ArrayList

class AppManager private constructor()  {

    private var activityList: MutableList<Activity>? = null

    companion object {
        val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager()
        }
    }

    init {
        activityList = ArrayList()
    }


    fun getActivityList(): List<Activity>? {
        return activityList
    }


    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityList!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (activityList!!.size <= 0) null else activityList!![activityList!!.size - 1]
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityList!![activityList!!.size - 1]
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity) {
        if (activityList != null && activityList!!.size != 0) {
            activityList!!.remove(activity)

            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        if (activityList != null && activityList!!.size != 0) {
            for (activity in activityList!!) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                    break
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityList!!.size
        while (i < size) {
            if (null != activityList!![i]) {
                finishActivity(activityList!![i])
                break
            }
            i++
        }
        activityList!!.clear()
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    fun getActivity(cls: Class<*>): Activity? {
        if (activityList != null && activityList!!.size != 0)
            for (activity in activityList!!) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        return null
    }


    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        if (activityList != null) {
            for (ac in activityList!!) {
                if (!ac.isFinishing) {
                    ac.finish()
                }
            }
        }
        System.exit(0)
    }
}