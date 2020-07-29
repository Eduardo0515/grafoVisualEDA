package listaadyacenciagrafo;

import InterfazGrafo.GraficaController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Hugo Ruiz
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    ComboBox comboNodos, conexiones;
    @FXML
    TextField cantComun;
    @FXML
    Button crearGrafo, aceptar;
    Nodo[] nodo;
    int cantidadNodos;
    int cantConexiones;
    GrafoUnion grafo;
    int[] connects;

    @FXML
    private void crearNodos(ActionEvent event) {
        if (Integer.parseInt(cantComun.getText()) >= 1) {
            nodo = new Nodo[Integer.parseInt(cantComun.getText())];
            cantidadNodos = Integer.parseInt(cantComun.getText());
            comboNodos.getItems().clear();
            String[] nodoss = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
            for (int i = 0; i < Integer.parseInt(cantComun.getText()); i++) {
                nodo[i] = new Nodo(nodoss[i]);
                comboNodos.getItems().add(nodo[i]);
            }
            connects = new int[250];
            cantConexiones = 0;
            verificarDireccion = new int[50];
            aceptar.setDisable(true);
        } else {
            Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
            dialogo.setTitle("Error");
            dialogo.setHeaderText(null);
            dialogo.setContentText("Ingrese una cantidad mayor a cero");
            dialogo.initStyle(StageStyle.UTILITY);
            dialogo.showAndWait();
        }
    }

    public void listCombo() {
        //comboNodos.setOnAction(e -> System.out.println("Action Nueva Selección: " + comboNodos.getValue()));
        comboNodos.valueProperty().addListener((ov, p1, p2) -> {
            conexiones.getItems().clear();
            for (int i = 0; i < cantidadNodos; i++) {
                if (comboNodos.getSelectionModel().getSelectedItem().equals(nodo[i])) {
                    System.out.println("Nodo seleccionado: " + comboNodos.getSelectionModel().getSelectedItem());
                } else {
                    conexiones.getItems().add(nodo[i]);
                }
            }
        });
    }

    int posLine = 0;
    int posconects = 0;
    int[] verificarDireccion;
    int posVerificar = 0;

    @FXML
    public void relacionar(ActionEvent event) {
        double peso = generarPeso();
        double relacion = peso;
        int posicionFin = conexiones.getSelectionModel().getSelectedIndex();
        int posicionInicio = comboNodos.getSelectionModel().getSelectedIndex();
        if (conexiones.getSelectionModel().getSelectedIndex() >= posicionInicio) {
            posicionFin = conexiones.getSelectionModel().getSelectedIndex() + 1;
        }
        int comparacion = 0;
        nodo[posicionInicio].setAristas(new Conexion(nodo[posicionInicio], nodo[posicionFin], relacion));
        if (nodo[posicionFin].getAristas() != null) {
            for (int x = 0; x < nodo[posicionFin].getAristas().size(); x++) {
                if (nodo[posicionFin].getAristas().get(x).getFin().equals(nodo[posicionInicio])) {
                    comparacion = (int) nodo[posicionFin].getAristas().get(x).getPeso();
                    relacion = Math.abs(peso - nodo[posicionFin].getAristas().get(x).getPeso());
                    nodo[posicionFin].getAristas().get(x).setPeso(relacion);
                    for (int j = 0; j < nodo[posicionInicio].getAristas().size(); j++) {
                        if (nodo[posicionInicio].getAristas().get(x).getFin().equals(nodo[posicionFin])) {
                            nodo[posicionInicio].getAristas().get(x).setPeso(relacion);
                        }
                    }
                    for (int j = 0; j < posconects; j++) {
                        if (comparacion == connects[j]) {
                            connects[j] = (int) relacion;
                        }
                    }
                    verificarDireccion[posVerificar++] = posicionInicio;
                    verificarDireccion[posVerificar++] = posicionFin;
                }
            }
        }

        connects[posconects] = posicionInicio;
        connects[++posconects] = posicionFin;
        connects[++posconects] = (int) relacion;
        posconects++;
        cantConexiones++;
    }

    @FXML
    public void pruebas() {

    }

    @FXML
    public void crearGrafo(ActionEvent event) throws IOException {
        grafo = new GrafoUnion();
        for (int i = 0; i < cantidadNodos; i++) {
            grafo.agregarNodo(nodo[i]);
        }
        System.out.println(grafo);
        ventanaGrafica(event);
    }

    public int generarPeso() {
        int peso = (int) (Math.random() * 100) + 1;
        System.out.println(peso);
        return peso;
    }

    @FXML
    public void ventanaGrafica(ActionEvent event) throws IOException {
        grafo = new GrafoUnion();
        for (int i = 0; i < cantidadNodos; i++) {
            grafo.agregarNodo(nodo[i]);
        }
        System.out.println(grafo);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/InterfazGrafo/Grafo.fxml"));
        loader.load();
        Label label = new Label("ddd");
        GraficaController graficaController = loader.getController();
        graficaController.setCantidad(cantidadNodos);
        graficaController.setConexiones(connects);
        graficaController.setCantRelaciones(cantConexiones);
        graficaController.setCantVerific(posVerificar);
        graficaController.setVerificarDireccion(verificarDireccion);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listCombo();
    }

}
