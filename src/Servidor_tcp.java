import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor_tcp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Servidor esperando conexiones...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            Thread hilo = new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        Scanner consola = new Scanner(System.in); // Scanner para la consola del servidor

                        String mensaje = "";
                        while (mensaje != null && !mensaje.equals("chau")) {
                            mensaje = in.readLine();
                            System.out.println("Cliente dice: " + mensaje);

                            // Servidor decide qué responder por consola
                            System.out.print("Responder al cliente (enter para no responder): ");
                            String respuesta = consola.nextLine();


                        }

                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error con el cliente: " + e.getMessage());
                    }
                }
            });

            hilo.start();
        }
    }
}
