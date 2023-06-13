package com.opentechspace.newsmvvm.ui.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opentechspace.newsmvvm.Adapter.NewsAdapter
import com.opentechspace.newsmvvm.Adapter.OnItemClickListener
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.R
import com.opentechspace.newsmvvm.Repository.NewsRepository
import com.opentechspace.newsmvvm.Resource.Resource
import com.opentechspace.newsmvvm.ViewModel.NewsViewModel
import com.opentechspace.newsmvvm.ViewModel.ViewModelFactory
import com.opentechspace.newsmvvm.db.LocalDatabase
import com.opentechspace.newsmvvm.ui.MainActivity


class Breaking : Fragment(), OnItemClickListener {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.breaking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsAdapter(requireContext(),this)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.breakingRecyclerView)
        setRecyclerView()


        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
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
        progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }
    private fun setRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    override fun OnClick(article: Article) {
//        val bundle = Bundle().apply {
//            putSerializable("Article",article)
//        }
        val action = BreakingDirections.actionBreakingToDetailsNews(article)
       findNavController().navigate(action)
    }
}