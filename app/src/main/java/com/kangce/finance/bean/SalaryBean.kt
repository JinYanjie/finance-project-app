package com.kangce.finance.bean

import java.math.BigDecimal
import java.util.*

data class SalaryUserBean(
        val id: Int,
        val name: String,
        val state: Int
) : BaseEntity()

data class SalaryBean(
    val addSalary: BigDecimal,
    val attandance: BigDecimal,
    val baseSalary: BigDecimal,
    val bigDisease: BigDecimal,
    val bonus: BigDecimal,
    val childEdu: BigDecimal,
    val continueEdu: BigDecimal,
    val date: Date,
    val helpOld: BigDecimal,
    val homeLoan: BigDecimal,
    val homeRent: BigDecimal,
    val insureJob: BigDecimal,
    val insureJobBase: BigDecimal,
    val insureMedicine: BigDecimal,
    val insureMedicineBase: BigDecimal,
    val insurePension: BigDecimal,
    val insurePensionBase: BigDecimal,
    val jobSalary: BigDecimal,
    val otherFee: BigDecimal,
    val performSalary: BigDecimal,
    val pTax: BigDecimal,
    val reallySalary:BigDecimal,
    val reservedFunds: BigDecimal,
    val reservedFundsBase: BigDecimal,
    val sid: Int,
    val sName: String,
    val welfare: BigDecimal,
    val state: Int
)