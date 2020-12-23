// MODULE: lib_version1
// FILE: A.kt

fun foo() = "global"

class X {
    fun foo() = "member"
}

// MODULE: lib_version2
// FILE: A.kt

inline fun foo() = "inline global"

class X {
    inline fun foo() = "inline member"
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt
fun lib(): String = when {
    foo() != "inline global" -> "fail 1"
    X().foo() != "inline member" -> "fail 2"
    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

