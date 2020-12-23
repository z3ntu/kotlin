// MODULE: lib_version1
// FILE: A.kt

abstract class X {
    abstract fun foo(): String
    abstract val bar: String
    
    fun qux() = "nothing"
}

// MODULE: lib_version2
// FILE: B.kt

abstract class X {
    fun foo(): String = "now with a body"
    val bar: String = "now with a body"
    fun qux() = foo() + bar
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

class Y: X() {
    override fun foo() = "derived body"
    override val bar = "derived body"
}

fun lib(): String {
    val y = Y()
    return when {
        y.foo() != "derived body" -> "fail 1"
        y.bar != "derived body" -> "fail 2"
        y.qux() != "now with a bodynow with a body" -> "fail 3"

        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

