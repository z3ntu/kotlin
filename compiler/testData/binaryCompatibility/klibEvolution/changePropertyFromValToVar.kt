// MODULE: lib_version1
// FILE: A.kt

val bar = "global val initialization value"

class X {
    val qux = "member val initialization value"
}

fun foo(x: X) {
    // DO NOTHING
}

// MODULE: lib_version2
// FILE: B.kt

var bar = "global var initialization value"

class X {
    var qux = "member var initialization value"
}

fun foo(x: X) {
    bar = "changed global value"
    x.qux = "changed member value"
    
}


// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt
fun lib(): String {
    val x = X()
    foo(x)

    return when {
        bar != "changed global value" -> "fail 1"
        x.qux != "changed member value" -> "fail 2"

        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

