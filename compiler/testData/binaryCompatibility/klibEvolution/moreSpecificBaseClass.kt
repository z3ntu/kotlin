// MODULE: base
// FILE: base.kt

open class X {
    open val bar: String = "base class"
}

open class Y: X() {
    override val bar: String = "child class"
}

// MODULE: lib_version1(base)
// FILE: A.kt

class Z : X() 

// MODULE: lib_version2(base)
// FILE: B.kt

class Z : Y() 

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    return when {
        Z().bar != "child class" -> "fail 1"
        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

