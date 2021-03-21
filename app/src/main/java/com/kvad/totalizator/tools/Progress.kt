package com.kvad.totalizator.tools

import android.view.View

class Progress(
    private val progressBar: View,
    private val errorView: View
) {
    init {
        errorView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

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
        progressBar.visibility = View.GONE
        errorView.visibility = View.GONE
    }
}
