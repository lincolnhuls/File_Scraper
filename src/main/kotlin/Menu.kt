class Menu {
    fun defaultPaths() {
        val home = System.getProperty("user.home")
        val downloads = "$home/Downloads"
        val documents = "$home/Documents"
        val desktop = "$home/Desktop"
        println("Default Paths: \nDownloads: $downloads \nDocuments: $documents \nDesktop: $desktop")
    }

    fun start() {
        var again = true
        var input = ""
        val appState = AppState()
        val scanner = FileScanner()
        val manager = CleanupManager(maxFileDays = 30)

        while (again) {
            println("File Cleanup")
            println("Please select an option: ")
            println("1. Scan Directory")
            println("2. Analyze Files")
            println("3. Review and Delete Files")
            println("4. View Summary")
            println("5. Exit")
            input = readLine()?.trim() ?: ""

            when (input) {
                "1" -> {
                    println("This will add directory files to the list, you must then analyze and approve files for deletion.")
                    defaultPaths()
                    println("Please enter a directory path to scan:")
                    val path = readLine()?.trim() ?: ""
                    if (path.isNotBlank()) {
                        val files = scanner.scanDirectory(path, includeSubfolders = true)
                        appState.updateAfterScan(path, files)
                        println("Scan complete. ${files.size} files found.")
                    }
                }

                "2" -> {
                    println("Please enter how long (in days) a file must be unused to be considered old (default 30):")
                    val daysInput = readLine()?.trim()
                    val days = daysInput?.toLongOrNull() ?: 30L
                    manager.maxFileDays = days
                    println("Analyzing files...")
                    val categorized = manager.allFilesForDeletion(appState.scannedFiles)
                    appState.applyAnalysis(categorized)
                    println("Analysis complete. Categories found:")
                    for ((category, files) in categorized) {
                        println("$category: ${files.size} files")
                    }
                }

                "3" -> {
                    if (appState.categorizedFiles.isEmpty()) {
                        println("No analyzed files to review. Please analyze files first.")
                    } else {
                        println("Reviewing files for deletion:")
                        val filesToReview = appState.categorizedFiles.flatMap { it.value }
                        println("Approve files for deletion? (y/n): ")
                        val decision = readLine()?.trim()
                        if (decision?.equals("y", ignoreCase = true) == true) {
                            for (file in filesToReview) {
                                file.userApproved = true
                            }
                        } else {
                            for (file in filesToReview) {
                                file.userApproved = false
                            }
                        }

                        val filesToDelete = filesToReview.filter { it.userApproved == true }
                        val deletedCount = manager.deleteFiles(filesToDelete)
                        appState.recordDeletion(filesToDelete)
                        println("Deletion complete. $deletedCount files deleted.")
                    }
                }

                "4" -> {
                    appState.printSummary()
                }

                "5" -> {
                    println("Exiting...")
                    again = false
                }
            }
        }
    }
}
