import java.time.Instant

data class FileData(
    val filePath: String,
    val name: String,
    val extension: String,
    val size: Long,
    val createdTime: Instant,
    val lastModifiedTime: Instant,
    val lastAccessedTime: Instant,
    var markedForDeletion: Boolean = false,
    var userApproved: Boolean? = null
)
