package Tp_MQTT;
import javax.crypto.Cipher;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Cliente {
    private static String cifrarRSA(String texto, PublicKey clave) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, clave);
        return Base64.getEncoder().encodeToString(c.doFinal(texto.getBytes()));
    }

    private static String calcularHash(String mensaje) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(mensaje.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);

        ) {
            System.out.print("Ingrese la IP del broker: ");
            String ip=scanner.nextLine();
            Socket socket = new Socket(ip, 9090);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 🔑 Claves del cliente
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            KeyPair par = gen.generateKeyPair();
            PublicKey clavePublica = par.getPublic();
            PrivateKey clavePrivada = par.getPrivate();

            // 🔐 Recibir clave pública del broker
            String claveBrokerB64 = in.readLine();
            byte[] bytesBroker = Base64.getDecoder().decode(claveBrokerB64);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey clavePublicaBroker = kf.generatePublic(new X509EncodedKeySpec(bytesBroker));


            // Nombre del cliente
            System.out.print(" Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            out.println(nombre);
            out.println(Base64.getEncoder().encodeToString(clavePublica.getEncoded()));

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

                if (linea.toUpperCase().startsWith("PUB ")) {
                    // Si es publicación, agregar hash
                    String[] partes = linea.split(" ", 3);
                    if (partes.length < 3) {
                        System.out.println("Formato: PUB <topico> <mensaje>");
                        continue;
                    }
                    String mensaje = partes[2];
                    String hash = calcularHash(mensaje);
                    linea = "PUB " + partes[1] + " " + mensaje + "|" + hash;
                }

                // Envío cifrado
                String cifrado = cifrarRSA(linea, clavePublicaBroker);
                out.println(cifrado);
            }

            out.println("chau");
            socket.close();
            System.out.println("Desconectado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}