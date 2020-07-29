package InterfazGrafo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 *
 * @author Hugo Ruiz
 */
public class GraficaController implements Initializable {

    @FXML Button button;   
    @FXML
    public void graficar(ActionEvent event) {
        Stage stage2 = (Stage) button.getScene().getWindow();
        stage2.close();
        crear(event);
    }
    
    int[] posiciones = {100,100,100,300,300,100,300,300,500,100,500,300,100,500,300,500,500,500};
    String[] nombreNodos = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    Label[] label;  
    Line [] line;
    Circle[] circle;
    int cantidadCirculos;
    int cantidadRelaciones;
    int cantidadVerificaciones;
    int[] conexiones;
    int[] verificarDireccion;
    Label[] labelCon;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public static List<Circle> circles = new ArrayList<Circle>();

    public void crear(ActionEvent event) {  
        System.out.println("Cantidad de nodos "+cantidadCirculos);
        System.out.println("Cantidad de conexiones "+cantidadCirculos);
        for (int x = 0; x < cantidadRelaciones*3; x++) {
            System.out.println(conexiones[x]); 
        }
        System.out.println("-----------------------");
       
        Group root = new Group();
        Canvas canvas = new Canvas(300, 300);           
        line = new Line[110];
        labelCon = new Label[110];
        circle = new Circle[cantidadCirculos];
        label = new Label[cantidadCirculos];
        int relocate = 0;
        for(int x=0; x<cantidadCirculos; x++){
            circle[x] = new Circle(25);
            circle[x].setStroke(Color.GREEN);
            circle[x].setFill(Color.CHARTREUSE.deriveColor(1, 1, 1, 0.7));
            circle[x].relocate(posiciones[relocate], posiciones[++relocate]);
            relocate++;
            label[x] = new Label(nombreNodos[x]);
            label[x].setLayoutX( circle[x].getLayoutX());
            label[x].setLayoutY(circle[x].getLayoutY());
        }
        
        int posLine = 0;
        int posConex = 0;
        int ban = 0;
         String texto;
        for(int j=0; j<cantidadRelaciones; j++){ 
            texto ="";
            ban = 0;
            //for(int x=0; x<cantidadVerificaciones; x++){
                /*if(verificarDireccion[x] == conexiones[posConex] && verificarDireccion[x+1]== conexiones[posConex+1] || verificarDireccion[x] == conexiones[posConex+1] && verificarDireccion[x+1]== conexiones[posConex]){
                    ban = 1;*/                 
                 if(conexiones[posConex]>conexiones[posConex+1]){
                    ban = 2;
                }else if(conexiones[posConex]<conexiones[posConex+1]){
                    ban = 3;
                }
                 // x++;
           // }
            if(ban==3){
                texto = String.valueOf("      "+conexiones[posConex+2])+"-->";     
            }else if(ban==2){
                texto = String.valueOf("<--"+conexiones[posConex+2]+"    ");     
            }else{
                texto = String.valueOf(conexiones[posConex+2]);     
            }                 
            labelCon[posLine] = new Label(texto);     
            line[posLine] = new Line(circle[conexiones[posConex]].getLayoutX(), circle[conexiones[posConex]].getLayoutY(), circle[conexiones[posConex+1]].getLayoutX(), circle[conexiones[posConex+1]].getLayoutY());
            labelCon[posLine].setLayoutX((circle[conexiones[posConex]].getLayoutX() + circle[conexiones[posConex+1]].getLayoutX()) / 2 );
            labelCon[posLine].setLayoutY((circle[conexiones[posConex]].getLayoutY() + circle[conexiones[posConex+1]].getLayoutY()) / 2 );
            line[posLine].setStrokeWidth(2);
            line[posLine].setStroke(Color.BLUE.deriveColor(0, 1, 1, 0.5));           
            line[posLine].setStrokeLineCap(StrokeLineCap.BUTT);
            line[posLine++].getStrokeDashArray().setAll(10.0, 5.0);          
            posConex +=3;         
        }

        Pane overlay = new Pane();
        ObservableList<Node> nodeList = FXCollections.observableArrayList();
        
        for(int x=0; x<cantidadCirculos;x++){
            nodeList.add(circle[x]);
            nodeList.add(label[x]);
        }
        for(int x=0; x<posLine;x++){
            nodeList.add(line[x]);
        }
        for(int x=0; x<posLine;x++){
            nodeList.add(labelCon[x]);
        }
       
        overlay.getChildren().addAll(nodeList);
        MouseGestures mg = new MouseGestures();       
         for(int x=0; x<cantidadCirculos;x++){
           mg.makeDraggable(circle[x]);
        }
        for(int x=0; x<posLine;x++){
            mg.makeDraggable(line[x]);
        }
        root.getChildren().addAll(canvas, overlay);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static class MouseGestures {
        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public void makeDraggable(Node node) {
            node.setOnMousePressed(circleOnMousePressedEventHandler);
            node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        }
        EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                if (t.getSource() instanceof Circle) {
                    Circle p = ((Circle) (t.getSource()));
                    orgTranslateX = p.getCenterX();
                    orgTranslateY = p.getCenterY();
                } else {
                    Node p = ((Node) (t.getSource()));
                    orgTranslateX = p.getTranslateX();
                    orgTranslateY = p.getTranslateY();
                }
            }
        };
        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;
                if (t.getSource() instanceof Circle) {
                    Circle p = ((Circle) (t.getSource()));
                    p.setCenterX(newTranslateX);
                    p.setCenterY(newTranslateY);
                } else {
                    Node p = ((Node) (t.getSource()));
                    p.setTranslateX(newTranslateX);
                    p.setTranslateY(newTranslateY);
                }
            }
        };
    }
    
    public void setCantidad(int cantidad){
        this.cantidadCirculos = cantidad;
    }
    
    public void setCantRelaciones(int cantidad){
        this.cantidadRelaciones = cantidad;
    }
    
    public void setConexiones(int[] conexiones){
        this.conexiones = conexiones;
    }
    
    public void setVerificarDireccion(int[] conexiones){
        this.verificarDireccion = conexiones;
    }
    
    public void setCantVerific(int cant){
        this.cantidadVerificaciones = cant;
    }
    
    private void drawShapes(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeRoundRect(10, 10, 230, 230, 10, 10);
    }

}

