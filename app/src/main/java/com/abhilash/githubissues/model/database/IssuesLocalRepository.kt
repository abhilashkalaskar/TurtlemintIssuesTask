package com.abhilash.githubissues.model.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.abhilash.githubissues.model.entities.Issue

class IssuesLocalRepository (private val issuesDao: IssuesDao){

    @WorkerThread
    fun getAllLocalIssues(): LiveData<List<Issue>>{
        return issuesDao.getAllIssues()
    }

    @WorkerThread
    suspend fun addNewIssuesData(issue: Issue): Long{
        return issuesDao.addNewIssue(issue)
    }

}