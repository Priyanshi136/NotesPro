package com.example.notespro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NoteAdapter(private val options: FirestoreRecyclerOptions<Note>, val context: Context) :
    FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(options) {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById<TextView>(R.id.notes_title_text_view)
        val contentTextView: TextView = itemView.findViewById<TextView>(R.id.notes_content_text_view)
        val timestampTextView: TextView = itemView.findViewById<TextView>(R.id.notes_timestamp_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, note: Note) {
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.timestampTextView.text = Utility.timestampToString(note.timestamp)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NotesDetailActivity::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)
            val docId = this.snapshots.getSnapshot(position).id
            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }
    }
}