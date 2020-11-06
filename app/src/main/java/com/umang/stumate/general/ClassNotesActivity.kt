package com.umang.stumate.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Filter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.adapters.ClassNotesAdapter
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_notes.*
import java.io.File

class ClassNotesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classNotesList: ArrayList<FileUploadData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_notes)

        AppPreferences.init(this)

        closeButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        classNotesList = ArrayList<FileUploadData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = false


        sortbyDate.setOnClickListener {

            linearLayoutManager.reverseLayout = true
        }

        filterSubjects.setOnClickListener {
            startActivity(Intent(this, FilterSubjectsActivity::class.java))
        }

        // Retrieving Data from Firebase Realtime Database
        retriveClassNotesData()
    }

    private fun retriveClassNotesData() {

        val myRef = FirebaseDatabase.getInstance().reference.child(AppPreferences.studentID).child("files_data")

        val classNotesListener = object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val classNotesData = ds.getValue(FileUploadData::class.java)

                    if (classNotesData != null) {
                        classNotesList.add(classNotesData)


                    }
                }

                val classNotesAdapter = ClassNotesAdapter(this@ClassNotesActivity,classNotesList)
                classNotesRecycler.layoutManager = linearLayoutManager
                classNotesRecycler.adapter = classNotesAdapter
            } override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ClassNotesActivity,error.message,Toast.LENGTH_LONG).show()
            }
        }
        myRef.addValueEventListener(classNotesListener)

    }
}