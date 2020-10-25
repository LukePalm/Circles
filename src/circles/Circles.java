package circles;

import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A lab exercise to introduce Java 8 lambdas and streams.
 * @author Your name here
 */
public class Circles extends Application {
    
    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 100;
    
    @Override
    public void start(Stage primaryStage) {
        root = new VBox();
        canvas = new Pane();
        starter = new Button("Circles");
        
        root.setAlignment(Pos.CENTER);
        canvas.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        
        addButtonHandler();  // You must write
        
        root.getChildren().addAll(canvas, starter);
        
        primaryStage.setTitle("Java 8 Lab Exercise");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        //makeRow().forEach(x -> System.out.println(x));
        //makeAllRows().forEach(r -> r.forEach(x -> System.out.println(x)));
    }
    
    /**
     * This method adds the handler to the button that gives
     * this application its behavior.
     */
    private void addButtonHandler() {
        //Circle cir = new Circle(CELL_SIZE/4);
       
       
       starter.setOnAction((e) -> {
           canvas.getChildren().clear();
           this.addAllRowsToCanvas(makeAllRows());
       });
    }
    
    private VBox root;
    private Pane canvas;
    private Button starter;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    void addToCanvas(Circle cir) {
        
        cir.setFill(new Color(Math.random(), Math.random(), Math.random(), 1.0));
        double toX = (double)(this.col * 100 + 50);
        double toY = (double)(this.row * 100 + 50);
        double fromX = 450.0;
        double fromY = 350.0;
        cir.setCenterX(fromX);
        cir.setCenterY(fromY);
        canvas.getChildren().add(cir);
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(500));
        tt.setNode(cir);
        tt.setByX(toX - fromX);
        tt.setByY(toY-fromY);
        tt.play();
        
        ScaleTransition st = new ScaleTransition(Duration.millis(500 * Math.random() + 500));
        st.setNode(cir);
        //st.setByX(Math.random());  //random fun elipses
        //st.setByY(Math.random());
        st.setByX(2);
        st.setByY(2);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
    }
    
    private Stream<Circle> makeRow() {
        return Stream.generate( () -> {
            return new Circle(25);
        }).limit(5);
    }
    
    private void addRowToCanvas(Stream<Circle> str){
        this.col=0;
        str.forEach((cir) -> {
            this.addToCanvas(cir);
            col++;
        });
    }
    
    private Stream<Stream<Circle>> makeAllRows () {
        return Stream.generate( () -> {
            return makeRow();
        }).limit(4);
    }
    
    private void addAllRowsToCanvas(Stream<Stream<Circle>> str){
        this.row = 0;
        str.forEach((r) -> {
            this.addRowToCanvas(r);
            row++;
        });
        
    }
    
    private int row = ROWS-4;
    private int col = COLS-5;
}