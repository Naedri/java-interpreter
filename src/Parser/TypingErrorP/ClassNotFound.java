package Parser.TypingErrorP;

public class ClassNotFound extends TypeError {
    public String name;

    public ClassNotFound(String name) {
        this.name = name;
    }
}
