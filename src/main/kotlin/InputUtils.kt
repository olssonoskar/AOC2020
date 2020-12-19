import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.min

internal object InputUtils {

    private fun getFromResources(file : String) : File {
        val uri = InputUtils.javaClass.classLoader.getResource (file)?.toURI()
                ?: throw IllegalArgumentException("File was not found")
        return File(uri)
    }

    fun getLines(file: String) : List<String> {
        return getFromResources(file).readLines().toCollection(arrayListOf())
    }

    fun getString(file : String) : String {
        return getFromResources(file).readText()
    }

    fun getStrings(file : String, separator : String = "\n") : List<String> {
        return getString(file).split(separator)
    }

    fun getInts(file: String) : List<Int> {
        return getStrings(file).map { it.trim().toInt() }
    }

    fun getLongs(file: String) : List<Long> {
        return getStrings(file).map { it.trim().toLong() }
    }

    fun equallySplitList(input : String, length : Int = 1, isFile : Boolean = true) : List<String> {
        val content = if (isFile) getString(input) else input
        val result = mutableListOf<String>()
        for (i in content.indices) {
            result.add(content.substring(i, min((i + length), content.length)))
        }
        return result
    }

}