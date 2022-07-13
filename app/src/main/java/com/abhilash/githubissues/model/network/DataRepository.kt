package com.abhilash.githubissues.model.network

import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun getAllIssues(): Flow<Resource<Issues>> {
        return flow {
            emit(remoteRepository.getAllIssues())
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAllCommentsForSelectedIssue(issueId: String): Flow<Resource<Comments>> {
        return flow {
            emit(remoteRepository.getAllComments(issueId))
        }.flowOn(ioDispatcher)
    }
}
