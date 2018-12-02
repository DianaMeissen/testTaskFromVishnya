This app intended for finding tag with specific id into .html files, compare and find common tags (with 3 matches).

Was created 2 classes Main and FindTag.
Main class is takes arguments from console/terminal and calling FindTag's method with them.
FindTag class tekes arguments from the Main class. It can check arguments which are paths to folder with origin file and to folder with samples, parse every .html file, take element with needed id and output parent's tag to it. Also it compares element with needed id and elements in .html files from second path.

After executing in folder testTaskFromVishnya will apear file log_file.log with results.

To use this app you need to open console/terminal and type there:
java -jar <your_bundled_app>.jar <input_origin_file_path> <input_other_sample_file_path>

.jar file is located into /testTaskFromVishnya/target/
.html files is in /testTaskFromVishnya/src/main/resources/ 
