package listaadyacenciagrafo;

public class Conexion {
    private Nodo inicio;
    private Nodo fin;
    private double peso;
 /*Un objeto de la clase Conexion(Arista), guarda el vértice destino v del arco que tiene su origen en u; 
   además, en los grafos valorados, el peso asociado al arista.*/
    public Conexion(Nodo origen, Nodo destino, double valor) {
        inicio = origen;
        fin = destino;
        peso = valor;
    }
    public Nodo getInicio() {
        return inicio;
    }
    public void setInicio(Nodo origen) {
        inicio = origen;
    }
    public Nodo getFin() {
        return fin;
    }
    public void setFin(Nodo destino) {
        fin = destino;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double valor) {
        peso = valor;
    }
    @Override
    public String toString() {
        return " Arista [inicio=" + inicio.getDato()+ ", fin=" + fin.getDato() + ", peso="+ peso + "]";
    }
}
