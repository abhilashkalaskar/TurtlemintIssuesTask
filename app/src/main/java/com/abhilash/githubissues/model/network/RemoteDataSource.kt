package com.abhilash.githubissues.model.network

import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issues

internal interface RemoteDataSource {
    suspend fun getAllIssues(): Resource<Issues>
    suspend fun getAllComments(issueId: String): Resource<Comments>
}
