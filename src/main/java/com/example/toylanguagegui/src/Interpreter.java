package com.example.toylanguagegui.src;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.Repo.Repo;
import com.example.toylanguagegui.src.View.command.ExitCommand;
import com.example.toylanguagegui.src.View.command.LoadExample;
import com.example.toylanguagegui.src.View.command.OneStep;
import com.example.toylanguagegui.src.View.command.RunExampleCommand;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.hardcodedExamples.ExamplePrograms;
import com.example.toylanguagegui.src.hardcodedExamples.TestFileOperations;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.View.TextMenu;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class Interpreter {

    public static void main(String[] args) throws StatementException, ADTException, IOException, ExpressionException {
//        IRepo repo = new Repo();
//
//        Controller controller = new Controller(repo);
//
//        View view = new View(controller);
//
//        view.run();

        TestFileOperations testFileOperations = new TestFileOperations();
        testFileOperations.ceva();
        MyIHeap heap = new MyHeap();
        MyIList<String> output = new MyList<>();
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        
        IStatement ex1 = ExamplePrograms.getExample1();
        MyIDictionary<String, IValue> symTable1 = new MyDictionary<>(symTable.getMap());
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();

//        PrgState prg1 = new PrgState(symTable,exeStack,output,ex1,fileTable,heap);
        IRepo repo = new Repo("log1.txt");
        //repo.addProgramState(prg1);
        Controller controller = new Controller(repo);

        IStatement ex2 = ExamplePrograms.getExample2();
//        PrgState prg2 = new PrgState(ex2);
//        IRepo repo2 = new Repo("log2.txt");
//        repo2.addProgramState(prg2);
//        Controller controller2 = new Controller(repo2);
//
        IStatement ex3 = ExamplePrograms.getExample3();
//        PrgState prg3 = new PrgState(ex3);
//        IRepo repo3 = new Repo("log3.txt");
//        repo3.addProgramState(prg3);
//        Controller controller3 = new Controller(repo3);
        IStatement HeapExample = ExamplePrograms.getHeapAllocExample();
        IStatement HeapReadingExample = ExamplePrograms.getHeapReadExample();
        IStatement HeapWritingExample = ExamplePrograms.getHeapWriteExample();
        IStatement GarbageCollector = ExamplePrograms.getGarbageCollectorExample();
        IStatement WhileExample = ExamplePrograms.getWhileExample();
        IStatement forkExample = ExamplePrograms.getForkExample();
        IStatement typeCheckFail = ExamplePrograms.getFailTypeCheckExample();
        IStatement typeCheckFail2 = ExamplePrograms.getFailTypeCheckExample2();

        TextMenu textMenu = new TextMenu();

        textMenu.addCommand(new ExitCommand("0","exit"));
        textMenu.addCommand(new RunExampleCommand("1","Execute all steps",controller));
        textMenu.addCommand(new OneStep("2","Execute one step",controller));
//        textMenu.addCommand(new RunExampleCommand("2",ex2.toString(),controller));
//        textMenu.addCommand(new RunExampleCommand("3",ex3.toString(),controller));
        textMenu.addCommand(new LoadExample("3","Load example 1",repo,controller,ex1));
        textMenu.addCommand(new LoadExample("4","Load example 2",repo,controller,ex2));
        textMenu.addCommand(new LoadExample("5","Load example 3",repo,controller,ex3));
        textMenu.addCommand(new LoadExample("6","Load example heap alloc",repo,controller,HeapExample));
        textMenu.addCommand(new LoadExample("7","Load example heap read",repo,controller,HeapReadingExample));
        textMenu.addCommand(new LoadExample("8","Load example heap write",repo,controller,HeapWritingExample));
        textMenu.addCommand(new LoadExample("9","Load example garbage collector",repo,controller,GarbageCollector));
        textMenu.addCommand(new LoadExample("10","Load example while statement",repo,controller,WhileExample));
        textMenu.addCommand(new LoadExample("11","Load example fork statement",repo,controller,forkExample));
        textMenu.addCommand(new LoadExample("12","Load example type check fail",repo,controller,typeCheckFail));
        textMenu.addCommand(new LoadExample("13","Load example 2 type check fail",repo,controller,typeCheckFail2));

        textMenu.show();
    }
}