package fr.davidpelissier.adventofcode2022

import fr.davidpelissier.adventofcode2022.InputReader.readAsList
import fr.davidpelissier.adventofcode2022.InputReader.readAsString
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class InputReaderTest {

    @Test
    fun `should return string`() {
        assertThat(readAsString(1)).isEqualTo(listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000"
        ).joinToString(SEPARATOR))
    }

    @Test
    fun `should return lines`() {
        assertThat(readAsList(1)).isEqualTo(listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000"
        ))
    }

    companion object {
        const val SEPARATOR = "\n"
    }
}