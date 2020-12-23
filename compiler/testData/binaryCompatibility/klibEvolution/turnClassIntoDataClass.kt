// MODULE: lib_version1
// FILE: A.kt

interface A {
    fun foo(): String
    val bar: String
}

class X(val a: String, var b: String) {
    fun foo(): String = "original class' method"
    val bar: String = "original class' property"
}

// MODULE: lib_version2
// FILE: B.kt

interface A {
    fun foo(): String
    val bar: String
}

data class X(val a: String, var b: String) {
    fun foo(): String = a + b
    val bar: String = b + a
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    val x = X("first", "second")
    return when {
        x.a != "first" -> "fail 1"
        x.b != "second" -> "fail 2"
        x.foo() != "firstsecond" -> "fail 3"
        x.bar != "secondfirst" -> "fail 4"
        x.toString() != "X(a=first, b=second)" -> "fail 5"
        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

