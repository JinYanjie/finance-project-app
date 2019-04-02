package com.kangce.finance.http.service

object ApiCodeConfig {
    // 请求成功
    val SUCCESS = 1000
    /**
     * 答题错误
     */
    val ANSWER_ERROR = 1019
    // token失效，重新登录
    val TOKEN_EXPIRED = 1002
    // 同时登陆，重新登录
    val TOKEN_RELOGIN = 1010

    fun getApiErr(code: Int): String {
        var errStr = "网络不给力，请稍后再试。"
        when (code) {
            1000 -> errStr = "请求成功"
            1001 -> errStr = "返回结果不正确"
            1002 -> errStr = "令牌有误"
            1003 -> errStr = "参数有误"
            1004 -> errStr = "发送失败"
            1005 -> errStr = "验证码有误"
            1006 -> errStr = "注册失败"
            1007 -> errStr = "登录失败"
            1008 -> errStr = "认证失败"
            1009 -> errStr = "信息不存在"
            10010 -> errStr = "用户不存在"
            1011 -> errStr = "上传失败"
            1012 -> errStr = "创建信息失败"
            1013 -> errStr = "获取信息失败"
            1014 -> errStr = "修改失败"
            1015 -> errStr = "删除失败"
            1016 -> errStr = "数据库操作有误"
            1017 -> errStr = "请求失败，访问路径有误"
            1018 -> errStr = "用户已授权"
            1019 -> errStr = "答题失败"
            1020 -> errStr = "答题超时"
        }
        return errStr
    }
}