import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit

class CleanupManager(
    var maxFileDays: Long = 90,
) {
    fun findOldFiles(files: List<FileData>): List<FileData> {
        val oldFiles = mutableListOf<FileData>()
        val currentTime = Instant.now()
        for (file in files) {
            val lastAccessed = file.lastAccessedTime
            val daysSinceAccess = ChronoUnit.DAYS.between(lastAccessed, currentTime)
            val lastModified = file.lastModifiedTime
            val daysSinceModified = ChronoUnit.DAYS.between(lastModified, currentTime)
            if (daysSinceAccess > maxFileDays && daysSinceModified > maxFileDays) {
                file.markedForDeletion = true
                oldFiles.add(file)
            }
        }
        return oldFiles
    }

    fun findTempFiles(files: List<FileData>): List<FileData> {
        val tempFiles = mutableListOf<FileData>()
        val tempExtensions = listOf("tmp", "log", "bak")
        for (file in files) {
            if (tempExtensions.contains(file.extension.lowercase())) {
                file.markedForDeletion = true
                tempFiles.add(file)
            }
        }
        return tempFiles
    }

    fun deleteFiles(files: List<FileData>): Int {
        var deleted = 0
        for (fileData in files) {
            val file = File(fileData.filePath)
            if (file.exists() && fileData.userApproved == true) {
                file.delete()
                deleted++
                println("Deleted: ${fileData.filePath}")
            }
        }
        return deleted
    }

    fun allFilesForDeletion(files: List<FileData>): Map<String, List<FileData>> {
        return mapOf(
            "Old Files" to findOldFiles(files),
            "Temporary Files" to findTempFiles(files)
        )
    }
}