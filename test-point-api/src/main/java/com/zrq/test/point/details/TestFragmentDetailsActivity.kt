package com.zrq.test.point.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zrq.test.point.R

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
open class TestFragmentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_test_fragment_details)
        if (savedInstanceState != null) return  // 配置变更，则不重新进行替换布局，Fragment会自恢复。

        val fragmentClassName = intent?.getStringExtra(KEY_FRAGMENT_CLASS_NAME) ?: return
        val fragmentBundle = intent.getBundleExtra(KEY_FRAGMENT_BUNDLE)
        try {
            val instance = Class.forName(fragmentClassName).newInstance()
            if (instance is Fragment) {
                // Support Fragment
                instance.arguments = fragmentBundle
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_test_details, instance)
                    .commitAllowingStateLoss()
            } else {
                // Fragment
                (instance as android.app.Fragment).arguments = fragmentBundle
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_test_details, instance)
                    .commitAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val KEY_FRAGMENT_CLASS_NAME = "fragment_Class_Name"
        private const val KEY_FRAGMENT_BUNDLE = "key_fragment_bundle"

        /**
         * 创建跳到【TestFragmentDetailsActivity】需要的Intent
         */
        @JvmStatic
        fun newIntent(
            context: Context,
            fragmentClassName: String?,
            fragmentBundle: Bundle?
        ): Intent {
            val intent = Intent(context, TestFragmentDetailsActivity::class.java)
            intent.putExtras(newBundle(fragmentClassName, fragmentBundle))
            return intent
        }

        /**
         * 创建跳到【TestFragmentDetailsActivity】需要的Bundle
         */
        @JvmStatic
        fun newBundle(fragmentClassName: String?, fragmentBundle: Bundle?): Bundle {
            val bundle = Bundle()
            bundle.putString(KEY_FRAGMENT_CLASS_NAME, fragmentClassName)
            bundle.putBundle(KEY_FRAGMENT_BUNDLE, fragmentBundle)
            return bundle
        }
    }
}
