package Parser.TypingErrorP;

public class FieldNotFound extends TypeError {
    public String name;

    public FieldNotFound(String name) {
        this.name = name;
    }
}
