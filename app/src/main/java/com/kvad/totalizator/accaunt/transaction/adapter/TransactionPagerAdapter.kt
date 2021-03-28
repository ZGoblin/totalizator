package com.kvad.totalizator.accaunt.transaction.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kvad.totalizator.accaunt.transaction.deposit.DepositPageFragment
import com.kvad.totalizator.accaunt.transaction.withdraw.WithdrawPageFragment

class TransactionPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment){
    override fun getItemCount() = TransactionPage.values().size

    override fun createFragment(position: Int): Fragment {
        return when(TransactionPage.values()[position]){
            TransactionPage.DEPOSIT_PAGE -> DepositPageFragment.newInstance()
            TransactionPage.WITHDRAW_PAGE -> WithdrawPageFragment.newInstance()
        }
    }

    @StringRes
    fun getTabTitle(position: Int): Int {
        return TransactionPage.values()[position].title
    }

}
