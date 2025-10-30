package Tp_MQTT;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);

        ) {
            System.out.print("Ingrese la IP del broker: ");
            String ip=scanner.nextLine();
            Socket socket = new Socket(ip, 9090);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Nombre del cliente
            System.out.print(" Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            out.println(nombre);

            // Hilo para escuchar mensajes
            Thread listener = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String recibido;
                        while ((recibido = in.readLine()) != null) {
                            System.out.println(recibido);
                            System.out.print("- ");
                        }
                    } catch (IOException e) {
                        System.out.println("Conexión cerrada.");
                    }
                }
            });
            listener.start();


            // Comandos
            System.out.println("Comandos:");
            System.out.println("  SUB  (topico)→  Suscribirse a un topico");
            System.out.println("  DSUB  (topico)→  Desuscribirse a un topico");
            System.out.println("  PUB (topico, mensaje)  →  Publicar");
            System.out.println("  chau  →  Salir");

            // Enviar comandos
            String linea;
            while (!(linea = scanner.nextLine()).equalsIgnoreCase("chau")) {
                out.println(linea);
            }

            out.println("chau");
            socket.close();
            System.out.println(" Desconectado.");

        } catch (IOException e) {
            System.out.println(" Error al conectar con el broker.");
        }
    }
}
