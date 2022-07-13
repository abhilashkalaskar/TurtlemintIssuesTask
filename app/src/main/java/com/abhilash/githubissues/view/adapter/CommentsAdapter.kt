package com.abhilash.githubissues.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhilash.githubissues.R
import com.abhilash.githubissues.model.entities.Comment
import com.abhilash.githubissues.utils.Utility
import com.abhilash.githubissues.viewmodel.IssuesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comment_list.view.*

class CommentsAdapter(private val issuesViewModel: IssuesViewModel, private val commentsList: List<Comment>, private val context: Context) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment_list, parent, false))
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.tvTitle.text = comment.body
        holder.tvUserNameDate.text = "By ${comment.user.login} on ${Utility.changeDateFormat(comment.updated_at)}"

        Picasso.get().load(comment.user.avatar_url).placeholder(R.drawable.user).error(R.drawable.user).into(holder.ivAvator)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvator: ImageView = view.imageview_user_avator
        val tvUserNameDate: TextView = view.textview_user_name_date
        val tvTitle: TextView = view.textview_issue_title
    }
}