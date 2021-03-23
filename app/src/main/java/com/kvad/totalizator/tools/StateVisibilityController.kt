package com.kvad.totalizator.tools

import android.view.View

class StateVisibilityController(
    private var progressBar: View?,
    private var errorView: View?
) {
    fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar?.visibility = View.GONE
    }

    fun showError() {
        errorView?.visibility = View.VISIBLE
    }

    fun hideError() {
        errorView?.visibility = View.GONE
    }

    fun hideAll() {
        hideLoading()
        hideError()
    }

    fun destroy() {
        progressBar = null
        errorView = null
    }
}
