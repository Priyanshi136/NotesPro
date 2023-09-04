package com.example.notespro

import android.app.DownloadManager.Query
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import io.grpc.okhttp.internal.Util

class MainActivity : AppCompatActivity() {

    private lateinit var addNoteBtn: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuBtn: ImageButton
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNoteBtn = findViewById(R.id.add_note_btn)
        recyclerView = findViewById(R.id.recycler_view)
        menuBtn = findViewById(R.id.menu_btn)

        addNoteBtn.setOnClickListener {
            startActivity(Intent(this, NotesDetailActivity::class.java))
        }

        menuBtn.setOnClickListener {
            showMenu()
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()

        recyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(options, this)
        recyclerView.adapter = noteAdapter
    }

    private fun showMenu() {
        // display menu
        val popupMenu = PopupMenu(this, menuBtn)
        popupMenu.menu.add("LogOut")
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {item->

            when(item.title){
                "LogOut"->{
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginScreen::class.java))
                    finish()
                }
            }
            true
        })
    }

    override fun onStart() {
        super.onStart()
        noteAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.notifyDataSetChanged()
    }
}