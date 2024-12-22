package com.zrq.test.point.list

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zrq.test.point.R
import com.zrq.test.point.details.TestFragmentDetailsActivity.Companion.newBundle
import com.zrq.test.point.details.TestFragmentDetailsActivity.Companion.newIntent
import com.zrq.test.point.entity.TestEntryPointInfo
import com.zrq.test.point.init.TestEntryPointInit
import com.zrq.test.point.list.TestListFragment.Companion.newBundle
import com.zrq.test.point.listener.OnItemClickListener
import com.zrq.test.point.utils.GeneratedClassesUtils

/**
 * 描述：测试列表Activity
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
class TestListActivity : AppCompatActivity() {
    var adapter: TestListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_test_list)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_test_list_content)
        // 初始化内容RecyclerView
        initContentRecyclerView(recyclerView) // 重建后需要重新初始化，因为adapter等已丢失
        if (savedInstanceState == null) {
            // 初始化 CustomTestListFragment
            initCustomTestListFragment() // 重建后不需要重新初始化，因为Fragment已经增加
        }
    }

    // 初始化 CustomTestListFragment
    private fun initCustomTestListFragment() {
        // 设置所有CustomTestListFragment显示
        val customTestListFragmentInfoList = TestEntryPointInit.allCustomTestListFragmentListData
        if (customTestListFragmentInfoList.isNotEmpty()) {
            // 有CustomTestListFragment，add到View
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            for ((moduleName, fragmentClassName) in customTestListFragmentInfoList) {
                try {
                    // 有约束，customClass 是TestListFragment的子类，所以用SupportFragmentManager进行add
                    val fragment = Class.forName(fragmentClassName).newInstance() as Fragment
                    fragment.arguments =
                        newBundle(moduleName)
                    fragmentTransaction.add(
                        R.id.fl_test_list_custom_layout, fragment,
                        fragmentClassName
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    // 初始化内容RecyclerView
    private fun initContentRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(
            applicationContext, 3
        )
        adapter = TestListAdapter(TestEntryPointInit.allModuleTestListData)
        recyclerView.adapter = adapter
        // 设置注解信息item点击监听
        adapter!!.setOnAnnotationsItemClickListener(object :
            OnItemClickListener<TestEntryPointInfo> {
            override fun onItemClick(view: View, item: TestEntryPointInfo) {
                // type：1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法、5：非静态无参方法（TestListFragment子类）
                if (item.type == 1) {
                    // Activity
                    try {
                        startActivity(Intent(applicationContext, Class.forName(item.className)))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "跳转异常", Toast.LENGTH_SHORT).show()
                    }
                } else if (item.type == 2 || item.type == 3) {
                    // Fragment、Support Fragment
                    val customDetailsClassName =
                        GeneratedClassesUtils.getCustomTestFragmentDetailsActivityClassName()
                    if (TextUtils.isEmpty(customDetailsClassName)) {
                        // 无自定义，使用TestFragmentDetailsActivity
                        startActivity(newIntent(applicationContext, item.className, null))
                    } else {
                        // 有自定义，使用自定义
                        try {
                            val customDetailsClass = Class.forName(customDetailsClassName)
                            val intent = Intent(applicationContext, customDetailsClass)
                            intent.putExtras(newBundle(item.className, null))
                            startActivity(intent)
                        } catch (e: ClassNotFoundException) {
                            e.printStackTrace()
                            Toast.makeText(applicationContext, "跳转异常", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else if (item.type == 4 || item.type == 5) {
                    // 静态无参方法、非静态无参方法（TestListFragment子类）
                    try {
                        val clazz = Class.forName(item.className)
                        val method = clazz.getDeclaredMethod(item.methodName)
                        method.isAccessible = true
                        method.invoke(
                            if (item.type == 4) clazz else supportFragmentManager.findFragmentByTag(
                                item.className
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "调用方法异常", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
}
