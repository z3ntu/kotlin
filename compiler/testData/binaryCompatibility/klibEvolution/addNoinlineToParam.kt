// MODULE: lib_version1
// FILE: A.kt

inline fun foo(x: () -> String) =
    x().reversed()

// MODULE: lib_version2
// FILE: B.kt


inline fun foo(noinline x: () -> String) =
    x().reversed()

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String = when {
    foo { "pajamas" } != "samajap" -> "fail 1"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

