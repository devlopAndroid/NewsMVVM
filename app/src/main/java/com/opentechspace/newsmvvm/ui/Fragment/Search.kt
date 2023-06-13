package com.opentechspace.newsmvvm.ui.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opentechspace.newsmvvm.Adapter.NewsAdapter
import com.opentechspace.newsmvvm.Adapter.OnItemClickListener
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.R
import com.opentechspace.newsmvvm.Resource.Resource
import com.opentechspace.newsmvvm.ViewModel.NewsViewModel
import com.opentechspace.newsmvvm.ui.MainActivity
import com.opentechspace.newsmvvm.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Search : Fragment(), OnItemClickListener {
    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        searchView = view.findViewById(R.id.searchView)
        newsAdapter = NewsAdapter(requireContext(),this)
        recyclerView = view.findViewById(R.id.searchRecyclerView)
        searchProgressBar = view.findViewById(R.id.search_progressBar)
        setRecyclerView()

        var job : Job? = null
        searchView.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_NEW_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty())
                    {
                        viewModel.getSearchNews(editable.toString())
                    }
                }

            }
        }
        viewModel.searchNewsResponse.observe(viewLifecycleOwner, Observer {response->
            when(response) {
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        Log.e("data",newsResponse.articles.toString())
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let {message->
                    }

                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }
    private fun hideProgressBar(){
        searchProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        searchProgressBar.visibility = View.VISIBLE
    }
    private fun setRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    override fun OnClick(article: Article) {
        val action = SearchDirections.actionSearchToDetailsNews(article)
        findNavController().navigate(action)
    }
}