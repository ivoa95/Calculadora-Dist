
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Servidor {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(5050);
            servidor.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final int porta;

    public Servidor(int porta) throws IOException {
        this.porta = porta;
    }

    public void executar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Ouvindo a porta " + porta);

        Socket server1 = null;
        while ((server1 = servidor.accept()) != null) { // aguarda um cliente se conectar
            System.out.println("Cliente conectado: " + server1.getInetAddress().getHostAddress());

            DataOutputStream o1 = new DataOutputStream(server1.getOutputStream());
            DataInputStream i1 = new DataInputStream(server1.getInputStream());

            String str;

            do {
                byte[] line = new byte[100];
                i1.read(line);
                str = new String(line);

                if(str.trim().equals("calculadora")) {

                    line = new byte[100];//aqui ele recebe os valores, mas se o valor for a palavra sair
                    i1.read(line);
                    str = new String(line);

                    if( !str.trim().equals("Data") && !str.trim().equals("calculadora")){
                        String[] arrayDeNomes = str.split("\n");
                        float num1 = Float.parseFloat(arrayDeNomes[0]);
                        float num2 = Float.parseFloat(arrayDeNomes[2]);
                        float soma;
                        if (arrayDeNomes[1].trim().equals("1")) {
                            soma = num1 + num2;
                            str = Float.toString(soma);
                            line = str.trim().getBytes();
                            o1.write(line);
                        }

                        if (arrayDeNomes[1].trim().equals("2")) {
                            soma = num1 - num2;
                            str = Float.toString(soma);
                            line = str.trim().getBytes();
                            o1.write(line);
                        }

                        if (arrayDeNomes[1].trim().equals("3")) {
                            soma = num1 * num2;
                            str = Float.toString(soma);
                            line = str.trim().getBytes();
                            o1.write(line);
                        }

                        if (arrayDeNomes[1].trim().equals("4")) {
                            soma = num1 / num2;
                            str = Float.toString(soma);
                            line = str.trim().getBytes();
                            o1.write(line);
                        }

                    }
                }

                if (str.trim().equals("Data")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    str = dtf.format(LocalDateTime.now());
                    line = str.getBytes();
                    o1.write(line);
                }
            } while(!str.trim().equals("bye"));

             //servidor.accept() = close;
            System.out.println("Saindo do Servidor 1");
            break;

        }

    }
}