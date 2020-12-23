// MODULE: lib_version1
// FILE: A.kt

inline fun foo() = "foo before change"

class X {
    inline fun bar() = "bar before change"
}

// MODULE: lib_version2
// FILE: A.kt

inline fun foo() = "foo after change"

class X {
    inline fun bar() = "bar after change"
}


// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt
fun lib(): String = when {
    foo() != "foo after change" -> "fail 1"
    X().bar() != "bar after change" -> "fail 2"
    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

