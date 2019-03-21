package com.kangce.finance.utils

import android.app.Application
import com.kangce.finance.Constant
import com.kangce.finance.bean.LoginSuccess
import com.orhanobut.hawk.Hawk
import java.util.HashMap


/**
 * 登录用户的缓存信息
 */
class UserCacheHelper(private val application: Application) {
    private var entity: LoginSuccess? = null



    companion object {
        var instance: UserCacheHelper? = null
            private set

        fun init(application: Application) {
            if (instance == null) {
                instance = UserCacheHelper(application)
            } else {
                instance!!.entity = Hawk.get(Constant.SP_KEY_USERDETAIL)
            }
        }

        fun update(entity: LoginSuccess?): Boolean {
            if (entity == null)
                return false
            instance!!.entity = entity
            return Hawk.put(Constant.SP_KEY_USERDETAIL, entity)
        }

        fun getEntity(): LoginSuccess? {
            if (instance == null) {
                return null
            }
            if (instance!!.entity == null) {
                instance!!.entity = Hawk.get(Constant.SP_KEY_USERDETAIL)
            }
            return instance!!.entity
        }

        val token: String
            get() {
                var token = ""
                if (getEntity() != null) {
                    token = getEntity()!!.token
                }
                return token
            }

        fun clear() {
            instance!!.entity = null
            Hawk.delete(Constant.SP_KEY_USERDETAIL)
        }
    }
}
