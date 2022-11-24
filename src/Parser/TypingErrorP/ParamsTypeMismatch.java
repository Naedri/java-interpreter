package Parser.TypingErrorP;

public class ParamsTypeMismatch extends TypeError {
    public MismatchType[] params;

    public ParamsTypeMismatch(MismatchType[] params) {
        this.params = params;
    }
}
