// MODULE: lib_version1
// FILE: A.kt

enum class X {
    Y,
    Z
}

// MODULE: lib_version2
// FILE: B.kt

enum class X {
    Y,
    Z,
    W
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String = when {
    X.values().map { it.name }.joinToString(", ") != "Y, Z, W" -> "fail 1"
    X.valueOf("W").name != "W" -> "fail 2"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

