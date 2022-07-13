package com.abhilash.githubissues.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhilash.githubissues.R
import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.utils.AppConstants
import com.abhilash.githubissues.utils.observe
import com.abhilash.githubissues.view.adapter.CommentsAdapter
import com.abhilash.githubissues.viewmodel.IssuesViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueDetailsActivity : AppCompatActivity() {

    private val issuesViewModel: IssuesViewModel by viewModels()

    private var imageViewAvator: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvUserName: TextView? = null
    private var tvDate: TextView? = null
    private var tvDescription: TextView? = null
    private var rvCommentsList: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_details)
        val issueId = issuesViewModel.initIntentData(intent.getParcelableExtra(AppConstants.ISSUE_ITEM_KEY)!!)
        observeViewModel()
        issuesViewModel.getAllComments(issueId)
    }

    private fun observeViewModel() {
        observe(issuesViewModel.issueData, ::initializeViews)
        observe(issuesViewModel.commentsLiveData, ::handleCommentsList)
    }

    private fun initializeViews(issue: Issue) {
        imageViewAvator = findViewById(R.id.imageview_user_avator)
        tvTitle = findViewById(R.id.textview_issue_title)
        tvUserName = findViewById(R.id.textview_user_name)
        tvDate = findViewById(R.id.textview_last_modified)
        tvDescription = findViewById(R.id.textview_issue_description)
        rvCommentsList = findViewById(R.id.rv_comments_list)
        progressBar = findViewById(R.id.pb_loading)

        val layoutManager = LinearLayoutManager(this)
        rvCommentsList?.layoutManager = layoutManager

        tvTitle?.text = issue.title
        tvDescription?.text = issue.body
        tvUserName?.text = "By ${issue.user.login}"
        tvDate?.text = "Last updated on ${issue.updated_at}"

        Picasso.get().load(issue.user.avatar_url).placeholder(R.drawable.user).error(R.drawable.user).into(imageViewAvator)

    }

    private fun handleCommentsList(status: Resource<Comments>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { showListData(comments = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { showToastMessage(status.errorCode) }
            }
        }
    }

    private fun showToastMessage(errorCode: Int) {

    }

    private fun showLoadingView() {
        rvCommentsList?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE
    }

    private fun showListData(comments: Comments) {
        if (!(comments.commentsList.isNullOrEmpty())) {
            val issuesAdapter = CommentsAdapter(issuesViewModel, comments.commentsList, this@IssueDetailsActivity)
            rvCommentsList?.adapter = issuesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showDataView(show: Boolean) {
        rvCommentsList?.visibility = if (show) View.VISIBLE else View.GONE
        progressBar?.visibility = View.GONE
    }

}