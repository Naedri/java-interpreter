package Parser.TypingErrorP;

public class MethodNotFound extends TypeError {
    public String name;
    public String type;

    public MethodNotFound(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
