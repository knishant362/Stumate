package com.umang.stumate.general

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_student_profile.*

class StudentProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        AppPreferences.init(this)

        retrieveStudentDetails(AppPreferences.studentEmailID.toString())

        closeButton.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }

        profileNameLayout.visibility = View.GONE
        profileEmailLayout.visibility = View.GONE
        profilePhoneLayout.visibility = View.GONE
        profileCollegeLayout.visibility = View.GONE
        profileDeptSectionLayout.visibility = View.GONE

        divider.visibility = View.GONE
        emailDivider.visibility = View.GONE
        phoneNumberDivider.visibility = View.GONE
        collegeDivider.visibility = View.GONE
        deptSectionDivider.visibility = View.GONE
        lastDivider.visibility =View.GONE

        profileTextLayout.visibility = View.GONE

       loadingAnimationView.visibility = View.VISIBLE

        profileFAB.setOnClickListener {
            startActivity(Intent(this, UploadFilesActivity::class.java))
        }


        profileBottomNav.setOnClickListener {
            val dialog= BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_home, null)


            view.findViewById<TextView>(R.id.homePage).setOnClickListener {
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.homePage).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, HomeActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, ClassNotesActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.remainders).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, ReminderActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                // dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.profile).setOnClickListener {
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.profile).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, StudentProfileActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()


            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))
                //startActivity(Intent(this, StudentDetailsActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.logOut).setOnClickListener {

                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.logOut).setTextColor(resources.getColor(R.color.colorPrimary))

                // Logout the user from session
                AppPreferences.isLogin = false
                AppPreferences.studentID = ""
                AppPreferences.studentName = ""
                startActivity(Intent(this, AuthenticationActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                //  dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.collegeMates).setTextColor(resources.getColor(R.color.colorPrimary))
                startActivity(Intent(this, ClassMatesActivity::class.java))

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()

            }


            dialog.setContentView(view)
            //dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }




    }

    private fun retrieveStudentDetails(emailID: String) {

        val myRef = FirebaseDatabase.getInstance().reference
        val studentDataQuery = myRef.child("students_data").orderByChild("emailID").equalTo(emailID)
        val studentDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {

                    loadingAnimationView.visibility = View.GONE

                    profileNameLayout.visibility = View.VISIBLE
                    profileEmailLayout.visibility = View.VISIBLE
                    profilePhoneLayout.visibility = View.VISIBLE
                    profileCollegeLayout.visibility = View.VISIBLE
                    profileDeptSectionLayout.visibility = View.VISIBLE

                    divider.visibility = View.VISIBLE
                    emailDivider.visibility = View.VISIBLE
                    phoneNumberDivider.visibility = View.VISIBLE
                    collegeDivider.visibility = View.VISIBLE
                    deptSectionDivider.visibility = View.VISIBLE
                    lastDivider.visibility = View.VISIBLE


                    profileTextLayout.visibility = View.VISIBLE


                    for (ds in dataSnapshot.children) {

                        val studentData = ds.getValue(StudentData::class.java)
                        val key = ds.key.toString()

                        if (studentData != null) {
                            val studentName=studentData.studentName.toString()

                            val count = studentName.split(" ").toTypedArray()

                            if(count.size == 1) {

                                profileTextLayout.findViewById<TextView>(R.id.profileText).text = studentName[0].toString()


                            } else {
                                val index = studentName?.lastIndexOf(' ')
                                val firstName = index?.let { it1 -> studentName?.substring(0, it1) }
                                val lastName = index?.plus(1)?.let { it1 -> studentName?.substring(
                                    it1
                                ) }

                                profileTextLayout.findViewById<TextView>(R.id.profileText).text = firstName?.toString()[0] + lastName[0]?.toString()

                            }


                            profileTextLayout.findViewById<TextView>(R.id.editProfile).setOnClickListener {


                                profileTextLayout.findViewById<TextView>(R.id.editProfile).visibility = View.GONE

                                saveProfile.visibility = View.VISIBLE

                                profileName.visibility = View.GONE
                                txtProfileName.visibility = View.GONE
                                profileIcon.visibility = View.GONE

                                edtStudentName.visibility = View.VISIBLE
                                editStudentName.setText(studentName.toString())

                                txtprofilePhone.visibility = View.GONE
                                profilePhone.visibility = View.GONE
                                profilePhoneIcon.visibility = View.GONE

                                edtPhoneNumber.visibility = View.VISIBLE
                                editPhoneNumber.setText(studentData.studentPhoneNumber.toString())


                            }

                            saveProfile.setOnClickListener {



                                val studentName = editStudentName.text!!.trim().toString()
                                val phoneNumber = editPhoneNumber.text!!

                                if(studentName.isEmpty()) {
                                    edtStudentName.error = "Please enter Student Name"
                                } else if(phoneNumber.isEmpty() and phoneNumber.length) {
                                    edtStudentName.error = null
                                    edtPhoneNumber.error = "Please enter Valid Phone Number"
                                } else {
                                    edtPhoneNumber.error = null

                                    AppPreferences.studentName = studentName

                                   val myRef = FirebaseDatabase.getInstance().getReference("students_data").ref.child(key)
                                    myRef.setValue(StudentData(AppPreferences.studentID, studentName.toString(),studentData.emailID.toString(), phoneNumber.toString(), studentData.collegeName.toString(),studentData.graduationYear.toString(),studentData.studentDept.toString(),studentData.studentSection.toString()))

                                    startActivity(
                                        Intent(
                                            this@StudentProfileActivity,
                                            HomeActivity::class.java
                                        )
                                    )


                                    Toast.makeText(
                                        this@StudentProfileActivity,
                                        "Profile Updated Successfully !",
                                        Toast.LENGTH_LONG
                                    ).show()


                                }


                            }


                            profileName.text = studentName
                            profileEmail.text = studentData.emailID.toString()
                            profilePhone.text = "+91 " + studentData.studentPhoneNumber.toString()
                            profileCollege.text = studentData.collegeName.toString()
                            profileDeptSection.text =   studentData.graduationYear.toString() +" "+ studentData.studentDept.toString()

                        }

                    }
            } else {

                    Toast.makeText(baseContext, "No Data Found!", Toast.LENGTH_LONG).show()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    baseContext,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()



            }
        }
        studentDataQuery.addListenerForSingleValueEvent(studentDataListener)
    }
}

private infix fun Boolean.and(length: Int): Boolean {
    return length != 10
}
