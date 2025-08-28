package Tp_MQTT;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

class ClienteInfo {
    private String nombre;
    private PrintWriter out;
    private Set<String> topicos;

    public ClienteInfo(String nombre, PrintWriter out) {
        this.nombre = nombre;
        this.out = out;
        this.topicos = new HashSet<>();
    }

    public String getNombre() { return nombre; }
    public PrintWriter getOut() { return out; }
    public Set<String> getTopicos() { return topicos; }

    public void suscribir(String topico) {
        topicos.add(topico);
    }
}