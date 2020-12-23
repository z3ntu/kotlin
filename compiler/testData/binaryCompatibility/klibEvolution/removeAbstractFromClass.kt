// MODULE: lib_version1
// FILE: A.kt

abstract class X {
    fun foo(): String = "in abstract class"
    val bar: String = "in abstract class"
}

// MODULE: lib_version2
// FILE: B.kt

open class X {
    fun foo(): String = "in non-abstract class"
    val bar: String = "in non-abstract class"
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

class Y: X() 

fun lib(): String = when {
    Y().foo() != "in non-abstract class" -> "fail 1"
    Y().bar != "in non-abstract class" -> "fail 2"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

