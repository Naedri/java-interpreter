import Parser.DefinitionP.*;
import Parser.ExpressionP.CreateObject;
import Parser.ExpressionP.Expr;
import Parser.ExpressionP.FieldAccess;
import Parser.ExpressionP.Var;

import java.util.TreeSet;

public class main {

    public static void TestObject() {
        ClassDeclaration classDeclaration = new ClassDeclaration(new Constructor("SimpleObject"));
        C simpleObject = new C("SimpleObject", classDeclaration);
        System.out.println(simpleObject);
    }

    public static void TestCounter() {
        Field param = new Field(new Type("int"), "count");
        TreeSet<Field> params = new TreeSet<Field>(new Comparators.FieldComparator());
        params.add(param);

        InitiatedField initField = new InitiatedField("count", "count");
        TreeSet<InitiatedField> initFields = new TreeSet<InitiatedField>(new Comparators.InitiatedFieldComparator()); //Arrays.asList(initField);
        initFields.add(initField);

        Constructor constructor = new Constructor("Counter", params, initFields);//in Java name of the constructor should be the same than the class
        ClassDeclaration declaration = new ClassDeclaration(params, constructor);
        C objetCounter = new C("Counter", declaration);
        System.out.println(objetCounter);
    }

    public static void TestPairInitial() {
        class A {
            A() {
                super();
            }
        }
        class B {
            B() {
                super();
            }
        }
        class Pair {
            final Object fst;
            final Object snd;

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

    public static void TestPair() {
        C objectA = new C("A", new ClassDeclaration(new Constructor("kA")));
        C objectB = new C("B", new ClassDeclaration(new Constructor("kB")));

        Field param1 = new Field(new Type("Object"), "fst");
        Field param2 = new Field(new Type("Object"), "snd");
        TreeSet<Field> params = new TreeSet<>(new Comparators.FieldComparator()); //Arrays.asList(param1, param2);
        params.add(param1);
        params.add(param2);
        InitiatedField initField1 = new InitiatedField("fst", "fst");
        InitiatedField initField2 = new InitiatedField("snd", "snd");
        TreeSet<InitiatedField> initFields = new TreeSet<>(new Comparators.InitiatedFieldComparator()); //Arrays.asList(initField1, initField2);
        initFields.add(initField1);
        initFields.add(initField2);
        Constructor constructor = new Constructor("kPair", params, initFields);

        TreeSet<Expr> bodyParams = new TreeSet<>(new Comparators.ExprComparator());
        bodyParams.add(new Var("newfst"));
        bodyParams.add(new FieldAccess(new Var("this"), "snd"));
        Expr body = new CreateObject("Pair", bodyParams);
        //Expr body = new CreateObject("Pair", Arrays.asList(new Var("newfst"), new FieldAccess(new Var("this"), "snd")));

        //Definition.Method setfst = new Method(new Signature(new Type("Pair"), "setfst", Arrays.asList(new Field(new Type("Object"), "newfst"))), body);
        TreeSet<Field> treeSetSetFST = new TreeSet<Field>(new Comparators.FieldComparator());
        treeSetSetFST.add(new Field(new Type("Object"), "newfst"));
        Method setfst = new Method(new Signature(new Type("Pair"), "setfst", treeSetSetFST), body);
        TreeSet<Method> methods = new TreeSet<>(new Comparators.MethodComparator());
        methods.add(setfst);
        C objectPair = new C("Pair", new ClassDeclaration(new TreeSet<Field>(new Comparators.FieldComparator()) {
        }, new Constructor("kB"), methods));

        System.out.println(objectPair);
    }

    public static void main(String[] args) {
        try {
            TestObject();
            TestCounter();
            TestPairInitial();
            TestPair();
            System.out.println("OK");
        } catch (Error e) {
            System.err.println(e);
        }
    }
}


