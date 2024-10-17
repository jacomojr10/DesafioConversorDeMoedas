import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.IllegalFormatConversionException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner opcaoMenu = new Scanner(System.in);

        System.out.println("Digite a opção de câmbio desejada: ");
        System.out.println("1. Dólar -> Real");
        System.out.println("2. Real -> Dólar");
        System.out.println("3. Real -> Euro");
        System.out.println("4. Euro -> Dólar");
        System.out.println("5. Peso Argentino -> Dólar");
        System.out.println("6. Real -> Yen");
        System.out.println("7. Sair");

        int opcao = opcaoMenu.nextInt();

        //public void buscaTaxa(double conversion_rate) throws IOException, InterruptedException {


        String endereco = "";

        if (opcao == 1) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/USD/BRL";
        } else if (opcao == 2) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/BRL/USD";
        } else if (opcao == 3) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/BRL/EUR";
        } else if (opcao == 4) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/EUR/USD";
        } else if (opcao == 5) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/ARS/USD";
        } else if (opcao == 6) {
            endereco = "https://v6.exchangerate-api.com/v6/74a751f4f1151a23ae7f3b3b/pair/BRL/JPY";
        } else if (opcao >= 7) {
            System.out.println("Programa finalizado");
            System.exit(0);
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new Gson();
            TaxaDeConversaoExchange minhaTaxaDeConversaoExchange = gson.fromJson(json, TaxaDeConversaoExchange.class);

            double taxaConversao = minhaTaxaDeConversaoExchange.getConversion_rate();
            System.out.println("A taxa de câmbio é: " + taxaConversao);

            System.out.println("Por favor, digite o valor a ser convertido");
            double valor = opcaoMenu.nextDouble();

            double valorConvertido = converterMoeda(valor, taxaConversao);
            System.out.println("Valor convertido: " + valorConvertido);


            }catch (NumberFormatException e) {
            System.out.println("Formato de número errado, digite um núemro válido.");

            } catch (InputMismatchException e) {
            System.out.println("Não aceita digitação de letra, por favor, digite o número da opção desejada.");
            }

    }
    public static double converterMoeda ( double valor, double taxa){
        return valor * taxa;
    }

}


