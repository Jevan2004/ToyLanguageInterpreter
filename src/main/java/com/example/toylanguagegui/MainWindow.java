package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.Repo.Repo;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.value.IValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        IRepo repo = new Repo("log1.txt");
        Controller controller = new Controller(repo);


        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 520);

        MainWindowController windowController = fxmlLoader.getController();
        windowController.setDependencies(repo, controller, exeStack, symTable);

        stage.setTitle("Toy interpreter");
        stage.setScene(scene);
        stage.show();

        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader();

        fxmlLoader2.setControllerFactory(c -> new ProgramSelectorWindow(controller,windowController));
        fxmlLoader2.setLocation(MainWindow.class.getResource("programSelector.fxml"));

        Scene scene2 = new Scene(fxmlLoader2.load(), 640, 420);


        stage2.setTitle("Program Selector");
        stage2.setScene(scene2);
        stage2.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
