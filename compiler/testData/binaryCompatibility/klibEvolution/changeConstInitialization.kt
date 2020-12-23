// MODULE: lib_version1
// FILE: A.kt

const val bar = 17
const val muc = "first"

object X {
    const val tis = "second"
    const val roo = 19
}

class Y {
    companion object {
        const val zeb = 23
        const val loo = "third"
    }
}

// MODULE: lib_version2
// FILE: B.kt

const val bar = 31
const val muc = "fourth"

object X {
    const val tis = "fifth"
    const val roo = 37
}

class Y {
    companion object {
        const val zeb = 41
        const val loo = "sixth"
    }
}


// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt
fun lib(): String = when {
    bar != 31 -> "fail 1"
    muc != "fourth" -> "fail 2"
    X.tis != "fifth" -> "fail 3"
    X.roo != 37 -> "fail 4"
    Y.zeb != 41 -> "fail 5"
    Y.loo != "sixth" -> "fail 6"

    else -> "OK"
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

