package com.kangce.finance.ui.main.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.bean.ManagerItemBean
import com.kangce.finance.R
import com.kangce.finance.ui.manager.department.DepartmentActivity
import com.kangce.finance.ui.manager.salary.SalaryActivity
import com.kangce.finance.ui.manager.staff.StaffActivity
import com.kangce.finance.ui.manager.userlevel.UserLevelActivity

class ManageFragment : BaseFragment() {
    var rv: RecyclerView? = null
    var adapter: ManagerItemAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_manage_layout
    }

    override fun initView(view: View) {
        super.initView(view)
        rv = view.findViewById(R.id.rv)
        rv?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = ManagerItemAdapter()
        rv?.adapter = adapter
        adapter?.setNewData(getData())
        adapter?.setOnItemClickListener { adapter, view, position ->
            var item = adapter.data[position] as ManagerItemBean
            when (item.name) {
                "用户权限" -> {
                    UserLevelActivity.start(context)
                }
                "部门管理" -> {
                    DepartmentActivity.start(activity as Context)
                }
                "职员管理" -> {
                    StaffActivity.start(activity as Context)
                }
                "税率管理" -> {
                }
                "薪资管理" -> {
                    SalaryActivity.start(activity as Context)
                }
            }
        }
    }

    private fun getData(): MutableList<ManagerItemBean>? {
        val list = mutableListOf<ManagerItemBean>()
        list.add(ManagerItemBean(R.drawable.manage_ico_permissions, "用户权限"))
        list.add(ManagerItemBean(R.drawable.manage_ico_departments, "部门管理"))
        list.add(ManagerItemBean(R.drawable.home_icon_transf, "薪资管理"))
        list.add(ManagerItemBean(R.drawable.manage_ico_staff, "职员管理"))
        list.add(ManagerItemBean(R.drawable.manage_ico_rate, "税率管理"))
        return list
    }

}