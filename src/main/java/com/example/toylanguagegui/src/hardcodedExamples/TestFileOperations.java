
package com.example.toylanguagegui.src.hardcodedExamples;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.Repo.Repo;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.expresions.ValueExpression;
import com.example.toylanguagegui.src.model.expresions.VariableExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.*;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.types.StringType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.BufferedReader;


public class TestFileOperations {
    public void ceva() {
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

        try {
            MyIHeap heap = new MyHeap();
            MyIList<String> output = new MyList<>();
            MyIStack<IStatement> exeStack = new MyStack<>();
            MyIDictionary<String, IValue> symTable = new MyDictionary<>();
            MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
            IBarrierTable barrierTable = new BarrierTable();
            PrgState initialState = new PrgState(symTable,exeStack,output,program,fileTable,heap,barrierTable);
            IRepo repo = new Repo("logtest.txt");

            repo.addProgramState(initialState);

            Controller controller = new Controller(repo);
            controller.allSteps();
//
//            MyIList<String> output = controller.allSteps();
//            for(String el:output.getAll()){
//                System.out.println(el);
//            }
            } catch (Exception e) {
            System.err.println("Execution error: " + e.getMessage());
        }
    }
}
