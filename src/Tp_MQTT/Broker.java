package Tp_MQTT;
import java.io.*;
import java.net.*;
import java.util.*;

public class Broker {
    private static int PUERTO = 9090;
    // Guardamos cada cliente y sus tópicos
    private static HashMap<Socket, ClienteInfo> clientes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PUERTO);
        System.out.println(" Broker MQTT iniciado en puerto " + PUERTO);

        while (true) {
            Socket socketCliente = serverSocket.accept();
            Thread hilo = new Thread(new Runnable() {
                public void run() {
                    manejarCliente(socketCliente);
                }
            });
            hilo.start();
        }
    }


    private static void manejarCliente(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Datos que vienen del cliente
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)//Datos que van al cliente
        ) {
            // Nombre del cliente
            String nombre = in.readLine();
            clientes.put(socket, new ClienteInfo(nombre, out));
            System.out.println(" Cliente conectado: " + nombre);

            String linea;
            while ((linea = in.readLine()) != null && !linea.equalsIgnoreCase("chau")) {
                procesarComando(nombre, linea);
            }

            clientes.remove(socket);
            socket.close();
            System.out.println(" Cliente desconectado: " + nombre);

        } catch (IOException e) {
            System.out.println(" Error con un cliente: " + e.getMessage());
        }
    }



    private static void procesarComando(String nombre, String comando) {
        ClienteInfo cliente = null;
        for (ClienteInfo c : clientes.values()) {
            if (c.getNombre().equals(nombre)) {
                cliente = c;
                break;
            }
        }
        if (cliente == null) {
            System.out.println("Cliente no encontrado: " + nombre);
            return;
        }

        String[] partes = comando.split(" ", 3); // [0]=Comando, [1]=topico, [2]=mensaje si no hay topico o msj queda null ese campo
        String accion = partes[0].toUpperCase();

        switch (accion) {
            case "SUB":
                if (partes.length < 2) {
                    System.out.println(" Falta tópico para suscribirse.");
                    return;
                }
                String topicoSub = partes[1].trim();

                if(cliente.getTopicos().contains(topicoSub)){
                    System.out.println(cliente.getNombre() +" ya esta suscripto a este topico (" +topicoSub+ ").");
                    return;
                }

                cliente.suscribir(topicoSub);
                System.out.println(nombre + " se suscribió a: " + topicoSub);
                break;

            case "DSUB":
                String topicoDsub = partes[1].trim();
                if (partes.length<2  ){
                    System.out.println(" Falta tópico para desuscribirse.");
                    return;
                }
                if(!cliente.getTopicos().contains(topicoDsub)){
                    System.out.println(cliente.getNombre() +" no esta suscripto a este topico (" + topicoDsub+").");
                    return;
                }
                cliente.desuscribir(topicoDsub);
                System.out.println(nombre + " se desuscribió a: " + topicoDsub);
                break;




            case "PUB":
                if (partes.length < 3) {
                    System.out.println(" Falta tópico o mensaje en PUB.");
                    return;
                }
                String topicoPub = partes[1].trim();
                String mensaje = partes[2].trim();

                System.out.println( nombre + " publicó en " + topicoPub + ": " + mensaje);

                for (ClienteInfo c : clientes.values()) {
                    if (c.getTopicos().contains(topicoPub)) {
                        c.getOut().println("[" + topicoPub + "] " + nombre + ": " + mensaje);
                    }
                }
                break;

            default:
                System.out.println(" Comando inválido: " + comando);
        }
    }

}
