package assignment1;

public class Post {
    private static int nextPk = 1;
    private final int pk;
    private final String title;
    private final String content;

    public Post(String title, String content) {
        this.pk = nextPk++;
        this.title = title;
        this.content = content;
    }
} 