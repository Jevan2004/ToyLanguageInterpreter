package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Controller.IController;
import com.example.toylanguagegui.src.hardcodedExamples.ExamplePrograms;
import com.example.toylanguagegui.src.model.adt.MyDictionary;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.types.IType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;

public class ProgramSelectorWindow {
    IController controller;
    MainWindowController mainWindowController;

    @FXML
    private ListView<IStatement> programList;

    @FXML
    private Button selectButton;

    public ProgramSelectorWindow(IController controller, MainWindowController mainWindowController) {
        this.controller = controller;
        this.mainWindowController = mainWindowController;
    }

    @FXML
    public void initialize() {
        programList.setItems(FXCollections.observableList(Arrays.asList(ExamplePrograms.getExample1(), ExamplePrograms.getExample2(), ExamplePrograms.getExample3(),
                ExamplePrograms.getHeapAllocExample(),ExamplePrograms.getHeapReadExample(),ExamplePrograms.getHeapWriteExample(),
                ExamplePrograms.getGarbageCollectorExample(),ExamplePrograms.getWhileExample(),ExamplePrograms.getForkExample(),
                ExamplePrograms.getFailTypeCheckExample(),ExamplePrograms.getFailTypeCheckExample2(),ExamplePrograms.getFileOpExample(),
                ExamplePrograms.getRepeatUntilStatement(),ExamplePrograms.getCyclicBarrierStatement())));

        selectButton.setOnAction(event -> loadSelectedProgram());
    }

    private void loadSelectedProgram() {
        try {
            IStatement selectedProgramStmt = programList.getSelectionModel().getSelectedItem();
            if (selectedProgramStmt != null) {
                MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
                selectedProgramStmt.typeCheck(typeEnv);
                controller.reinitializeProgram(selectedProgramStmt);
//                this.mainWindowController.setCurrentProgram(selectedProgramStmt.toString());
                this.mainWindowController.refreshAll();
            }
            else{
                this.mainWindowController.setCurrentProgram("Error on selecting program");

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.show();
        }
    }
}
