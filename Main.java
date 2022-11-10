/*
* This program uses the bell curve to generate marks.
*
* @author  Sydney Kuhn
* @version 1.0
* @since   2022-11-09
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
* This is the marks program.
*/
final class Main {

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Main() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The generateMarks() function.
    *
    * @param arrayOfStudents the collection of students
    * @param arrayOfAssignments the collection of assignments
    */

    public static void generateMarks(final ArrayList<String> arrayOfStudents,
                                       final ArrayList<String> arrayOfAssignments) {

        final Random random = new Random();

        // Get array lengths
        final int studSize = arrayOfStudents.size();
        final int assSize = arrayOfAssignments.size();

        // Creates the table array
        final ArrayList<ArrayList<String>> table =
                new ArrayList<ArrayList<String>>();

        // Format table
        final ArrayList<String> topRow = new ArrayList<String>();
        topRow.add(" ");
        for (int counter = 0; counter < assSize; counter++) {
            topRow.add(arrayOfAssignments.get(counter));
        }

        // Add empty row
        table.add(topRow);

        // Generate random marks
        for (int studCounter = 0; studCounter < studSize; studCounter++) {
            final ArrayList<String> studRow = new ArrayList<String>();
            studRow.add(arrayOfStudents.get(studCounter));
            for (int assCounter = 0; assCounter < assSize; assCounter++) {
                final int mark = (int) Math.floor(random.nextGaussian()
                                  * 10 + 75);
                studRow.add(String.valueOf(mark));
            }
            // Add to table
            table.add(studRow);
        }

        // Create CSV file
        final String fileName = "marks.csv";
        final File file = new File(fileName);

        // Format marks into coloums and rows
        try {
            final FileWriter fileWriter = new FileWriter(fileName, false);

            for (int row = 0; row < table.size(); row++) {
                final String line = String.join(",", table.get(row)) + ",\n";
                System.out.println(line);
                fileWriter.write(line);
            }
            fileWriter.close();
        } catch (IOException error) {
            System.out.println("Error Occurred, Please Try Again.");
            error.printStackTrace();
        }
    }

    /**
    * The starting main() function.
    *
    * @param args No args will be used
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssignments = new ArrayList<String>();
        final Path studentsFilePath = Paths.get(args[0]);
        final Path assignmentsFilePath = Paths.get(args[1]);
        final Charset charset = Charset.forName("UTF-8");

        // Read file
        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentsFilePath, charset)) {
            String lineStudent = null;
            while ((lineStudent = readerStudent.readLine()) != null) {
                listOfStudents.add(lineStudent);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        // Read file
        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentsFilePath, charset)) {
            String lineAssignment = null;
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssignments.add(lineAssignment);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        // Process
        generateMarks(listOfStudents, listOfAssignments);

        // Output
        System.out.println("\nDone.");
    }
}
