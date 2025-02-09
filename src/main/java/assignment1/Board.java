package assignment1;

public class Board {
    private static int nextPk = 1;
    private final int pk;
    private final String name;

    Board(String name) {
        this.pk = nextPk++;
        this.name = name;
    }

    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }
}
