package com.example.mvvmwithfirebase.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.databinding.ItemNoteLayoutBinding
import com.example.mvvmwithfirebase.util.addChip
import com.example.mvvmwithfirebase.util.hide
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemCLicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    private val sdf = SimpleDateFormat("dd MMM yyyy")
    private var list: MutableList<Note> = arrayListOf()

    inner class MyViewHolder(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.title.text = item.title
            binding.date.text = sdf.format(item.date)
            binding.tags.apply {
                if (item.tags.isEmpty()) {
                    hide()
                } else {
                    removeAllViews()
                    if (item.tags.size > 2) {
                        item.tags.subList(0, 2).forEach { addChip(it) }
                        addChip("+${item.tags.size - 2}")
                    } else {
                        item.tags.forEach { addChip(it) }
                    }
                }
            }
            binding.desc.apply {
                if (item.description.length > 120) {
                    text = "${item.description.substring(0, 120)}..."
                }else{
                    text=item.description
                }
            }
            binding.itemLayout.setOnClickListener {
                onItemCLicked.invoke(
                    adapterPosition,
                    item
                )
            }// bosilganini NoteListingFragmentga beryabdi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size


    fun updateList(list: MutableList<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemChanged(position)
    }
}