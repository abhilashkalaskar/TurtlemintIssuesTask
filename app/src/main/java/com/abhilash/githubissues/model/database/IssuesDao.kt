package com.abhilash.githubissues.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhilash.githubissues.data.Resource
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.model.entities.Issues
import kotlinx.coroutines.flow.Flow

@Dao
interface IssuesDao {

    @Query("select * from ISSUES_TABLE ORDER BY updated_at")
    fun getAllIssues(): LiveData<List<Issue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewIssue(issue: Issue): Long

}