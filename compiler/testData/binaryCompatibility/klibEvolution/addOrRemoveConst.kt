// MODULE: lib_version1
// FILE: A.kt

val bar: String
    get() = "a val with a getter"

const val qux: String = "a const val"

object X {
    val zem: String
        get() = "a member val with a getter"

    const val spi: String = "a member const val"
}


// MODULE: lib_version2
// FILE: B.kt

const val bar: String = "a val turned into a const"

val qux: String 
    get() = "a const turned into a val with a getter"

object X {
    val zem: String
        get() = "a member val turned into a const"

    const val spi: String = "a member const turned into a val with a getter"
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    return when {
        bar != "a val turned into a const" -> "fail 1"
        qux != "a const turned into a val with a getter" -> "fail 2"
        X.zem != "a member val turned into a const" -> "fail 1"
        X.spi != "a member const turned into a val with a getter" -> "fail 2"

        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

