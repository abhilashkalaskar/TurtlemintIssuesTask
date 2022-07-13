package com.abhilash.githubissues.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhilash.githubissues.R
import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.data.error.NO_INTERNET_CONNECTION
import com.abhilash.githubissues.data.error.NO_LOCAL_DATA_ERROR
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.model.entities.Issues
import com.abhilash.githubissues.utils.*
import com.abhilash.githubissues.view.adapter.IssuesAdapter
import com.abhilash.githubissues.viewmodel.IssuesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var progressBar: FrameLayout? = null
    private var recyclerViewIssues: RecyclerView? = null

    private val issuesViewModel: IssuesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.pb_loading)
        recyclerViewIssues = findViewById(R.id.rv_issues_list)

        val layoutManager = LinearLayoutManager(this)
        recyclerViewIssues?.layoutManager = layoutManager

        observeViewModel()
        issuesViewModel.getAllIssues()
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<Issue>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this@MainActivity, IssueDetailsActivity::class.java).apply {
                putExtra(AppConstants.ISSUE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeViewModel() {
        observe(issuesViewModel.issuesLiveData, ::handleIssuesList)
        observeEvent(issuesViewModel.openIssueDetails, ::navigateToDetailsScreen)
        observe(issuesViewModel.allLocalIssues, ::handleLocalIssuesList)
        observeToast(issuesViewModel.showToast)

    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        showToast(this, event, Toast.LENGTH_LONG, this@MainActivity)
    }

    private fun handleIssuesList(status: Resource<Issues>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let {
                if (it.issuesList.isNullOrEmpty()){
                    issuesViewModel.showToast(NO_INTERNET_CONNECTION)
                } else {
                    for (item in it.issuesList){
                        issuesViewModel.addIssue(item)
                        val issueId = issuesViewModel.initIntentData(item)
                        val comments = issuesViewModel.getAllComments(issueId)
                    }
                    showListData(issues = it)
                }

            }
            is Resource.DataError -> {
                showLocalData()
                status.errorCode?.let { issuesViewModel.showToast(it) }
            }
        }
    }

    private fun handleLocalIssuesList(list: List<Issue>) {
        Log.i("LOCAL_DATA_ACTIVITY", list.toString())

        if (list.isNullOrEmpty()) {
                issuesViewModel.showToast(NO_LOCAL_DATA_ERROR)
            } else {
                showListData(issues = Issues(list as ArrayList<Issue>))
            }
    }

    private fun showLocalData() {
        issuesViewModel.allLocalIssues
    }

    private fun showDataView(show: Boolean) {
        recyclerViewIssues?.visibility = if (show) View.VISIBLE else View.GONE
        progressBar?.visibility = View.GONE
    }

    private fun showListData(issues: Issues) {
        if (!(issues.issuesList.isNullOrEmpty())) {
            val issuesAdapter = IssuesAdapter(issuesViewModel, issues.issuesList, this@MainActivity)
            recyclerViewIssues?.adapter = issuesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showLoadingView() {
        recyclerViewIssues?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE
    }

}