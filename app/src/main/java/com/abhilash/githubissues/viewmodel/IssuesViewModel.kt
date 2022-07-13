package com.abhilash.githubissues.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.data.error.ErrorManager
import com.abhilash.githubissues.model.database.IssuesDatabase
import com.abhilash.githubissues.model.database.IssuesLocalRepository
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.model.entities.Issues
import com.abhilash.githubissues.model.network.DataRepository
import com.abhilash.githubissues.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(private val application: Application, private val dataRepository: DataRepository) : ViewModel() {
    private val repository : IssuesLocalRepository

    var allLocalIssues: LiveData<List<Issue>>

    @Inject
    lateinit var errorManager: ErrorManager
    init {
        val dao = IssuesDatabase.getDatabase(application).issuesDao()
        repository = IssuesLocalRepository(dao)
        allLocalIssues = repository.getAllLocalIssues()
        Log.i("FETCHED_LOCAL_DATA", allLocalIssues.toString())
    }

    fun addIssue(issue: Issue) = viewModelScope.launch(Dispatchers.IO) {
        val addedIssueId = repository.addNewIssuesData(issue)
        Log.i("INSERTED_ITEM_ID", addedIssueId.toString())
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val issuesLiveDataPrivate = MutableLiveData<Resource<Issues>>()
    val issuesLiveData: LiveData<Resource<Issues>> get() = issuesLiveDataPrivate

    fun getAllIssues() {
        viewModelScope.launch {
            issuesLiveDataPrivate.value = Resource.Loading()
            dataRepository.getAllIssues().collect {
                issuesLiveDataPrivate.value = it
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openIssueDetailsPrivate = MutableLiveData<SingleEvent<Issue>>()
    val openIssueDetails: LiveData<SingleEvent<Issue>> get() = openIssueDetailsPrivate

    fun openIssueDetails(issue: Issue) {
        openIssueDetailsPrivate.value = SingleEvent(issue)
    }

    //DETAILS VIEW MODEL DATA
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val issuePrivate = MutableLiveData<Issue>()
    val issueData: LiveData<Issue> get() = issuePrivate

    fun initIntentData(issue: Issue): String {
        issuePrivate.value = issue
        return issue.comments_url.substringAfter("issues/").split("/")[0]
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val commentsLiveDataPrivate = MutableLiveData<Resource<Comments>>()
    val commentsLiveData: LiveData<Resource<Comments>> get() = commentsLiveDataPrivate

    fun getAllComments(issueId: String) {
        viewModelScope.launch {
            commentsLiveDataPrivate.value = Resource.Loading()
            dataRepository.getAllCommentsForSelectedIssue(issueId).collect {
                commentsLiveDataPrivate.value = it
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun showToast(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

}