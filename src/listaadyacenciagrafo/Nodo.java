package listaadyacenciagrafo;
import java.util.*;

public class Nodo {
    //un vértice (nodos) del grafo guarda el identificador del vértice, su número y la lista de adyacencia.
    
    String dato;
    List<Conexion> aristas;
    
    public Nodo (String valor){
        dato=valor;
    }
    public String getDato(){
        return dato;
    }
    public void setDato (String valor){
        dato=valor;
    }
    public List<Conexion> getAristas (){
        return aristas;
    }
    //Un nodo de la lista contiene un objeto de la clase Conexion(Arista)
    public void setAristas(Conexion arista) {
        if (aristas == null) {
            aristas = new ArrayList<>();
        }
        aristas.add(arista);
    }
    /*@Override
     public String toString() {
        return "[vertice=" + dato+"]";
    }*/
    @Override
    public String toString() {
        return "\n \tComunidad [vertice=" + dato + ", arista=" + aristas + "]";
    }
}
