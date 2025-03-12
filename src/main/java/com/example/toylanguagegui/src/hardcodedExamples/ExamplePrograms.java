package com.example.toylanguagegui.src.hardcodedExamples;
import com.example.toylanguagegui.src.model.statements.*;
import com.example.toylanguagegui.src.model.types.*;
import com.example.toylanguagegui.src.model.value.*;
import com.example.toylanguagegui.src.model.expresions.*;

import static com.example.toylanguagegui.src.model.expresions.AritmeticalOperator.*;

public class ExamplePrograms {

    public static IStatement getExample1() {
        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        return statement;
    }

    public static IStatement getExample2() {
        IStatement statement =  new CompStatement(
                new VarDeclStmt("a", new IntType()),
                new CompStatement(
                        new VarDeclStmt("b", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new AritmeticalExpression(
                                        new ValueExpression(new IntValue(2)),
                                        ADD,
                                        new AritmeticalExpression(
                                                new ValueExpression(new IntValue(3)),
                                                MULTIPLY,
                                                new ValueExpression(new IntValue(5))
                                        )
                                )),
                                new CompStatement(
                                        new AssignStatement("b", new AritmeticalExpression(
                                                new VariableExpression("a"),
                                                ADD,
                                                new ValueExpression(new IntValue(1))
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        return statement;
    }
    public static IStatement getExample3() {
        IStatement statement =  new CompStatement(
                new VarDeclStmt("a", new BoolType()),
                new CompStatement(
                        new VarDeclStmt("v", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3))
                                                ),new VariableExpression("a")),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        return statement;
    }
    public static IStatement getHeapAllocExample(){
        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                                new CompStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))
                        ));
        return statement;
    }

    public static IStatement getHeapReadExample(){
        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                                new PrintStatement(
                                                        new AritmeticalExpression(
                                                                new HeapReading(new HeapReading(new VariableExpression("a"))),
                                                                ADD,
                                                                new ValueExpression(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        return statement;
    }
    public static IStatement getHeapWriteExample(){

        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                                new CompStatement(new HeapWriting("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new AritmeticalExpression(
                                                new HeapReading(new VariableExpression("v")),
                                                ADD,
                                                new ValueExpression(new IntValue(5)))))))
        );

        return statement;
    }

    public static IStatement getGarbageCollectorExample(){
        IStatement statement =  new CompStatement(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompStatement(
                                                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(5))),
                                                        new PrintStatement(new HeapReading(new HeapReading(new VariableExpression("a")))))
                                        )
                                )
                        )
                )
        );
        return statement;
    }

    public static IStatement getWhileExample(){
        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), RelationalOperator.GREATER,new ValueExpression(new IntValue(0))),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"),SUBTRACT ,new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v")) 
                        )
                )
        );
        return statement;

    }
    public static IStatement getForkExample() {
        IStatement statement = new CompStatement(
                new VarDeclStmt("v", new IntType()),
                new CompStatement(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(
                                                new forkStmt(
                                                        new CompStatement(
                                                                new HeapWriting("a", new ValueExpression(new IntValue(30))),
                                                                new CompStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new HeapReading(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReading(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        return statement;
    }

    public static IStatement getFailTypeCheckExample(){
        IStatement statement = new CompStatement(
                new VarDeclStmt("x", new BoolType()),
                new AssignStatement("x", new ValueExpression(new IntValue(5)))
        );
        return statement;
    }
    public static IStatement getFailTypeCheckExample2(){
        IStatement statement = new CompStatement(
                new VarDeclStmt("x", new BoolType()),
                new PrintStatement(new AritmeticalExpression(new ValueExpression(new IntValue(5)),ADD,new VariableExpression("x")))
        );
        return statement;
    }
    public static IStatement getFileOpExample(){
        IStatement program = new CompStatement(
                new VarDeclStmt("varf", new StringType()),
                new CompStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(
                                new openRFile(new VariableExpression("varf")),
                                new CompStatement(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new closeRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        return program;
    }

    public static IStatement getRepeatUntilStatement(){

        IStatement forkStatement = new forkStmt(new CompStatement(new PrintStatement(new VariableExpression("v")),
                new AssignStatement("v", new AritmeticalExpression( new VariableExpression("v"), SUBTRACT,new ValueExpression(new IntValue(1))))));

        IStatement statement = new CompStatement(new VarDeclStmt("v",new IntType()),
                new CompStatement(new VarDeclStmt("x",new IntType()),
                        new CompStatement(new VarDeclStmt("y",new IntType()),
                                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(0))),
                                        new CompStatement(new RepeatUntilStatement(
                                                new CompStatement(
                                                forkStatement, new AssignStatement("v",new AritmeticalExpression(new VariableExpression("v"),ADD,new ValueExpression(new IntValue(1))))
                                                        ),new RelationalExpression(new VariableExpression("v"),RelationalOperator.EQUAL,new ValueExpression(new IntValue(3)))
                                        ),
                                                new CompStatement(new AssignStatement("x",new ValueExpression(new IntValue(1))),
                                                new CompStatement(new NopStatement(),
                                                        new CompStatement(new AssignStatement("y",new ValueExpression(new IntValue(3))),
                                                                new CompStatement(new NopStatement(),
                                                                        new PrintStatement(new AritmeticalExpression(new VariableExpression("v"),MULTIPLY,new ValueExpression(new IntValue(10)))))))))))));

        return statement;
    }
    public static IStatement getCyclicBarrierStatement(){

        IStatement varDeclarationsStatement = new CompStatement(new VarDeclStmt("v1",new RefType(new IntType())),
                new CompStatement(new VarDeclStmt("v2",new RefType(new IntType())),
                        new CompStatement(new VarDeclStmt("v3",new RefType(new IntType())),new VarDeclStmt("cnt",new IntType()))));

        IStatement allocationStatements = new CompStatement(new HeapAllocationStatement("v1",new ValueExpression(new IntValue(2))),
                new CompStatement(new HeapAllocationStatement("v2",new ValueExpression(new IntValue(3))),
                        new CompStatement(new HeapAllocationStatement("v3",new ValueExpression(new IntValue(4))),
                                new NewBarrierStatement("cnt",new HeapReading(new VariableExpression("v2"))))));

        IStatement fork1Statement = new forkStmt(new CompStatement(new AwaitStatement("cnt"),
                new CompStatement(new HeapWriting("v1",new AritmeticalExpression(new HeapReading(new VariableExpression("v1")),MULTIPLY,new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new HeapReading(new VariableExpression("v1"))))));

        IStatement fork2Statement = new forkStmt(new CompStatement(new AwaitStatement("cnt"),
                new CompStatement(new HeapWriting("v2",new AritmeticalExpression(new HeapReading(new VariableExpression("v2")),MULTIPLY,new ValueExpression(new IntValue(10))))
                , new CompStatement(new HeapWriting("v2",new AritmeticalExpression(new HeapReading(new VariableExpression("v2")),MULTIPLY, new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new HeapReading(new VariableExpression("v2")))))));

        IStatement statement = new CompStatement(varDeclarationsStatement,
                new CompStatement(allocationStatements,
                        new CompStatement(fork1Statement,
                                new CompStatement(fork2Statement,
                                        new CompStatement(new AwaitStatement("cnt"),new PrintStatement(new HeapReading(new VariableExpression("v3"))))))));
        return statement;
    }
}

