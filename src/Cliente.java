
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.String;

class Cliente {

    public static void main(String[] args) throws IOException {
        try {
            Cliente cliente = new Cliente("127.0.0.1");
            cliente.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final Scanner scan = new Scanner(System.in);


    private final String host;


    public Cliente(String host) {
        this.host = host;

    }

    public void executar() throws IOException {
        Socket server1 = new Socket(host, 5050);
        Socket server2 = new Socket(host, 5051);

        System.out.println("O cliente se conectou ao servidor!");

        DataInputStream i1 = new DataInputStream(server1.getInputStream());
        DataOutputStream o1 = new DataOutputStream(server1.getOutputStream());

        DataInputStream i2 = new DataInputStream(server2.getInputStream());
        DataOutputStream o2 = new DataOutputStream(server2.getOutputStream());

        String str, opcaoS;
        int escolha = 1;
        int opcaoN = 0, opcaoDZ, opcaoP;//opcao Numero :: opcao Divisao/Zero :: opcao Porcentagem

        do {
            byte[] line = new byte[100];
            System.in.read(line);
            o1.write(line);//tenho que mandar pros 2 caso queira fazer o bye funcionar
            //o2.write(line);
            str = new String(line);
            System.out.println(str.trim());

            if (str.trim().equals("bye")) {
                o2.write(line);
            }

            if (str.trim().equals("Data")) {
                i1.read(line);//recebendo do servidor 1
                str = new String(line);
                System.out.println(str.trim());//mostro o que recebi do servidor 1
            }
            //try{
            if (str.trim().equals("calculadora")) {
                while(escolha != 2) {
                    do{//esse sistema de repetição é prevendo que o cliente digite uma opcao diferente das existentes.
                        try{//corrigindo a entrada de letras
                            System.out.println("Informe a operação desejada: ");
                            System.out.println("1 = +");
                            System.out.println("2 = -");
                            System.out.println("3 = *");
                            System.out.println("4 = /");
                            System.out.println("5 = Porcentagem");
                            System.out.println("6 = Raiz quadrada");
                            System.out.println("7 = Potenciação");
                            System.out.println("0 = SAIR...");
                            opcaoN = scan.nextInt();//tô jogando dentro de uma variavel int para ficar mais facil de manipular, deis transformo em string
                            if (opcaoN < -1 || opcaoN > 8) {
                                System.out.println("Opcao invalida, tente novamente!");
                                //Thread.sleep(2000);//um pequeno delay para visualizar melhor a msg
                            }
                        }catch (InputMismatchException e) {
                            System.out.println("Entrada Inválida. Por favor, entre com um dos números mostrado na tela.");
                            opcaoN = 9;//atribui um valor fora so pra retornar pro loop
                            scan.nextLine();
                            //Thread.sleep(1000);
                        }
                    } while (opcaoN < -1 || opcaoN > 8);

                    scan.nextLine();//limpando o buff para evitar de ser pulado

                    if (opcaoN == 1 || opcaoN == 2 || opcaoN == 3 || opcaoN == 4) {//***aqui eu poderia colocar opcaoN para comparar
                        System.out.print("Informe o primeiro numero: ");
                        str = scan.nextLine() + "\n";

                        //System.out.println("Informe a operacao: ");
                        str = str + Integer.toString(opcaoN) + "\n";//tô concatenando a opcao, que foi escolhida mais acima,direto dentro da string para mostrar se é: + - * /. antes do resultado
                        // ficará: str = 1° numero digitado + opcao
                        //aqui eu irei corrigir a divisao por 0
                        if (opcaoN == 4) {
                            do {
                                System.out.print("Informe o segundo numero: ");
                                opcaoDZ = scan.nextInt();
                                if (opcaoDZ == 0) {
                                    System.out.println("Não é possivel dividir por zero");
                                }
                            } while (opcaoDZ == 0);
                            scan.nextLine();//limpando o buff para evitar de ser pulado
                            //opcaoZ = Integer.toString(opcaoDZ);//transformando o inteiro em Stringo para junta à variavel que será enviada//Não precisei criar
                            str = str + Integer.toString(opcaoDZ) + "\n";//concatenei à varialvel que será enviada
                        }else {
                            System.out.print("Informe o segundo numero: ");
                            str = str + scan.nextLine() + "\n";
                        }
                        //acima estou cirrigindo a divisao por 0

                        String[] arrayDeNomes = str.split("\n");
                        if (opcaoN == 1) {
                            System.out.println(arrayDeNomes[0] + " + " + arrayDeNomes[2]);
                        }
                        if (opcaoN == 2) {
                            System.out.println(arrayDeNomes[0] + " - " + arrayDeNomes[2]);
                        }
                        if (opcaoN == 3) {
                            System.out.println(arrayDeNomes[0] + " * " + arrayDeNomes[2]);
                        }
                        if (opcaoN == 4) {
                            System.out.println(arrayDeNomes[0] + " / " + arrayDeNomes[2]);
                        }

                        line = str.getBytes();
                        o1.write(line);
                        line = new byte[100];
                        i1.read(line);
                        str = new String(line);
                        System.out.println("Resultado: " + str.trim());
                        //Thread.sleep(3000);//um pequeno delay para visualizar melhor o resultado

                    }

                    if(opcaoN == 5) {//Aqui eu tô tratando da porcentagem %
                        str = Integer.toString(opcaoN) + "\n";//to colocando assim para que no servido 2 facilite a verificação de qual conta eu tô fazendo
                        System.out.print("Informe o valor: ");// 1- aqui eu tô pegando o primeiro valor
                        str = str + scan.nextLine() + "\n";

                        do{// 2 - to colhendo a operação de porcentagem que o cliente deseja
                            System.out.println("Informe a operação desejada: ");
                            System.out.println("1 = +");
                            System.out.println("2 = -");
                            System.out.println("3 = *");
                            System.out.println("4 = /");
                            //str = str + scan.nextLine() + "\n";
                            opcaoP = scan.nextInt();//eu tô jogando dentro de um inteiro para ficar mais fácil o tratamento
                            scan.nextLine();//limpando o buff para evitar de ser pulado
                            opcaoS = Integer.toString(opcaoP);//agora eu pego io inteiro e jogo numa string
                            str = str + opcaoS + "\n";//e aqui eu faço a concatenação da opcao escolhida junto à string que será enviada depois que colher a info abaixo

                            if (opcaoP < 0 || opcaoP > 4) {
                                System.out.println("Opcao invalida, tente novamente!");
                                //Thread.sleep(2000);//um pequeno delay para visualizar melhor a msg
                            }
                        }while(opcaoP<0 || opcaoP>4);// 3- tratamento para evitar de escolher uma opcao inválida

                        System.out.println("Informe a porcentagem desejada: "); //4- aqui eu tô colhendo a porcentagem
                        str = str +  scan.nextLine() + "\n";
                        /////////////TÕ trabalhando aqui, o resto antes disso ta funcionando. qualquer coisa é so apagar aqui
                        String[] arrayDeNomes = str.split("\n");
                        if (opcaoS.equals("1")) {System.out.println(arrayDeNomes[1] + " + " + arrayDeNomes[3] + "%");}
                        if (opcaoS.equals("2")) {System.out.println(arrayDeNomes[1] + " - " + arrayDeNomes[3] + "%");}
                        if (opcaoS.equals("3")) {System.out.println(arrayDeNomes[1] + " * " + arrayDeNomes[3] + "%");}
                        if (opcaoS.equals("4")) {System.out.println(arrayDeNomes[1] + " / " + arrayDeNomes[3] + "%");}
                        /////////////
                        //System.out.println(str);//é para o cliente visualizar a conta que ele tá pedindo antes do resultado sair
                        line = str.getBytes();
                        o2.write(line);
                        line = new byte[100];
                        i2.read(line);
                        str = new String(line);
                        System.out.println("Resultado: " + str.trim());

                        str = "calculadora";
                        line = new byte[100];
                        line = str.getBytes();
                        o1.write(line);
                    }
                    if(opcaoN == 6) {
                        str = Integer.toString(opcaoN) + "\n";
                        System.out.println("Informe o numero para saber sua Raiz Quadrada: ");
                        str = str + scan.nextLine();
                        line = str.getBytes();
                        o2.write(line);
                        line = new byte[100];
                        i2.read(line);
                        str = new String(line);
                        System.out.println("Resultado: " + str.trim());

                        str = "calculadora";
                        line = new byte[100];
                        line = str.getBytes();
                        o1.write(line);
                    }
                    if(opcaoN == 7) {
                        str = Integer.toString(opcaoN) + "\n";
                        System.out.println("Informe um número: ");
                        str = str + scan.nextLine() + "\n";
                        System.out.println("Informe a potência desejada: ");
                        str = str + scan.nextLine() + "\n";
                        line = str.getBytes();
                        o2.write(line);
                        line = new byte[100];
                        i2.read(line);
                        str = new String(line);
                        System.out.println("Resultado: " + str.trim());

                        str = "calculadora";
                        line = new byte[100];
                        line = str.getBytes();
                        o1.write(line);
                    }


                    do {
                        /////vou fazer aqui
                        try{
                            if (opcaoN != 0) {
                                System.out.println("Deseja realizar mais alguma operação: ?");
                                System.out.println("1-SIM");
                                System.out.println("2-NÃO");
                                //try {
                                escolha = scan.nextInt();

                                if (escolha != 1 && escolha != 2) {
                                    System.out.println("Opção Inválida, escolha novamente!");
                                    //Thread.sleep(1500);
                                } else if (escolha == 1) {
                                    str = "calculadora";
                                    line = new byte[100];
                                    line = str.getBytes();
                                    o1.write(line);
                                    o2.write(line);
                                } else {//aqui é se escolha for 2, ou seja, optar por sair da calculadora
                                    System.out.println("Calculadora Desligada!");
                                    //Thread.sleep(1000);*/
                                }

                            } else {
                                System.out.println("Calculadora Desligada!");
                                escolha = 2;
                            }
                        }catch (InputMismatchException e) {
                            System.out.println("Entrada Inválida. Por favor, entre com um dos números mostrado na tela.");
                            escolha=3;//to atribuindo um valor para escolha != de 1 e 2 para que caia no loop novamente
                            scan.nextLine();// aqui tá o x da questão para evitar o erro do loop constante
                            //Thread.sleep(1000);//um dalayzinho só pra ver a msg direito
                        }
                        ////vou fazer aqui
                    } while (escolha != 1 && escolha != 2);

                }
                escolha = 0;//zerando escolha para poder entrar novamente
            }

        } while(!str.trim().equals("bye"));
    }
}