import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class AppState(
    var lastScannedPath: String = "",
    var includeSubfolders: Boolean = true,
    var scannedFiles: List<FileData> = emptyList(),
    var categorizedFiles: Map<String, List<FileData>> = emptyMap(),
    var userApprovedFiles: List<FileData> = emptyList(),
    var deletedFiles: List<FileData> = emptyList(),
    var lastScanTime: Instant? = null,
    var changesPending: Boolean = false
) {
    fun updateAfterScan(path: String, files: List<FileData>) {
        lastScannedPath = path
        scannedFiles = files
        lastScanTime = Instant.now()
        categorizedFiles = emptyMap()
        userApprovedFiles = emptyList()
        deletedFiles = emptyList()
        changesPending = false
    }

    fun applyAnalysis(categorized: Map<String, List<FileData>>) {
        categorizedFiles = categorized
        changesPending = true
    }

    fun recordDeletion(deleted: List<FileData>) {
        deletedFiles = deleted
        changesPending = false
    }

    fun printSummary() {
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a")
        val lastScanStr = lastScanTime
            ?.atZone(ZoneId.systemDefault())
            ?.format(formatter)
            ?: "Never"
        println("=== Scan Summary ===")
        println("Scanned Path: $lastScannedPath")
        println("Total Files: ${scannedFiles.size}")
        println("Old Files: ${categorizedFiles["Old Files"]?.size ?: 0}")
        println("Temporary Files: ${categorizedFiles["Temporary Files"]?.size ?: 0}")
        println("Deleted Files: ${deletedFiles.size}")
        println("Last Scan Time: $lastScanStr")
        println("=====================")
    }
}
