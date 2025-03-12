package com.example.toylanguagegui;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.toylanguagegui.src.Controller.IController;
import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainWindowController {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Pair<Integer, IValue>>  heapList;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> address;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> value;

    @FXML
    private TableView<Pair<String, IValue>> SymTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> VarNamCol;

    @FXML
    private TableColumn<Pair<String, IValue>, String> ValueCol;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, List<Integer>>>> barrierTableTable;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> indexCol;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> valueBarrierCol;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> listOfValuesCol;

    @FXML
    private Button runProgram;

    @FXML
    private Button RunOneStepButton;

    @FXML
    private TextArea selectedProgram;

    @FXML
    private ListView<Integer> programStatesList;

    @FXML
    private Label numberOfPrograms;

    @FXML
    private ListView<String> ExeStackView;


    public void setCurrentProgram(String program) {
        selectedProgram.setText(program);
    }

    IRepo repo;
    IController controller;
    MyIHeap heap;
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    List<String> output;
    IBarrierTable barrierTable;
    @FXML ListView<String> outListView;
    @FXML private ListView<String> fileTableView;


    public void setDependencies(IRepo repo, IController controller,
                                MyIStack<IStatement> exeStack,
                                MyIDictionary<String, IValue> symTable) {
        this.repo = repo;
        this.controller = controller;
        this.heap = repo.getProgramStates().get(0).getHeap();
        this.exeStack = exeStack;
        this.barrierTable = repo.getProgramStates().get(0).getBarrierTable();
        this.symTable = repo.getProgramStates().get(0).getSymTable();
        this.fileTable = repo.getProgramStates().get(0).getFileTable();
        this.output = new ArrayList<>();

        repo.addListener(() -> {
            try {
                refreshProgramStatesList();
            } catch (ADTException e) {
                e.printStackTrace();
            }
        });
    }

    public void initialize() throws ADTException {
        address.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        value.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        VarNamCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        ValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        indexCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getKey()).asObject()
        );

        valueBarrierCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getValue().getKey()).asObject()
        );

        listOfValuesCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getValue().toString())
        );

        // Set button actions
        runProgram.setOnAction(event -> {
            try {
                runSelectedProgram();
            } catch (ADTException e) {
                throw new RuntimeException(e);
            }
        });

        RunOneStepButton.setOnAction(event -> {
            try {
                runOneStep();
            } catch (ADTException e) {
                throw new RuntimeException(e);
            }
        });

        programStatesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onProgramStateSelected(newValue);
            }
        });


    }

    private void refreshProgramStatesList() throws ADTException {
        programStatesList.getItems().clear();
        List<PrgState> states = repo.getProgramStates();
        for(PrgState el : controller.getPrgList()){
            programStatesList.getItems().add(el.getId());
        }
        // Update the number of programs
        numberOfPrograms.setText(String.valueOf(states.size()));
    }

    private void onProgramStateSelected(Integer newValue){
        Integer selectedProgramId = programStatesList.getSelectionModel().getSelectedItem();
        ExeStackView.getItems().clear();
        SymTableView.getItems().clear();
        PrgState current = null;

        try {
            // Find the program state by ID
            for (PrgState el : controller.getPrgList()) {
                if (el.getId() == selectedProgramId) {
                    current = el;
                    break;  // No need to continue once we find the state
                }
            }

            if (current != null) {
                populateSymTable(current);
                populateExeStack(current);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Function to populate the Symbol Table view
    private void populateSymTable(PrgState current) {
        SymTableView.getItems().clear();
        current.getSymTable().getMap().forEach((key, value) -> {
            SymTableView.getItems().add(new Pair<>(key, value));
        });
    }
    private void populateBarrierTable() {
        barrierTableTable.getItems().clear();

        barrierTable.getBarrierTable().forEach((index, pair) -> {
            barrierTableTable.getItems().add(new Pair<>(index, pair));
        });
    }
    // Function to populate the Execution Stack view
    private void populateExeStack(PrgState current) {
        ExeStackView.getItems().clear();
        Stack<IStatement> stack = new Stack<>();
        Stack<IStatement> originalStack = current.getExeStack().getStack();
        stack.addAll(originalStack);

        while (!stack.isEmpty()){
            ExeStackView.getItems().add(stack.pop().toString());
        }
    }


    public void refreshAll() throws ADTException {
        heapList.getItems().clear();
        outListView.getItems().clear();
        barrierTableTable.getItems().clear();

        Integer nrProgramStates = repo.getProgramStates().size();

        numberOfPrograms.setText(nrProgramStates.toString());
        selectedProgram.setText(controller.getLoadedProgram().toString());

        ObservableList<Integer> identifiers = FXCollections.observableArrayList();
        for(PrgState el : controller.getPrgList()){
            if(!el.getExeStack().isEmpty()){
                identifiers.add(el.getId());
            }
        }
        programStatesList.setItems(identifiers);
            output = controller.getPrgList().getFirst().getOutput().getAll();
            for(String el : output) {
                outListView.getItems().add(el);
        }
        populateFileTable();
        populateHeap();
        populateBarrierTable();

    }

    private void runSelectedProgram() throws ADTException {
        outListView.getItems().clear();
        barrierTableTable.getItems().clear();
        refreshAll();
        try {
            IStatement selectedProgramStmt = controller.getLoadedProgram();
            if (selectedProgramStmt == null) {
                outListView.getItems().add("Error: No program selected. Please select a program before running.");
                return;
            }
//            output = runProgram(selectedProgramStmt);
            runProgram(selectedProgramStmt);
//            output = repo.getProgramStates().get(0).getOutput().getAll();
//            for(String el : output) {
//                outListView.getItems().add(el);
//            }
            populateHeap();
            populateBarrierTable();
            numberOfPrograms.setText(String.valueOf(controller.getPrgList().size()));
        }
        catch (ADTException _) {}

    }

    private List<String> runProgram(IStatement statement) {
        try {
            MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
            try {
                statement.typeCheck(typeEnv);
            }
            catch (Exception e){
                outListView.getItems().add("Failed type check");
                throw e;
            }
            List<String> output = controller.allSteps();

            for(String el : output) {
                outListView.getItems().add(el);
            }
//            refreshAll();
            programStatesList.getItems().clear();
            return output;
        }
        catch(Exception e) {
            outListView.getItems().add(e.getMessage());
        }
        return Arrays.asList("Failed to run program");
    }
    private void populateHeap() {
        heapList.getItems().clear();
        heap.getHeapMap().forEach((key, value) -> {heapList.getItems().add(new Pair<>(key,value));});

    }
    private void populateFileTable(){

            fileTable = controller.getPrgList().get(0).getFileTable();
            fileTableView.getItems().clear();
            fileTableView.getItems().add(fileTable.toString());
    }
    private void runOneStep() throws ADTException {
        try {
//            List<String> stepOutput = controller.getPrgList().get(0).getOutput().getAll();
            controller.oneStep();
            refreshAll();


            if (controller.getPrgList().isEmpty()) {
                RunOneStepButton.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Execution completed. No programs left.");
                alert.show();
            }
        } catch (Exception e) {
            refreshAll();
            populateExeStack(controller.getPrgList().get(0));
            populateSymTable(controller.getPrgList().get(0));
            onProgramStateSelected(null);
            numberOfPrograms.setText(String.valueOf(controller.getPrgList().size() - 1));
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Execution Error");
            alert.show();
        }
    }


}