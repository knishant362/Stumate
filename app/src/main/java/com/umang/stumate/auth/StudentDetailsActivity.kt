package com.umang.stumate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.umang.stumate.R
import com.umang.stumate.general.HomeActivity
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_student_details.*

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    private lateinit var collegeID: String
    private lateinit var deptID: String
    private lateinit var yearID: String
    private lateinit var sectionID: String

    private var COLLEGE_NAME_GMRIT = Pair("GMR Institute of Technology","GMRIT")

    private var CSE_DEPT = Pair("Computer Science Engineering","CSE")
    private var IT_DEPT = Pair("Information Technology", "IT")

    private var FIRST_YEAR = Pair("1st Year","1")
    private var SECOND_YEAR = Pair("2nd Year","2")
    private var THIRD_YEAR = Pair("3rd Year","3")
    private var FOURTH_YEAR = Pair("4th Year","4")

    private var A_SECTION = Pair("A Section", "A")
    private var B_SECTION = Pair("B Section", "B")
    private var C_SECTION = Pair("C Section", "C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setUpCollegeList()
        setUpDepartmentList()
        setUpSectionList()
        setUpYearList()
        database = Firebase.database.reference

        AppPreferences.init(this)


        btnSubmit.setOnClickListener {
            val studentName = editName.text
            val studentPhoneNumber = editPhone.text
            val collegeName = collegeList.text
            val graduationYear = yearsList.text
            val studentDept = deptSpinner.text
            val studentSection = sectionSpinner.text


            //TODO: Phone Number (Only 10 Digits length!=10) and Name (only Characters allowed) Validation

            if(isNullOrEmpty(studentName)) {
                edtName.error = "Please enter Name"
            } else if(isNullOrEmpty(studentPhoneNumber)) {
                edtName.error = null
                edtPhone.error = "Please enter Phone Number"
            } else if(isNullOrEmpty(collegeName)) {
                edtPhone.error = null
                edtCollegeName.error = "Please choose College Name"
            } else if(isNullOrEmpty(graduationYear)) {
                edtCollegeName.error = null
                edtGraduationYear.error = "Please enter Graduation Year E.g 2022"
            } else if(isNullOrEmpty(studentDept)) {
                edtGraduationYear.error = null
                edtDepartment.error = "Please choose Department"
            } else if(isNullOrEmpty(studentSection)) {
                edtDepartment.error = null
                edtSection.error = "Please choose Section"
            } else {
                edtSection.error = null
                val intent=intent
                val email=intent.getStringExtra("Email")

                val collegeID: String
                var deptID: String
                var yearID: String
                var sectionID: String


                if(studentDept.toString().equals("Computer Science Engineering")) {
                    deptID = "CSE"

                }
                else {
                    deptID = IT_DEPT.second

                }

                if(graduationYear.toString().equals("1st Year")){
                    yearID = FIRST_YEAR.second
                }
                else if(graduationYear.toString().equals("2nd Year")){
                    yearID = SECOND_YEAR.second
                }
                else if(graduationYear.toString().equals("3rd Year")){
                    yearID = THIRD_YEAR.second
                }
                else {
                    yearID = FOURTH_YEAR.second
                }

                if(studentSection.toString().equals("A Section")){
                    sectionID = A_SECTION.second
                }
               else if(studentSection.toString().equals("B Section")){
                    sectionID = B_SECTION.second
                }
                else {
                    sectionID = C_SECTION.second
                }

                var userID = "GMRIT"+"_"+deptID + "_"+ yearID+"_"+sectionID
                //showToast(userID + " " + email)


                //TODO: Should send Email ID from Authentication Activity and pass Parameter to Database
               writeNewUser(userID,studentName,email, studentPhoneNumber, collegeName,graduationYear,studentDept,studentSection)

            }
        }
    }

    private fun writeNewUser(userId: String, name: Editable?, email: String?,phone: Editable?, collegeName: Editable?, graduationYear: Editable?, studentDept: Editable?, studentSection: Editable? ) {
        val user = StudentData(userId,
            name.toString(),email.toString(),phone.toString(),collegeName.toString(), graduationYear.toString(),studentDept.toString(),studentSection.toString())
        database.child("students_data").push().setValue(user)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            
            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })

        AppPreferences.isLogin = true
        AppPreferences.studentName = name.toString()
        AppPreferences.studentID = userId.toString()

        startActivity(Intent(this, HomeActivity::class.java))

        showToast("Details Submitted Successfully !")
    }


    private fun isNullOrEmpty(str: Editable?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun setUpCollegeList() {
        val collegeNames = listOf(COLLEGE_NAME_GMRIT.first)
        val adapter = ArrayAdapter(this,
            R.layout.list_item, collegeNames)
        (collegeList as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpYearList() {
        val yearNumbers = listOf(FIRST_YEAR.first,SECOND_YEAR.first,THIRD_YEAR.first,FOURTH_YEAR.first)
        val adapter = ArrayAdapter(this,
            R.layout.list_item, yearNumbers)
        (yearsList as? AutoCompleteTextView)?.setAdapter(adapter)
    }


    private fun setUpDepartmentList() {
        val deptNames = listOf(CSE_DEPT.first, IT_DEPT.first)
        val adapter = ArrayAdapter(this,
            R.layout.list_item, deptNames)
        (deptSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpSectionList() {
        val sectionNames = listOf(A_SECTION.first, B_SECTION.first, C_SECTION.first)
        val adapter = ArrayAdapter(this,
            R.layout.list_item, sectionNames)
        (sectionSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}