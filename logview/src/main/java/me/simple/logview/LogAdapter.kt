package me.simple.logview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter(private val items: MutableList<LogBean>) :
    RecyclerView.Adapter<LogAdapter.LogHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LogHolder(inflater.inflate(R.layout.item_log, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LogHolder, position: Int) {
        val item = items[holder.adapterPosition]
        val color = Utils.getColor(item.priority)

        holder.tvTag.run {
            setTextColor(color)
            text = String.format("%s：%s", item.priority, item.tag)
        }
        holder.tvMsg.run {
            setTextColor(color)
            text = item.msg
        }
        holder.tvTime.run {
            setTextColor(color)
            text = item.time
        }
    }

    class LogHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTag = itemView.findViewById<TextView>(R.id.tvTag)!!
        val tvMsg = itemView.findViewById<TextView>(R.id.tvMsg)!!
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)!!
    }
}