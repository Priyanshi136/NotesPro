package com.example.notespro

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class Utility {

    // a class for making the toast function. So that we don't have to write it again and again.
    companion object{
        fun showToast(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun getCollectionReferenceForNotes(): CollectionReference{

            val currentUser = FirebaseAuth.getInstance().currentUser
            return FirebaseFirestore.getInstance().collection("notes").document(currentUser!!.uid).collection("my_notes")
        }

        fun timestampToString(timestamp: com.google.firebase.Timestamp): String{
            return SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate())
        }
    }
}