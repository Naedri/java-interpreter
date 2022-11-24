package Parser.TypingErrorP;

public class VariableNotFound extends TypeError {
    public String name;

    public VariableNotFound(String name) {
        this.name = name;
    }
}
