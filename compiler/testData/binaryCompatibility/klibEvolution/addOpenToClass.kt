// MODULE: lib_version1
// FILE: A.kt

class X {
    fun foo(): String = "in final class"
    val bar: String = "in final class"
}

fun qux(): X = X()

// MODULE: lib_version2
// FILE: B.kt

open class X {
    fun foo(): String = "in open class"
    val bar: String = "in open class"
}

class Y: X()

fun qux(): X = Y()

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String = when {
    X().foo() != "in open class" -> "fail 1"
    X().bar != "in open class" -> "fail 2"
    qux().foo() != "in open class" -> "fail 3"
    qux().bar != "in open class" -> "fail 4"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

