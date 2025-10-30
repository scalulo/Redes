package Tp_MQTT;
import javax.crypto.Cipher;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class Broker {
    private static int PUERTO = 9090;
    // Guardamos cada cliente y sus tópicos
    private static HashMap<Socket, ClienteInfo> clientes = new HashMap<>();
    private static HashMap<ClienteInfo, PublicKey> clavesPublicasClientes = new HashMap<>();

    //  claves para el broker
    private static PrivateKey clavePrivadaBroker;
    private static PublicKey clavePublicaBroker;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        //genero claves
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair par = gen.generateKeyPair();
        clavePrivadaBroker = par.getPrivate();
        clavePublicaBroker = par.getPublic();


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
            //envio la clave pub del broker
            out.println(Base64.getEncoder().encodeToString(clavePublicaBroker.getEncoded()));

            // Nombre del cliente
            String nombre = in.readLine();
            ClienteInfo cliente= new ClienteInfo(nombre,out);
            String clavePubB64 = in.readLine();
            byte[] bytesClave = Base64.getDecoder().decode(clavePubB64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey clavePublicaCliente = keyFactory.generatePublic(new X509EncodedKeySpec(bytesClave));
            clavesPublicasClientes.put(cliente, clavePublicaCliente);


            clientes.put(socket, cliente);
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private static void procesarComando(String nombre, String comando) throws Exception {
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

        String comandoPlano = descifrarRSA(comando, clavePrivadaBroker);
        String[] partes = comandoPlano.split(" ", 3); // [0]=Comando, [1]=topico, [2]=mensaje si no hay topico o msj queda null ese campo
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
                String[] datos = partes[2].split("\\|", 3);
                String mensaje = datos[0];
                String hash = datos[1];

                // Verificar firma digital
                PublicKey claveEmisor = clavesPublicasClientes.get(cliente);

                if (!verificarHash(mensaje, hash)) {
                    System.out.println("⚠ Hash inválido de " + nombre);
                    return;
                }



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



    private static String descifrarRSA(String texto, PrivateKey clave) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, clave);
        return new String(c.doFinal(Base64.getDecoder().decode(texto)));
    }

    private static boolean verificarHash(String mensaje, String hashEsperadoBase64) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashCalculado = digest.digest(mensaje.getBytes());
        byte[] hashEsperado = Base64.getDecoder().decode(hashEsperadoBase64);

        if (Arrays.equals(hashCalculado,hashEsperado)){
            return true;
        }
        else{return false;}
    }

}
