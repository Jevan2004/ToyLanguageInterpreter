package com.example.toylanguagegui.src.View;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;

import java.util.Scanner;

public class View implements IView{

    private Controller controller;
    private Scanner scanner;

    public View(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    private void printMenu(){
        System.out.println("Toy Language Interpreter");
        System.out.println("1. Execute Example 1");
        System.out.println("2. Execute Example 2");
        System.out.println("3. Execute Example 3");
        System.out.println("4. Run one step");
        System.out.println("5. Run all steps");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private int getChoice(){
        return scanner.nextInt();
    }

    private void executeExample(PrgState state,IStatement example) throws ADTException {
//        this.controller.clearRepo();

            state.reinitializePrg(example);
//        controller.addProgramState(prgState);
        System.out.println("Example loaded successfully.");
    }

    private void executeAllSteps() {
        try {
            controller.allSteps();
            System.out.println("Final Output:");
//            for (String value : finalOutput) {
//                System.out.println(value);
//            }
        } catch (Exception e) {
            System.out.println("Error during execution: " + e.getMessage());
        }
    }
    private void executeOneStep() {
        try {
            String string;
            controller.oneStepForAllPrg(controller.getRepo().getProgramStates());
//            string = controller.oneStep(controller.getRepo().getCrtPrg());
//            if(!Objects.equals(string, ""))
//            {
//                System.out.print(string);
//            }

        } catch (Exception e) {
            System.out.println("Error during execution: " + e.getMessage());
        }
    }
    @Override
    public void run() {
        while (true) {
            printMenu();
            int choice = getChoice();
            switch (choice) {
//                case 1 -> executeExample(ExamplePrograms.getExample1());
//                case 2 -> executeExample(ExamplePrograms.getExample2());
//                case 3 -> executeExample(ExamplePrograms.getExample3());
                case 4 -> executeOneStep();
                case 5-> executeAllSteps();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
