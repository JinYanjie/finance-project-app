package com.example.jyj.choumou

import com.kangce.finance.utils.RatePersonalUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun rateTest(){

        var personalRat = RatePersonalUtil.getPersonalRat(
                RatePersonalUtil.RateBean(12525.0, 525.0, 1500.0, 12, 5000, 0.0))
        println(personalRat)
    }
}
