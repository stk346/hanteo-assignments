package assignment1;

public class Category {
    private static int nextPk = 1;
    private final int pk;
    private final String name;

    Category(String name) {
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
