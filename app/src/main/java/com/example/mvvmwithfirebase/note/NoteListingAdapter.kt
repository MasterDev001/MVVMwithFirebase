package com.example.mvvmwithfirebase.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.databinding.ItemNoteLayoutBinding

class NoteListingAdapter(
    val onItemCLicked: (Int, Note) -> Unit,
    val onDeleteClicked: (Int, Note) -> Unit,
    val onEditClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    private var list: MutableList<Note> = arrayListOf()

    inner class MyViewHolder(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.noteIdValue.text = item.id
            binding.msg.text = item.text

            binding.edit.setOnClickListener {
                onEditClicked.invoke(
                    bindingAdapterPosition,
                    item
                )
            } // bosilganini NoteListingFragmentga beryabdi
            binding.delete.setOnClickListener { onDeleteClicked.invoke(bindingAdapterPosition, item) }
            binding.itemLayout.setOnClickListener { onItemCLicked.invoke(bindingAdapterPosition, item) }
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