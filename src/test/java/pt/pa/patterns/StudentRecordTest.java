package pt.pa.patterns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/** Regression tests for the initial version, before applying Strategy. */
class StudentRecordTest {
    @TempDir Path directory;

    private Path createRecord() throws IOException {
        Path file = directory.resolve("record.csv");
        Files.writeString(file, "YEAR,COURSE,COURSE_ID,SEMESTER,ECTS,GRADE\n"
                + "1,Programming,UC1,1S,6,10\n"
                + "1,Mathematics,UC2,1S,3,16\n", StandardCharsets.UTF_8);
        return file;
    }

    @Test void preservesStudentIdentity() {
        StudentRecord record = new StudentRecord("123", "Alex");
        assertEquals("123", record.getStudentId());
        assertEquals("Alex", record.getStudentName());
    }

    @Test void computesArithmeticAverageAfterImport() throws IOException {
        StudentRecord record = new StudentRecord("123", "Alex");
        record.importFromFile(createRecord().toString());
        assertEquals(13.0, record.computeAverage(), 1e-9);
    }

    @Test void includesCoursesAndStudentInListing() throws IOException {
        StudentRecord record = new StudentRecord("123", "Alex");
        record.importFromFile(createRecord().toString());
        String listing = record.toString();
        assertTrue(listing.contains("Alex"));
        assertTrue(listing.contains("UC1"));
        assertTrue(listing.contains("Mathematics"));
    }

    @Test void rejectsAverageOfEmptyRecord() {
        StudentRecord record = new StudentRecord("123", "Alex");
        assertThrows(IllegalStateException.class, record::computeAverage);
    }

    @Test void reportsMissingFile() {
        StudentRecord record = new StudentRecord("123", "Alex");
        assertThrows(IOException.class,
                () -> record.importFromFile(directory.resolve("missing.csv").toString()));
    }
}
