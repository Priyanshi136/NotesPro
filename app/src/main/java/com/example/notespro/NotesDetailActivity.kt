package com.example.notespro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class NotesDetailActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveNoteBtn: ImageButton
    private lateinit var pageTitleTextView: TextView
    private var title = ""
    var content = ""
    private var docId: String? = null
    var isEditMode = false
    private lateinit var deleteNoteTextViewBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_detail)

        titleEditText = findViewById(R.id.notes_title_text)
        contentEditText = findViewById(R.id.notes_content_text)
        saveNoteBtn = findViewById(R.id.save_note_btn)
        pageTitleTextView = findViewById(R.id.page_title)
        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn)

        // receive data

        title = intent.getStringExtra("title").toString()
        content = intent.getStringExtra("content").toString()
        docId = intent.getStringExtra("docId")

        if(docId != null && docId!!.isNotEmpty()){
            isEditMode = true
        }

        if(isEditMode){
            pageTitleTextView.text = "Edit your note"
            titleEditText.setText(title)
            contentEditText.setText(content)
            deleteNoteTextViewBtn.visibility = View.VISIBLE
        }


        saveNoteBtn.setOnClickListener {
            saveNote()
        }

        deleteNoteTextViewBtn.setOnClickListener {
            deleteNoteFromFirebase()
        }
    }

    private fun deleteNoteFromFirebase() {

        // update the note
        val documentReference: DocumentReference = Utility.getCollectionReferenceForNotes().document(docId!!)

        documentReference.delete().addOnCompleteListener {task ->
            if(task.isSuccessful){
                // note is deleted
                Utility.showToast(this, "Note deleted successfully")
                finish()
            }else{
                Utility.showToast(this, "Failed while deleting note")
            }
        }
    }

    private fun saveNote() {
        val noteTitle = titleEditText.text.toString()
        val noteContent = contentEditText.text.toString()

        if(noteTitle.isEmpty()){
            titleEditText.error = "Title is required."
            return
        }

        val note = Note(noteTitle, noteContent, Timestamp.now())
        saveNoteToFirebase(note)

    }

    private fun saveNoteToFirebase(note: Note) {

        val documentReference: DocumentReference = if(isEditMode){
            // update the note
            Utility.getCollectionReferenceForNotes().document(docId!!)
        }else{
            // create new note
            Utility.getCollectionReferenceForNotes().document()
        }

        documentReference.set(note).addOnCompleteListener {task ->
            if(task.isSuccessful){
                // note is added
                Utility.showToast(this, "Note added successfully")
                finish()
            }else{
                Utility.showToast(this, "Failed while adding note")
            }
        }
    }
}