package com.permissionx.xuewei.pojos

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import com.permissionx.xuewei.R
import java.security.AccessControlContext

class HAdapter(context: Context?, resLayout: Int, data:List<ArticleItem>)
    : BaseRecyclerViewAdapter<ArticleItem>(context,resLayout,data) {

    private lateinit var onAgainClickListener: OnAgainClickListener

    fun makeOnAgainClickListener(onAgainClickListener: OnAgainClickListener){
        this.onAgainClickListener = onAgainClickListener
    }
    override fun convert(
        holder: BaseRecyclerHolder?,
        items: MutableList<ArticleItem>?,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseRecyclerHolder?, position: Int) {
        (holder?.getView<MyImageView>(R.id.hImage))?.setImageURL(datas[position].articlePic)
        (holder?.getView<MyImageView>(R.id.doctor_img))?.setImageURL(datas[position].headPic)
        (holder?.getView<TextView>(R.id.hcontent))?.text = datas[position].articleTitle
        (holder?.getView<TextView>(R.id.htotnum))?.text = datas[position].watchNum
        (holder?.getView<TextView>(R.id.article_cly))?.text = datas[position].articleCly
        (holder?.getView<TextView>(R.id.doctor_name))?.text = datas[position].writerName

        holder?.getView<androidx.cardview.widget.CardView>(R.id.article_item)?.setOnClickListener {
            onAgainClickListener.again()
        }
    }
    interface OnAgainClickListener{
        fun again()
    }
}