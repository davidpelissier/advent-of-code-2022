package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day
import fr.davidpelissier.adventofcode2022.days.Day07.CommandType.CD_IN
import fr.davidpelissier.adventofcode2022.days.Day07.CommandType.CD_UP
import fr.davidpelissier.adventofcode2022.days.Day07.CommandType.LS

object Day07 : Day(7, "No Space Left On Device") {

    override fun part1(): Int {
        val root = Directory("/", null)
        val commands = parseCommands(inputList).drop(1)
        createTree(commands, root)
        return directoryRegistry.map { it.getSize() }.filter { it <= 100000 }.sum()
    }

    override fun part2(): Int {
        val root = Directory("/", null)
        val commands = parseCommands(inputList).drop(1)
        createTree(commands, root)
        val directorySizes = directoryRegistry.map { it.getSize() }
        val spaceRequired = 30000000 - (70000000 - root.getSize())
        return directorySizes.filter { it > spaceRequired }.min()
    }

    private val directoryRegistry = mutableSetOf<Directory>()

    enum class CommandType { CD_IN, CD_UP, LS }

    data class Command(val type: CommandType, val directoryName: String = "", val output: List<String> = emptyList())

    data class File(val name: String, val size:Int)

    class Directory(private val name : String, val parent : Directory?) {

        private lateinit var directories : List<Directory>

        private lateinit var files : List<File>

        fun create(contents : List<String>) {
            directories = contents
                .map { it.split(" ") }
                .filter { it[0] == "dir" }
                .map { Directory(it[1], this) }
            files = contents
                .map { it.split(" ") }
                .filter { it[0].toIntOrNull() != null }
                .map { File(it[1], it[0].toInt()) }
        }

        fun navigateTo(directoryName: String) : Directory {
            return directories.find { directoryName == it.name }!!
        }

        fun getSize(): Int {
            return files.sumOf { it.size } + directories.sumOf {it.getSize()}
        }
    }

    private fun parseCommands(lines: List<String>): List<Command> {
        val commands = mutableListOf<Command>()
        lines.forEachIndexed { index, line ->
            when {
                line.startsWith("$ cd") && line.endsWith("..") -> commands.add(Command(CD_UP))
                line.startsWith("$ cd") && !line.endsWith("..") -> commands.add(Command(CD_IN, directoryName = line.drop(5)))
                line.startsWith("$ ls") -> commands.add(Command(LS, output = lines.drop(index + 1).takeWhile { l -> l.first() != '$' }))
            }
        }
        return commands
    }

    private fun createTree(commands: List<Command>, current: Directory) {
        if (commands.isEmpty()) return
        val command = commands.first()
        val remainingCommands = commands.drop(1)
        return when (command.type) {
            CD_UP -> createTree(remainingCommands, current.parent!!)
            CD_IN -> createTree(remainingCommands, current.navigateTo(command.directoryName))
            LS -> { current.create(command.output); directoryRegistry.add(current); createTree(remainingCommands, current) }
        }
    }
}