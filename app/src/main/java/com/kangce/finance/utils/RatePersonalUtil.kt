package com.kangce.finance.utils

class RatePersonalUtil {

    companion object {
        /**
         * 计算个人所得税
         *
         *  累计预扣预缴应纳税所得额=累计收入-累计免税收入-累计减除费用-累计专项扣除-累计专项附加扣除-累计依法确定的其他扣除
         *  本期应预扣预缴税额=（累计预扣预缴应纳税所得额×预扣率-速算扣除数)-累计减免税额-累计已预扣预缴税额
         *
         *
         *  累计预扣预缴应纳税所得额         预扣率（%）          速算扣除数
         *  不超过36,000元的部分               3                   0
         *  超过36,000元至144,000元的部分       10                  2520
         *  超过144,000元至300,000元的部分      20                  16920
         *  超过300,000元至420,000元的部分      25                  31920
         *  超过420,000元至660,000元的部分      30                  52920
         *  超过660,000元至960,000元的部分      35                  85920
         *  超过960,000元的部分                45                  181920
         *
         */

        fun getPersonalRat(rateBean: RateBean): RateBean {

            //每月 预扣 预缴应纳税所得额
            val everyMonth = rateBean.salary - rateBean.baseNum - rateBean.insure - rateBean.specialAdditional - rateBean.other

            //本月  累计  预扣预缴应纳税所得额
            val targetAll = everyMonth * rateBean.term

            //上月  累计  预扣预缴应纳税所得额
            val lastMothAll = everyMonth * (rateBean.term - 1)

            //应缴税额 = 截止本期应缴税额 -  截止上期应缴税额
            var ratePersonal = getDeserveRateAll(targetAll) - getDeserveRateAll(lastMothAll)

            //实发工资=工资 - 社保 - 个人所得税
            var reallySalary = rateBean.salary - ratePersonal - rateBean.insure

            rateBean.personalRate = ratePersonal
            rateBean.reallySalary = reallySalary

            return rateBean
        }

        /**
         * 获取 累计应缴税款
         */
        private fun getDeserveRateAll(targetAll: Double): Double {
            var deserveRateAll: Double
            when {
                targetAll <= 36000 -> {
                    deserveRateAll = targetAll * 0.03
                }
                targetAll <= 144000 -> {
                    deserveRateAll = targetAll * 0.1 - 2520
                }
                targetAll <= 300000 -> {
                    deserveRateAll = targetAll * 0.2 - 16920
                }
                targetAll <= 420000 -> {
                    deserveRateAll = targetAll * 0.25 - 31920
                }
                targetAll <= 660000 -> {
                    deserveRateAll = targetAll * 0.30 - 52920
                }
                targetAll <= 960000 -> {
                    deserveRateAll = targetAll * 0.35 - 85920
                }
                else -> {
                    deserveRateAll = targetAll * 0.45 - 181920
                }
            }
            return deserveRateAll
        }
    }

    /**
     * 计算个人所得税 所需信息
     *
     * 本月工资（）
     * 各项社会保险费（）
     * 专项附加扣除（）
     *  纳税期数（）
     *  免税基数（）
     *  其他扣除（）
     *
     *
     */
    class RateBean(val salary: Double,
                   val insure: Double,
                   val specialAdditional: Double,
                   val term: Int,
                   val baseNum: Double,
                   val other: Double,
                   var personalRate: Double,
                   var reallySalary: Double)


}