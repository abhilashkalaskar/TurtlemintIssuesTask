package com.abhilash.githubissues.model.network

import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issues
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {

    suspend fun getAllIssues(): Flow<Resource<Issues>>

    suspend fun getAllCommentsForSelectedIssue(issueId: String): Flow<Resource<Comments>>

}
