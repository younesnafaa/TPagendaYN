package agenda;


import java.time.temporal.ChronoUnit;

public class Repetition {
    private ChronoUnit myFrequency;

    public Repetition(ChronoUnit frequency) {
        this.myFrequency = frequency;
    }

    public ChronoUnit getFrequency() {
        return myFrequency;
    }
}
