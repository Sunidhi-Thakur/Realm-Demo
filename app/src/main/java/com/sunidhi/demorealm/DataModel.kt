package com.sunidhi.demorealm

import io.realm.RealmObject

open class DataModel : RealmObject() {
    var id = 0
    var name: String? = null
}