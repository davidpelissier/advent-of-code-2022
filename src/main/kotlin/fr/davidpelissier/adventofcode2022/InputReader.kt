package fr.davidpelissier.adventofcode2022

import java.io.File

object InputReader {

    fun readAsString(number: Int) = fileFromResource(number).readText()

    fun readAsList(number: Int) = fileFromResource(number).readLines()

    private fun dayFilename(number: Int) = "Day${number.toString().padStart(2, '0')}.txt"

    private fun resource(number: Int) = javaClass.classLoader.getResource(dayFilename(number))?.toURI()

    @JvmStatic
    private fun fileFromResource(number: Int) = File(resource(number) ?: error("Input for day $number not found!"))

}

