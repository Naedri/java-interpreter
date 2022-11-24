package Parser.DefinitionP;

/**
 * To define object from the paper
 */
public class Definition {

    private static Definition instance;

    private Definition() {
    }

    public static Definition getInstance() {
        if (instance == null) {
            instance = new Definition();
        }

        return instance;
    }


    /*********************Lambda syntactic constructors***************************************/

    /*********************String name wrappers***************************************/


    /*********************Lambda Auxiliary definitions***************************************/


    /*********************Lambda nominal typing***************************************/

}




