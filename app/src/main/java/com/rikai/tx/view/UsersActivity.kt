package com.rikai.tx.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rikai.tx.BaseApp.Companion.baseApp
import com.rikai.tx.adapter.UsersAdapter
import com.rikai.tx.databinding.ActivityUsersBinding
import com.rikai.tx.view_model.UsersViewModel
import com.rikai.tx.view_model.UsersViewModelFactory

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    lateinit var viewModel: UsersViewModel
    lateinit var viewModelFactory: UsersViewModelFactory
    lateinit var userAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = UsersViewModelFactory(baseApp!!.appRepositoryImpl)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UsersViewModel::class.java)
        setupRecyclerView()
        initializeView()
        initializeViewModel()
    }

    private fun setupRecyclerView() {
        userAdapter = UsersAdapter()
        binding.usersRv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@UsersActivity)
        }
        binding.usersRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) ) {
                    if(viewModel.showLoading.value == false){
                        viewModel.getUsersPagination()
                    }
                }
            }
        })
    }

    private fun initializeView() {
        userAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("user", it)
            }
            val intent = intentDetail(this)
            intent.putExtra("bundleDetailUser", bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun initializeViewModel() {
        viewModel.showResult.observe(this, Observer {
            if(it) {
//                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
                userAdapter.users = viewModel.users
            }
        })
        viewModel.showLoading.observe(this, Observer {
            if(it) {
                binding.progress.visibility = View.VISIBLE
            }else {
                binding.progress.visibility = View.INVISIBLE
            }
        })
    }

    companion object {
        fun intentDetail(context: Context): Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }
}