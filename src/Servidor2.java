
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ServidorEspecial {

    public static void main(String[] args) {
        try {
            ServidorEspecial servidor = new ServidorEspecial(5051);
            servidor.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final int porta;

    public ServidorEspecial(int porta) throws IOException {
        this.porta = porta;
    }

    public void executar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Ouvindo a porta " + porta);

        Socket server2 = null;
        while ((server2 = servidor.accept()) != null) { // aguarda um cliente se conectar
            System.out.println("Cliente conectado: " + server2.getInetAddress().getHostAddress());

            DataOutputStream o2 = new DataOutputStream(server2.getOutputStream());
            DataInputStream i2 = new DataInputStream(server2.getInputStream());

            String str;

            do {
                byte[] line = new byte[100];
                i2.read(line);
                str = new String(line);

                    if(!str.trim().equals("Data") && !str.trim().equals("calculadora") && !str.trim().equals("bye")) {
                        String[] arrayDeNomes2 = str.split("\n");

                        if(arrayDeNomes2[0].trim().equals("5")) {
                            float num11 = Float.parseFloat(arrayDeNomes2[1]);//***Tenho que corrigir esses variaveis,
                            float num22 = Float.parseFloat(arrayDeNomes2[3]);//***acho que as que estao lá em cima servem pra cá sem influenciar as de cima
                            float soma2;
                            if (arrayDeNomes2[2].trim().equals("1")) {
                                soma2 = num11 + (num11 * num22 / 100);
                                str = Float.toString(soma2);
                                line = str.trim().getBytes();
                                o2.write(line);

                            }//soma com a %
                            if (arrayDeNomes2[2].trim().equals("2")) {
                                soma2 = num11 - (num11 * num22) / 100;
                                str = Float.toString(soma2);
                                line = str.trim().getBytes();
                                o2.write(line);
                            }//subtrao a %
                            if (arrayDeNomes2[2].trim().equals("3")) {
                                soma2 = num11 * (num11 * num22) / 100;
                                str = Float.toString(soma2);
                                line = str.trim().getBytes();
                                o2.write(line);
                            }//multiplico pela %
                            if (arrayDeNomes2[2].trim().equals("4")) {
                                soma2 = num11 / (num11 * num22) / 100;
                                str = Float.toString(soma2);
                                line = str.trim().getBytes();
                                o2.write(line);
                            }//divido pela %
                            //posso colcar uma 5 opcao pra ele ver somente a porcentagem de algum valor
                            //str = "calculadora";
                        }
                        if(arrayDeNomes2[0].trim().equals("6")){
                            double num11 = Double.parseDouble(arrayDeNomes2[1]);
                            str = Double.toString(Math.sqrt(num11));
                            line = str.trim().getBytes();
                            o2.write(line);
                        }
                        if(arrayDeNomes2[0].trim().equals("7")){
                            double num11 = Double.parseDouble(arrayDeNomes2[1]);
                            double num22 = Double.parseDouble(arrayDeNomes2[2]);
                            str = Double.toString(Math.pow(num11, num22));
                            line = str.trim().getBytes();
                            o2.write(line);
                        }

                    }

            } while(!str.trim().equals("bye"));
            //server2 = null;
            System.out.println("Saindo do Servidor 2");
            break;

        }

    }
}