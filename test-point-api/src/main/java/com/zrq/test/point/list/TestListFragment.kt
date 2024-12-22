package com.zrq.test.point.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.zrq.test.point.details.TestFragmentDetailsActivity
import com.zrq.test.point.utils.GeneratedClassesUtils

/**
 * 描述：测试列表Fragment
 *
 * @author zhangrq
 * createTime 2020/12/22 17:23
 */
open class TestListFragment : Fragment() {
    private var moduleName: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        moduleName = arguments?.getString(KEY_MODULE_NAME)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onAddTestViews()
    }

    /**
     * 增加测试Item，内部调用【addItem】
     */
    open fun onAddTestViews() {
    }

    /**
     * 增加Item
     *
     * @param title         名称
     * @param clickListener 点击事件
     */
    fun addItem(title: String?, clickListener: View.OnClickListener?) {
        (activity as TestListActivity).adapter.addItem(moduleName, title, clickListener)
    }

    fun addItem(title: String, clazz: Class<*>, vararg args: Pair<String, Any>) {
        val bundle = bundleOf(*args)
        addItem(title) {
            if (Activity::class.java.isAssignableFrom(clazz)) {
                // Activity
                try {
                    startActivity(Intent(context, clazz).putExtras(bundle))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "跳转异常", Toast.LENGTH_SHORT).show()
                }
            } else if (android.app.Fragment::class.java.isAssignableFrom(clazz)
                || androidx.fragment.app.Fragment::class.java.isAssignableFrom(clazz)
            ) {
                // Fragment、Support Fragment
                val customDetailsClassName =
                    GeneratedClassesUtils.getCustomTestFragmentDetailsActivityClassName()
                if (TextUtils.isEmpty(customDetailsClassName)) {
                    // 无自定义，使用TestFragmentDetailsActivity
                    startActivity(
                        TestFragmentDetailsActivity.newIntent(
                            context,
                            clazz.canonicalName,
                            bundle
                        )
                    )
                } else {
                    // 有自定义，使用自定义
                    try {
                        val customDetailsClass = Class.forName(customDetailsClassName)
                        val intent = Intent(context, customDetailsClass)
                        intent.putExtras(
                            TestFragmentDetailsActivity.newBundle(
                                clazz.canonicalName,
                                bundle
                            )
                        )
                        startActivity(intent)
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                        Toast.makeText(context, "跳转异常", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    companion object {
        private const val KEY_MODULE_NAME = "module_name"

        /**
         * 创建此类所需的Bundle
         *
         * @param moduleName 模块名称
         */
        @JvmStatic
        fun newBundle(moduleName: String?): Bundle {
            val bundle = Bundle()
            bundle.putString(KEY_MODULE_NAME, moduleName)
            return bundle
        }
    }
}
