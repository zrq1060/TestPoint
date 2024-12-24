package com.zrq.test.point.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val moduleName by lazy { arguments?.getString(KEY_MODULE_NAME)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // 此返回值，才会调用onViewCreated。
        return View(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAddTestItems()
    }

    /**
     * 增加测试Item，内部调用【addItem】
     */
    open fun onAddTestItems() {
    }

    /**
     * 增加Item
     *
     * @param title         名称
     * @param clickListener 点击事件
     */
    fun addItem(title: String?, clickListener: View.OnClickListener?) {
        (activity as TestListActivity).adapter?.addItem(moduleName, title, clickListener)
    }

    fun addItem(title: String, clazz: Class<*>, vararg args: Pair<String, Any>) {
        val bundle = if (args.isNotEmpty()) bundleOf(*args) else null
        addItem(title) {
            if (Activity::class.java.isAssignableFrom(clazz)) {
                // Activity
                try {
                    startActivity(Intent(requireContext(), clazz).apply {
                        if (bundle != null) {
                            putExtras(bundle)
                        }
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "跳转异常", Toast.LENGTH_SHORT).show()
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
                            requireContext(),
                            clazz.canonicalName,
                            bundle
                        )
                    )
                } else {
                    // 有自定义，使用自定义
                    try {
                        val customDetailsClass = Class.forName(customDetailsClassName!!)
                        val intent = Intent(requireContext(), customDetailsClass)
                        intent.putExtras(
                            TestFragmentDetailsActivity.newBundle(
                                clazz.canonicalName,
                                bundle
                            )
                        )
                        startActivity(intent)
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "跳转异常", Toast.LENGTH_SHORT).show()
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
