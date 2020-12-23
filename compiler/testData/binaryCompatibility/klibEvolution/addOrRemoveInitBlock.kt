// MODULE: lib_version1
// FILE: A.kt

class X {
    var x = 17
    init {
        x = 19
    }
    init {
        x = 23
    }
    init {
        x = 29
    }
    var y = 31
}

// MODULE: lib_version2
// FILE: B.kt

class X {
    init {
        // Empty
    }
    var x = 17
    var y = 31
    init {
        x = 37
        y = 41
    }
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    val x = X()
    return when {
        x.x != 37 -> "fail 1"
        x.y != 41 -> "fail 2"
        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

