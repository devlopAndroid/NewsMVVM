package com.opentechspace.newsmvvm.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.opentechspace.newsmvvm.R
import com.opentechspace.newsmvvm.ViewModel.NewsViewModel
import com.opentechspace.newsmvvm.ui.MainActivity


class DetailsNews : Fragment() {


    lateinit var viewModel : NewsViewModel
    private lateinit var webView : WebView
    private lateinit var floatingActionButton: FloatingActionButton
    private val args : DetailsNewsArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.newWebView)
        viewModel = (activity as MainActivity).viewModel
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        floatingActionButton.setOnClickListener {
            viewModel.insertDb(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }

    }
}