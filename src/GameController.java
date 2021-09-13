import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;

public class GameController {

    private Timer timer = new Timer();

    private KMiner kMiner = new KMiner();

    @FXML
    Button nextButton;

    @FXML
    Button exitButton;

    @FXML
    Label timerLabel;

    @FXML
    Label levelLabel;

    @FXML
    ImageView imageView;


    @FXML
    GridPane grid;




    public void initialize() throws MalformedURLException {
        buildGridPane();
        Path imageFile = Paths.get("./lunette.png");
        imageView.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
    }


    private void buildGridPane() {
        buildGridConstraints();
        buildGridContent();
    }

    private void buildGridConstraints() {
        grid.setMaxSize(600, 410);
        for (int i = 0; i < kMiner.columnCount; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / kMiner.rowCount);

            grid.getColumnConstraints().add(i, colConst);
        }
        for (int i = 0; i < kMiner.rowCount; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / kMiner.columnCount);

            grid.getRowConstraints().add(i, rowConst);
        }
    }

    private void buildGridContent() {
        for (int i = 0; i < kMiner.rowCount; i++) {
            for (int j = 0; j < kMiner.columnCount; j++) {
                grid.add(createLabel(i, j), i, j);
            }
        }
    }

    private void clearGridContent() {
        for (Node node : grid.getChildren()) {
            Label kMineLabel = (Label) node;
            kMineLabel.setText("");
        }
    }



    public Label createLabel(int x, int y) {
        Label label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.getStyleClass().add("mine_label");
        /*label.setPrefWidth(30);
        label.setPrefHeight(20);

        label.setMaxSize(30, 20); */

        label.setPrefWidth(Double.MAX_VALUE);
        label.setPrefHeight(Double.MAX_VALUE);
        label.setOnMousePressed(event -> {

            int mineOrNot = kMiner.getGrid()[x][y];

            if (kMiner.playerWined()) {
                //Open information dialog that says that he wins
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez gagné; félicitations.");
                alert.showAndWait();
                kMiner.generateLevelTwo();
                updateWindow();

            }

            if (mineOrNot > 0)
                label.setText(Integer.toString(kMiner.neighborMinesCountAt(x, y)));

            else {
                //Open information dialog that says that he loose
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez perdu; GAME OVER");
                alert.showAndWait();
                updateWindow();
            }
        });
        return label;
    }

    private void updateWindow() {
        levelLabel.setText("Level " + kMiner.getCurrentLevel());
        clearGridContent();
    }


    @FXML
    private void handleOnNextClicked() {
        if (kMiner.getCurrentLevel() == 4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Félicitation, vous avez terminé");
            alert.showAndWait();
        }
        else {
            kMiner.nextLevel();
            updateWindow();
        }
    }

    @FXML
    private void handleOnExitClicked() {
        Platform.exit();
    }
}
