package com.practice.baseadapter

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.baseadapter.databinding.ActivityMainBinding
import com.practice.baseadapter.databinding.CustomAddDialogBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),ClickList {
    lateinit var binding: ActivityMainBinding
    lateinit var userbaseAdapter: UserbaseAdapter
    var userList = ArrayList<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    userList.add(UserModel(dialogBinding.etadddialog.text.toString(),dialogBinding.etRollNo.text.toString()))
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
        dialogBinding.btnadd.setOnClickListener {
            if (dialogBinding.etadddialog.text.toString().isEmpty()) {
                dialogBinding.etadddialog.error = "enter name"
            }else if (dialogBinding.etRollNo.text.toString().isEmpty()){
                dialogBinding.etRollNo.error="enter rollno"
            }
            else {
                userList.set(position,UserModel(dialogBinding.etadddialog.text.toString(),dialogBinding.etRollNo.text.toString()))
                userbaseAdapter.notifyDataSetChanged()
                customDialog.dismiss()
            }
        }
        dialogBinding.btncancel.setOnClickListener {
            userList.removeAt(position)
            userbaseAdapter.notifyDataSetChanged()
            customDialog.dismiss()
        }

        customDialog.show()

    }
}


