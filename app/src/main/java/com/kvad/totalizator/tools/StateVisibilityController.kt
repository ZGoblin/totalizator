package com.kvad.totalizator.tools

import android.view.View

class StateVisibilityController(
    private var progressBar: View?,
    private var errorView: View?
) {
    fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    fun showError() {
        errorView?.visibility = View.VISIBLE
    }

    fun hideAll() {
        progressBar?.visibility = View.GONE
        errorView?.visibility = View.GONE
    }

    fun destroy() {
        progressBar = null
        errorView = null
    }
}
