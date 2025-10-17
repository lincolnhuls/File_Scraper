import java.io.File
import java.nio.file.Files

class FileScanner {
    fun scanDirectory(path: String, includeSubfolders: Boolean = true): List<FileData> {
        val root = File(path)
        val fileList = mutableListOf<FileData>()

        if (!root.exists() || !root.isDirectory) {
            println("Invalid directory: $path")
            return emptyList()
        }

        val files = if (includeSubfolders) root.walk() else root.walkTopDown().maxDepth(1)
        for (file in files) {
            if (file.isFile) {
                val attrs = Files.readAttributes(file.toPath(), java.nio.file.attribute.BasicFileAttributes::class.java)
                val ext = file.extension
                fileList.add(
                    FileData(
                        filePath = file.absolutePath,
                        name = file.name,
                        extension = ".${ext}",
                        size = file.length(),
                        createdTime = attrs.creationTime().toInstant(),
                        lastModifiedTime = attrs.lastModifiedTime().toInstant(),
                        lastAccessedTime = attrs.lastAccessTime().toInstant()
                    )
                )
            }
        }
        println("✅ Scanned ${fileList.size} files from $path")
        return fileList
    }

    private fun getCategory(extension: String): String {
        return when (extension.lowercase()) {
            "txt", "pdf", "docx" -> "Documents"
            "jpg", "png", "gif" -> "Images"
            "mp3", "wav" -> "Audio"
            "mp4", "mov" -> "Video"
            "zip", "rar" -> "Archives"
            "exe", "dll", "sys" -> "System"
            "tmp", "log", "bak" -> "Temporary"
            else -> "Other"
        }
    }
}
