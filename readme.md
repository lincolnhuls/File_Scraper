# Kotlin File Scaper

I wrote a file scaper to work through files on my computer and create lists of files that were older than a certian date, hadn't
been opened in a certian period of time, or were a temporary file. The program has a menu that allows the user to manually select which
folders the program would scan, and once the scan was done, it would return a list of files to the user. The user can then choose to approve
the detletion of those files or cancel the operation. The time for an old file can also be set by the user.

## Instructions for Build and Use

Steps to build and/or run the software:

1. Install IntelliJ IDEA community edition
2. Open the project in the editor
3. Click the green run button at the top right of the editor

Instructions for using the software:

1. Once the program is running, select an option from the menu
2. Pick 1 to enter a folder path to be scanned and hit enter
3. Pick 2 to 3enter the number of days a file has to be unused to be considered old and hit enter to scan selected folders
4. Pick 3 to look over files selected for deletion and either approve or cancel the deletion
5. Pick 4 to view the summary of the path scanned along with the files found, files deleted, and last scan time
6. Pick 5 to exit the program

## Development Environment

To recreate the development environment, you need the following software and/or libraries with the specified versions:

- Dowload IntelliJ from [JetBrains](https://www.jetbrains.com/idea/download/?section=windows)
- The only needed libraries are already included Kotlin and will run with no extra setup

## Useful Websites to Learn More

I found these websites useful in developing this software:

- [Kotlin docs](https://kotlinlang.org/docs/home.html)
- [Setup VSCode for Kotlin Development](https://in-kotlin.com/ide/vscode/setup-vscode-for-kotlin-development/)
- [Kotlin Tutorial](https://www.w3schools.com/KOTLIN/index.php)
- [Java Files](https://www.w3schools.com/java/java_files.asp)

## Future Work

The following items I plan to fix, improve, and/or add to this project in the future:

- I want to add an ability to store user preferences between sessions
- I want to imporve the file approving system, right now it is just in bulk, but I want the user to be able to specify files they want to delete
- I want to add a size attribute that will show the user how much space they are freeing up with the deletions
- I want to look into how I can make the interface a bit more user friendly, like clearing the screen between selections
