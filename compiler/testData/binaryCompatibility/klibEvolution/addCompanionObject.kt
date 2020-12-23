// MODULE: lib_version1
// FILE: A.kt

class X {
    fun foo() = "without companion"
}


// MODULE: lib_version2
// FILE: B.kt

class X {
    fun foo() = "with companion"

    companion object {
        val bar = "this is a companion"
    }
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String = when {
    X().foo() != "with companion" -> "fail 1"
    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

