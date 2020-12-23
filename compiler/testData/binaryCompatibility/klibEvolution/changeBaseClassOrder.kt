// MODULE: base
// FILE: base.kt

open class X {
    open val bar: String 
        get() = "base class open"
    val zon: String 
        get() = "base class"
}

interface Y {
    val qux: String 
        get() = "base interface Y"
}

interface Z {
    val sep: String 
        get() = "base interface Z"
}

// MODULE: lib_version1(base)
// FILE: A.kt

class W : Y, Z, X() {
    override val bar: String
        get() = "from base class"
    override val qux: String
        get() = "from interface Y"
    override val sep: String
        get() = "from interface Z"
}

// MODULE: lib_version2(base)
// FILE: B.kt

class W : X(), Z, Y {
    override val bar: String
        get() = "from base class"
    override val qux: String
        get() = "from interface Y"
    override val sep: String
        get() = "from interface Z"
}

// MODULE: mainLib(lib_version1, lib_version2)
// FILE: mainLib.kt

fun lib(): String {
    val w = W()
    return when {
        w.bar != "from base class" -> "fail 1"
        w.zon != "base class" -> "fail 2"
        w.qux != "from interface Y" -> "fail 3"
        w.sep != "from interface Z" -> "fail 4"
        else -> "OK"
    }
}

// MODULE: main(mainLib)
// FILE: main.kt
fun box(): String = lib()

