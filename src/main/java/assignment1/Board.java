package assignment1;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static int nextPk = 1;
    private final int pk;
    private final String name;
    private final List<Post> posts;

    Board(String name) {
        this.pk = nextPk++;
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public int getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }
}
