package com.abhilash.githubissues.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhilash.githubissues.R
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.utils.Utility
import com.abhilash.githubissues.viewmodel.IssuesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_issues_list.view.*

class IssuesAdapter (private val issuesViewModel: IssuesViewModel, private val issuesList: List<Issue>, private val context: Context) : RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        return IssuesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_issues_list, parent, false))
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val issue = issuesList[position]
        holder.tvTitle.text = issue.title
        holder.tvDescription.text = issue.body
        holder.tvUserNameDate.text = "Modified by ${issue.user.login} on ${Utility.changeDateFormat(issue.updated_at)}"

        Picasso.get().load(issue.user.avatar_url).placeholder(R.drawable.user).error(R.drawable.user).into(holder.ivAvator)

        holder.itemView.setOnClickListener {
            issuesViewModel.openIssueDetails(issue)
        }
    }

    override fun getItemCount(): Int {
        return issuesList.size
    }

    class IssuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvator = view.imageview_user_avator
        val tvUserNameDate = view.textview_user_name_date
        val tvTitle = view.textview_issue_title
        val tvDescription = view.textview_issue_description
    }
}
