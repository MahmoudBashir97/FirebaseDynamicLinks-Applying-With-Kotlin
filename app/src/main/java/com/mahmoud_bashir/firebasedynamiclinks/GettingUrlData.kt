package com.mahmoud_bashir.firebasedynamiclinks

class GettingUrlData(private val link:String) {

    fun getId():String? {
        return if (link.contains("id")) {
            link.toString().split("id=")[1].split("?")[0]
        } else null
    }

    fun getType():String? {
        return if (link.contains("type")){
            link.toString().split("type=")[1]
        }else null
    }
}