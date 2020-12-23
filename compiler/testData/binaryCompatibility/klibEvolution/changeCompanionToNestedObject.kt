// MODULE: lib_version1
// FILE: A.kt

open class N {
    fun bar() = "something in N"
}

class X {
    fun foo() = "with companion"

    companion object : N() {
        val qux = "this is in companion object"
    }
}

// MODULE: lib_version2
// FILE: B.kt

open class N {
    fun bar() = "something in N"
}

class X {
    fun foo() = "without companion"

    object Companion : N() {
        val qux = "this is in object"
    }

}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String = when {
    X.qux != "this is in object" -> "fail 1"
    X.bar() != "something in N" -> "fail 2"
    X().foo() != "without companion" -> "fail 3"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

