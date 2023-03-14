package com.practice.baseadapter

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.practice.baseadapter.databinding.ActivityMainBinding
import com.practice.baseadapter.databinding.CustomAddDialogBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),ClickList {
    lateinit var binding: ActivityMainBinding
    val database= Firebase.database
    lateinit var userbaseAdapter: UserbaseAdapter
    var userList = ArrayList<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database.reference.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var userModel=snapshot.getValue(UserModel::class.java)
                userModel?.key=snapshot.key
                userList.add(userModel?:UserModel())

                userbaseAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                for (i in 0..userList.size-1){
                    if (userList[i].key==snapshot.key){
                        var userModel=snapshot.getValue(UserModel::class.java)
                        userModel?.key=snapshot.key
                        userList.set(i,userModel?:UserModel())
                        userbaseAdapter.notifyDataSetChanged()
                        break
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                for (i in 0..userList.size-1){
                    if (userList[i].key==snapshot.key){
                        userList.removeAt(i)
                        userbaseAdapter.notifyDataSetChanged()
                        break
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        userbaseAdapter = UserbaseAdapter(userList, this)
        binding.lvlist1.adapter = userbaseAdapter
        binding.floatbtnadd.setOnClickListener {
            var dialogBinding = CustomAddDialogBinding.inflate(layoutInflater)
            var customDialog = Dialog(this)
            customDialog.setContentView(dialogBinding.root)
            dialogBinding.btnadd.setOnClickListener {
                if (dialogBinding.etadddialog.text.toString().isEmpty()) {
                    dialogBinding.etadddialog.error = "enter name"
                }else if (dialogBinding.etRollNo.text.toString().isEmpty()){
                    dialogBinding.etRollNo.error="enter rollno"
                }
                else {
                    var userModel=UserModel(dialogBinding.etadddialog.text.toString(),dialogBinding.etRollNo.text.toString())
                    database.reference.push().setValue(userModel)
                   // userList.add(UserModel(dialogBinding.etadddialog.text.toString(),dialogBinding.etRollNo.text.toString()))
                    userbaseAdapter.notifyDataSetChanged()
                    customDialog.dismiss()
                }
            }
            dialogBinding.btncancel.setOnClickListener {
                customDialog.dismiss()
            }
            customDialog.show()
        }
    }

    override fun listClicked(position: Int) {
        var dialogBinding = CustomAddDialogBinding.inflate(layoutInflater)
        var customDialog = Dialog(this)
        customDialog.setContentView(dialogBinding.root)
        dialogBinding.btnadd.setText(resources.getString(R.string.edit_name))
        dialogBinding.btncancel.setText(resources.getString(R.string.delete_name))
        dialogBinding.btnadd.setOnClickListener {
            if (dialogBinding.etadddialog.text.toString().isEmpty()) {
                dialogBinding.etadddialog.error = "enter name"
            }else if (dialogBinding.etRollNo.text.toString().isEmpty()){
                dialogBinding.etRollNo.error="enter rollno"
            }
            else {
                val updateUserModel=UserModel()
                updateUserModel.name=dialogBinding.etadddialog.text.toString()
                updateUserModel.rollno=dialogBinding.etRollNo.text.toString()
                updateUserModel.key=userList[position].key
                database.reference.child(updateUserModel.key?:"").setValue(updateUserModel)
              //  userList.set(position,UserModel(dialogBinding.etadddialog.text.toString(),dialogBinding.etRollNo.text.toString()))
                userbaseAdapter.notifyDataSetChanged()
                customDialog.dismiss()
            }
        }
        dialogBinding.btncancel.setOnClickListener {
            database.reference.child(userList[position].key?:"").removeValue()
          //  userList.removeAt(position)
            userbaseAdapter.notifyDataSetChanged()
            customDialog.dismiss()
        }

        customDialog.show()

    }
}


