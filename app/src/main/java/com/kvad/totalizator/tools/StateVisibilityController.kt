package com.kvad.totalizator.tools

import android.view.View

class StateVisibilityController(
    private val progressBar: View,
    private val errorView: View
) {
    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    fun showError() {
        errorView.visibility = View.VISIBLE
    }

    fun hideError() {
        errorView.visibility = View.GONE
    }

    fun hideAll() {
        hideLoading()
        hideError()
    }
}
