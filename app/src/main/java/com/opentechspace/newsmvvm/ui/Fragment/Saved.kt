package com.opentechspace.newsmvvm.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.opentechspace.newsmvvm.Adapter.NewsAdapter
import com.opentechspace.newsmvvm.Adapter.OnItemClickListener
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.R
import com.opentechspace.newsmvvm.ViewModel.NewsViewModel
import com.opentechspace.newsmvvm.ui.MainActivity

class Saved : Fragment(), OnItemClickListener {
    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsAdapter(requireContext(),this)
        recyclerView = view.findViewById(R.id.savedRecyclerView)
        setRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            @Suppress("DEPRECATION")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteDb(article)
                Snackbar.make(view,"Deleted Successfully",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.insertDb(article)
                    }
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }

        viewModel.getAllSavedDBNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })

    }

    private fun setRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    override fun OnClick(article: Article) {
        val action = SavedDirections.actionSavedToDetailsNews(article)
        findNavController().navigate(action)
    }

}