import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {
    private int score = 0;
    private Text scoreText = new Text("Taskai: 0");
    private Pane gamePane;
    private Scene mainMenuScene, gameScene;
    private AudioClip collectSound;
    private AudioClip backgroundMusic;
    private Timeline appleSpawner;
    private Button muteButton;
    private boolean isMuted = false;
    private Rectangle basket;
    private final double BASKET_SPEED = 20;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupMainMenu(primaryStage);
        setupGameScene(primaryStage);
        initializeSound();

        primaryStage.setTitle("Obuoliuku Zaidimas");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    // Garsu iniciavimas
    private void initializeSound() {
        collectSound = new AudioClip(new java.io.File("resources/collect.wav").toURI().toString());
        backgroundMusic = new AudioClip(new java.io.File("resources/music.wav").toURI().toString());
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();
    }

    // Pagrindinis meniu
    private void setupMainMenu(Stage stage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #98FB98);");

        // Garso mygtukas virsuje
        muteButton = new Button("Muzika");
        muteButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 10; " +
                "-fx-background-color: #FFD700; -fx-text-fill: black; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");
        muteButton.setOnAction(e -> toggleMusic());

        HBox topLeft = new HBox();
        topLeft.setPadding(new Insets(10));
        topLeft.getChildren().add(muteButton);
        mainContainer.setTop(topLeft);

        // Meniu mygtukai centre
        VBox menuLayout = new VBox(20);
        menuLayout.setPadding(new Insets(50));
        menuLayout.setStyle("-fx-alignment: center;");

        Text title = new Text("Obuoliuku Zaidimas");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: #2F4F2F;");

        Button startButton = new Button("Pradeti");
        Button exitButton = new Button("Iseiti");

        String buttonStyle = "-fx-font-size: 18px; -fx-padding: 10 20; " +
                "-fx-background-color: #2eafff; -fx-text-fill: white; " +
                "-fx-background-radius: 5; -fx-cursor: hand;";
        startButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);

        // Mygtuku veiksmai
        startButton.setOnAction(e -> {
            resetGame();  // Paruosia nauja zaidima
            stage.setScene(gameScene);  // Pereina i zaidima
            gameScene.getRoot().requestFocus();
        });
        exitButton.setOnAction(e -> stage.close());  // Uzdaro programa

        menuLayout.getChildren().addAll(title, startButton, exitButton);
        mainContainer.setCenter(menuLayout);

        mainMenuScene = new Scene(mainContainer, 800, 590);
    }

    // Zaidimo scena
    private void setupGameScene(Stage stage) {
        gamePane = new Pane();
        gamePane.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #98FB98);");

        // Prideda medi
        createTreeBackground();

        // Prideda krepsi
        basket = new Rectangle(80, 20);
        basket.setFill(Color.BROWN);
        basket.setStroke(Color.DARKGOLDENROD);
        basket.setStrokeWidth(2);
        basket.setLayoutX(360);
        basket.setLayoutY(550);
        gamePane.getChildren().add(basket);

        // Tasku textas
        VBox ui = new VBox(10);
        ui.setPadding(new Insets(10));
        scoreText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #2F4F2F;");

        // Mygtukas grizti i meniu
        Button backButton = new Button("Atgal i meniu");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 10; " +
                "-fx-background-color: #FF6347; -fx-text-fill: white; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");
        backButton.setOnAction(e -> {
            stopGame();
            stage.setScene(mainMenuScene);
        });

        // Garso mygtukas zaidime
        Button gameMuteButton = new Button("Muzika");
        gameMuteButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 10; " +
                "-fx-background-color: #FFD700; -fx-text-fill: black; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");
        gameMuteButton.setOnAction(e -> toggleMusic());

        ui.getChildren().addAll(scoreText, backButton, gameMuteButton);
        gamePane.getChildren().add(ui);

        gameScene = new Scene(gamePane, 800, 590);

        // Valdymas krepsiui
        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                moveBasketLeft();
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                moveBasketRight();
            }
        });
    }

    // Muzikos isjungimas ir ijungimas
    private void toggleMusic() {
        if (backgroundMusic != null) {
            if (isMuted) {
                backgroundMusic.play();
                isMuted = false;
            } else {
                backgroundMusic.stop();
                isMuted = true;
            }
        }
    }

    // Krepsio judejimas i kaire
    private void moveBasketLeft() {
        double newX = basket.getLayoutX() - BASKET_SPEED;
        if (newX >= 0) {  // Kad neiseitu uz kairio krašto
            basket.setLayoutX(newX);
        }
    }

    // Krepsio judejimas i desine
    private void moveBasketRight() {
        double newX = basket.getLayoutX() + BASKET_SPEED;
        if (newX <= gamePane.getWidth() - basket.getWidth()) {  // Kad neiseitu uz desinio krašto
            basket.setLayoutX(newX);
        }
    }

    // Medis
    private void createTreeBackground() {
        // Medzio kamienas
        Rectangle trunk = new Rectangle(350, 50, 100, 400);
        trunk.setFill(Color.SADDLEBROWN);

        Polygon bottom = new Polygon(
                350.0, 400.0,
                450.0, 400.0,
                500.0, 600.0,
                300.0, 600.0
        );
        bottom.setFill(Color.SADDLEBROWN);

        // Medzio lapai
        Circle leaf0 = new Circle(60, -70, 150);
        Circle leaf1 = new Circle(120, -70, 150);
        Circle leaf2 = new Circle(400, -70, 150);
        Circle leaf3 = new Circle(550, -70, 150);
        Circle leaf4 = new Circle(650, -70, 150);
        Circle leaf5 = new Circle(240, -70, 150);
        Circle leaf6 = new Circle(750, -70, 150);

        for (Circle leaf : new Circle[]{leaf0, leaf1, leaf2, leaf3, leaf4, leaf5, leaf6}) {
            leaf.setFill(Color.GREEN);
            leaf.setStroke(Color.GREEN);
        }

        GaussianBlur blur = new GaussianBlur(10);
        trunk.setEffect(blur);
        bottom.setEffect(blur);

        gamePane.getChildren().addAll(trunk, bottom, leaf0, leaf1, leaf2, leaf3, leaf4, leaf5, leaf6);
    }

    // Zaidimo perkrovimas
    private void resetGame() {
        score = 0;
        scoreText.setText("Taskai: 0");
        // Panaikina obuolius ir texta pabaigos
        gamePane.getChildren().removeIf(node -> node instanceof ImageView ||
                (node instanceof Text && ((Text)node).getText().contains("ZAIDIMAS BAIGTAS")));
        basket.setLayoutX(360);  // Krepsio pozicija
        startAppleSpawner();  // Pradeda obuolius generuoti
    }

    // Sustabdo zaidima
    private void stopGame() {
        if (appleSpawner != null) {
            appleSpawner.stop();
        }
    }

    // Paleidzia obuoliu generatoriu
    private void startAppleSpawner() {
        appleSpawner = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> spawnApple()));
        appleSpawner.setCycleCount(Animation.INDEFINITE);
        appleSpawner.play();
    }

    // Sukurti ir meta obuolius i apacia
    private void spawnApple() {
        Image appleImage = new Image(new java.io.File("resources/apple.png").toURI().toString());
        ImageView apple = new ImageView(appleImage);
        apple.setFitWidth(50);
        apple.setFitHeight(50);
        apple.setPreserveRatio(true);
        apple.setSmooth(true);

        Random rand = new Random();
        double x = rand.nextDouble() * (Math.max(gamePane.getWidth() - 40, 100));
        double y = 0;  // Pradeda nuo virsaus

        apple.setLayoutX(x);
        apple.setLayoutY(y);

        setupAppleEffectsAndHandlers(apple);

        int treeStartIndex = 2;
        if (gamePane.getChildren().size() > treeStartIndex) {
            gamePane.getChildren().add(treeStartIndex, apple);
        } else {
            gamePane.getChildren().add(apple);
        }

        // Obuolio kritimo animacija
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(5), apple);
        fallTransition.setToY(gamePane.getHeight());
        fallTransition.play();

        // Obuolio pasiekimas zemes
        fallTransition.setOnFinished(e -> {
            if (gamePane.getChildren().contains(apple)) {
                gamePane.getChildren().remove(apple);
                score--;
                scoreText.setText("Taskai: " + score);

                if (score < 0) {
                    stopGame();
                    showGameOver();
                }
            }
        });
    }

    // Efektai obuoliui
    private void setupAppleEffectsAndHandlers(javafx.scene.Node apple) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.DARKRED);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        apple.setEffect(shadow);

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.8), apple);
        st.setFromX(0.1);
        st.setToX(1.0);
        st.setFromY(0.1);
        st.setToY(1.0);
        st.play();

        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1), apple);
        pulse.setFromX(1.0);
        pulse.setToX(1.1);
        pulse.setFromY(1.0);
        pulse.setToY(1.1);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        // Tikrina susidurima
        Timeline collisionChecker = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            checkAppleBasketCollision((ImageView) apple);
        }));
        collisionChecker.setCycleCount(Animation.INDEFINITE);
        collisionChecker.play();
    }

    // Tikrina ar ppataike
    private void checkAppleBasketCollision(ImageView apple) {
        if (!gamePane.getChildren().contains(apple)) return;

        // Obuolio pozicija ir dydis
        double appleX = apple.getLayoutX() + apple.getTranslateX();
        double appleY = apple.getLayoutY() + apple.getTranslateY();
        double appleWidth = apple.getFitWidth();
        double appleHeight = apple.getFitHeight();

        // Krepsio pozicija ir dydis
        double basketX = basket.getLayoutX();
        double basketY = basket.getLayoutY();
        double basketWidth = basket.getWidth();
        double basketHeight = basket.getHeight();

        // Tikrina ar obuolys krenta i krepsi
        if (appleX < basketX + basketWidth && appleX + appleWidth > basketX &&
                appleY < basketY + basketHeight && appleY + appleHeight > basketY) {

            gamePane.getChildren().remove(apple);
            score++;
            scoreText.setText("Taskai: " + score);
            if (collectSound != null) {
                collectSound.play();
            }

            ScaleTransition collectAnim = new ScaleTransition(Duration.millis(200), apple);
            collectAnim.setToX(0);
            collectAnim.setToY(0);
            collectAnim.play();
        }
    }

    // Pabaigos ekranas
    private void showGameOver() {
        Text gameOverText = new Text(230, 250, "ZAIDIMAS BAIGTAS!");
        gameOverText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: red;");

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        gameOverText.setEffect(shadow);

        gamePane.getChildren().addAll(gameOverText);

        Timeline returnToMenu = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            gamePane.getChildren().removeAll(gameOverText);
            primaryStage.setScene(mainMenuScene);
        }));
        returnToMenu.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}