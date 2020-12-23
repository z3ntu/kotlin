// MODULE: lib_version1
// FILE: A.kt

sealed class X(val name: String ="X")

class Y: X("Y")

class Z: X("Z")

fun last(): X = Z()

// MODULE: lib_version2
// FILE: B.kt

sealed class X(val name: String ="X")

class Y: X("Y")

class Z: X("Z")

class W: X("W")
          
fun last(): X = W()

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    val x = last()
    return when(x) {
        is Y -> "fail 1"
        is Z -> "fail 2"

        else -> {
            if (x.name == "W")
                "OK"
            else
                "fail 3"
        }
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

