package fr.mi.wewok.ui.details_wok.step1

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

@SuppressLint("WrongConstant")
class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragments = ArrayList<Fragment>()
    val titles = ArrayList<String>()

    fun addFragment(fragment:Fragment,title:String)
    {
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getCount(): Int  = fragments.size

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}