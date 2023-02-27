package com.practice.baseadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class UserbaseAdapter(var userList: ArrayList<UserModel>, var list: ClickList):BaseAdapter() {
    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(p0: Int): Any {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return 1
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val view= LayoutInflater.from(parent?.context).inflate(R.layout.item_user,parent,false)
        var tvName= view.findViewById<TextView>(R.id.tvName)
        var tvRollno= view.findViewById<TextView>(R.id.tvRollNo)
         tvName.text=userList[position].name
        tvRollno.text=userList[position].rollno.toString()

        view.setOnClickListener{
            list.listClicked(position)
        }



        return view
    }
}


