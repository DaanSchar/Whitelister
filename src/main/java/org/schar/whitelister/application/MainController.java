package org.schar.whitelister.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.schar.whitelister.parser.CsvPlayerParser;
import org.schar.whitelister.parser.PlayerNameParser;
import org.schar.whitelister.parser.SimpleTextPlayerParser;
import org.schar.whitelister.whitelist.WhiteList;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainController {

    @FXML
    private Button selectFile;

    @FXML
    private Button generate;

    @FXML
    TextArea fileArea;

    @FXML
    TextArea failedArea;

    @FXML
    TextArea whiteListArea;

    @FXML
    TextField columnNameField;

    @FXML
    TextField selectedFileField;

    @FXML
    public void initialize() {
        this.whiteListArea.setEditable(false);
        this.failedArea.setEditable(false);
        this.selectedFileField.setEditable(false);
    }

    public void selectFileAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        String playerNames = getPlayerNamesFromFile(selectedFile);

        selectedFileField.setText(selectedFile.toString());
        this.fileArea.setText(playerNames);
    }

    public void generateAction(ActionEvent event) {
        String text = this.fileArea.getText();
        List<String> playerNames = Arrays.stream(text.split("\n")).toList();
        WhiteList whiteList = new WhiteList(playerNames);

        this.whiteListArea.setText(whiteList.toString());
        this.failedArea.setText(listToString(whiteList.getFailedPlayers()));
    }

    private String getPlayerNamesFromFile(File file) {
        PlayerNameParser parser;

        if (file.toString().endsWith(".csv")) {
            parser = new CsvPlayerParser(file, columnNameField.getText());
        } else {
            parser = new SimpleTextPlayerParser(file);
        }

        return parser.toString();
    }

    private String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string);
            sb.append("\n");
        }

        return sb.toString();
    }
}
