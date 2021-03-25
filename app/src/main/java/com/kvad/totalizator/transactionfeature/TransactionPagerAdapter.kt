package com.kvad.totalizator.transactionfeature

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TransactionPagerAdapter(
    fragment: Fragment,
    private val pages: TransactionPage
) : FragmentStateAdapter(fragment){
    override fun getItemCount() = TransactionPage.values().size

    override fun createFragment(position: Int): Fragment {
        return when(pages){
            TransactionPage.DEPOSIT_PAGE -> DepositPageFragment.newInstance()
            TransactionPage.WITHDRAW_PAGE -> WithdrawPageFragment.newInstance()
        }
    }


}