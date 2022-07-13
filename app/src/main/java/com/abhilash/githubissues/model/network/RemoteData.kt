package com.abhilash.githubissues.model.network

import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.data.error.NETWORK_ERROR
import com.abhilash.githubissues.data.error.NO_INTERNET_CONNECTION
import com.abhilash.githubissues.model.entities.Comment
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.model.entities.Issues
import com.abhilash.githubissues.utils.NetworkConnectivity
import retrofit2.Response
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    private lateinit var issueid: String
    override suspend fun getAllIssues(): Resource<Issues> {
        val issuesService = serviceGenerator.createService(IssuesApiService::class.java)
        return when (val response = processCall(issuesService::getAllIssues)) {
            is List<*> -> {
                Resource.Success(data = Issues(response as ArrayList<Issue>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getAllComments(issueId: String): Resource<Comments> {
        issueid = issueId
        val issuesService = serviceGenerator.createService(IssuesApiService::class.java)
        return when (val response = processCall(issuesService::getAllCommentsForSelectedIssue)) {
            is List<*> -> {
                Resource.Success(data = Comments(response as ArrayList<Comment>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
        return null!!
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {

        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NETWORK_ERROR
        }
    }

    private suspend fun processCall(responseCall: KSuspendFunction1<String, Response<List<Comment>>>): Any? {

        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(issueid)
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NETWORK_ERROR
        }
    }

}