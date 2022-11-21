import Parser.Definition;
import Parser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    public static void TestCounter(Definition defP, Expression expP) {
        Definition.Field param = defP.new Field(defP.new Type("int"), "count");
        List<Definition.Field> params = Arrays.asList(param);
        Definition.InitiatedField initField = defP.new InitiatedField("count", "count");
        List<Definition.InitiatedField> initFields = Arrays.asList(initField);
        Definition.Constructor constructor = defP.new Constructor("kCounter", params, initFields);
        Definition.C objetCounter = defP.new C("Counter", defP.new ClassDeclaration(Arrays.asList(param), constructor));

        System.out.println(objetCounter);
    }

    public static void TestPairInitial() {
        class A extends Object {
            A() {
                super();
            }
        }
        class B extends Object {
            B() {
                super();
            }
        }
        class Pair extends Object {
            Object fst;
            Object snd;

            Pair(Object fst, Object snd) {
                super();
                this.fst = fst;
                this.snd = snd;
            }

            Pair setfst(Object newfst) {
                return new Pair(newfst, this.snd);
            }
        }
        Pair pair = new Pair(new A(), new B()).setfst(new B());

        System.out.println(pair);
    }

    public static void TestPair(Definition defP, Expression expP) {
        Definition.C objectA = defP.new C("A", defP.new ClassDeclaration(defP.new Constructor("kA")));
        Definition.C objectB = defP.new C("B", defP.new ClassDeclaration(defP.new Constructor("kB")));

        Definition.Field param1 = defP.new Field(defP.new Type("Object"), "fst");
        Definition.Field param2 = defP.new Field(defP.new Type("Object"), "snd");
        List<Definition.Field> params = Arrays.asList(param1, param2);
        Definition.InitiatedField initField1 = defP.new InitiatedField("fst", "fst");
        Definition.InitiatedField initField2 = defP.new InitiatedField("snd", "snd");
        List<Definition.InitiatedField> initFields = Arrays.asList(initField1, initField2);
        Definition.Constructor constructor = defP.new Constructor("kPair", params, initFields);

        Expression.Expr body = expP.new CreateObject("Pair", Arrays.asList(expP.new Var("newfst"), expP.new FieldAccess(expP.new Var("this"), "snd")));

        Definition.Method setfst = defP.new Method(defP.new Signature(defP.new Type("Pair"), "setfst", Arrays.asList(defP.new Field(defP.new Type("Object"), "newfst"))), body);
        List<Definition.Method> methods = Arrays.asList(setfst);
        Definition.C objectPair = defP.new C("Pair", defP.new ClassDeclaration(new ArrayList<Definition.Field>() {
        }, defP.new Constructor("kB"), methods));

        System.out.println(objectPair);
    }

    public static void main(String[] args) {
        Definition defP = Definition.getInstance();
        Expression expP = new Expression();
        try {
            TestCounter(defP, expP);
            TestPairInitial();
            TestPair(defP, expP);
            System.out.println("OK");
        } catch (Error e) {
            System.err.println(e);
        }
    }
}


