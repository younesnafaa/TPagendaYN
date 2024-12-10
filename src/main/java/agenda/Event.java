package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String myTitle;
    private LocalDateTime myStart;
    private Duration myDuration;
    private List<LocalDate> exceptions = new ArrayList<>();
    private Repetition repetition;
    private Termination termination;

    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        this.repetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        exceptions.add(date);
    }

    public void setTermination(LocalDate terminationInclusive) {
        this.termination = new Termination(myStart.toLocalDate(), repetition.getFrequency(), terminationInclusive);
    }

    public void setTermination(long numberOfOccurrences) {
        this.termination = new Termination(myStart.toLocalDate(), repetition.getFrequency(), numberOfOccurrences);
    }

    public int getNumberOfOccurrences() {
        return termination != null ? (int) termination.numberOfOccurrences() : -1;
    }

    public LocalDate getTerminationDate() {
        return termination != null ? termination.terminationDateInclusive() : null;
    }

    public boolean isInDay(LocalDate aDay) {
        LocalDate startDate = myStart.toLocalDate();
        LocalDate endDate = myStart.plus(myDuration).toLocalDate();

        if (exceptions.contains(aDay)) return false;

        if (repetition == null) {
            return !aDay.isBefore(startDate) && !aDay.isAfter(endDate);
        }

        LocalDate terminationDate = termination != null ? termination.terminationDateInclusive() : LocalDate.MAX;

        LocalDate current = startDate;
        while (!current.isAfter(terminationDate)) {
            if (aDay.equals(current)) return true;
            current = current.plus(1, repetition.getFrequency());

            if (current.isBefore(startDate)) {
                throw new IllegalStateException("Repetition frequency leads to infinite loop.");
            }
        }

        return false;
    }

    public String getTitle() {
        return myTitle;
    }

    public LocalDateTime getStart() {
        return myStart;
    }

    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}

