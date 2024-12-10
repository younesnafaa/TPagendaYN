package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Termination {
    private LocalDate terminationDate;
    private long occurrences;

    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        this.terminationDate = terminationInclusive;
        this.occurrences = frequency.between(start, terminationInclusive) + 1;
    }

    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        this.terminationDate = start.plus(numberOfOccurrences - 1, frequency);
        this.occurrences = numberOfOccurrences;
    }

    public long numberOfOccurrences() {
        return occurrences;
    }

    public LocalDate terminationDateInclusive() {
        return terminationDate;
    }
}
