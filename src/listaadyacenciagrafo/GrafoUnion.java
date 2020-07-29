package listaadyacenciagrafo;
import java.util.*;

public class GrafoUnion {
   private List<Nodo> nodos;
 
    public void agregarNodo(Nodo nodo) {
        if (nodos == null) {
            nodos = new ArrayList<>();
        }
        nodos.add(nodo);
    }
 
    public List<Nodo> getNodos() {
        return nodos;
    }
 
    @Override
    public String toString() {
        return "GRAFO [NODOS=" + nodos + "\n]";
    }
}
