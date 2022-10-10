
import data.Locacao;
import data.NorthCar;
import data.SouthCar;
import data.WestCar;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws ParseException {
        
        Scanner input = new Scanner(System.in); //crio leitor para entrada do usuário
        
        Locacao locacao = new Locacao();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatar datas

        //criação de variáveis para receber inputs do usuário
        double numPassageiros;
        
        String dataInicio, dataFim;
        
        System.out.print("Insira a data inicial (xx/xx/xxxx): ");
        dataInicio = input.next();
        
        LocalDate dataInicial =  LocalDate.parse(dataInicio, formatter);
        
        System.out.print("Insira a data final (xx/xx/xxxx) : ");
        dataFim = input.next();
        
        LocalDate dataFinal = LocalDate.parse(dataFim, formatter);
        
        System.out.print("Insira o numero de passageiros: ");
        numPassageiros = input.nextInt();
        
        
        ///crio objetos das classes das locadoras e defino preços e capacidade para cada um
        SouthCar southCar = new SouthCar();
        WestCar westCar = new WestCar();
        NorthCar northCar = new NorthCar();
        
        //seta preços SouthCar
        southCar.setPrecoSemana(210);
        southCar.setPrecoFimSemana(200);
        southCar.setCapacidade(4);
        southCar.setQtdCarros(Math.ceil(numPassageiros/southCar.getCapacidade()));
        
        //seta preços WestCar
        westCar.setPrecoSemana(530);
        westCar.setPrecoFimSemana(200);
        westCar.setCapacidade(2);
        westCar.setQtdCarros(Math.ceil(numPassageiros/westCar.getCapacidade()));
        
        //seta preços NorthCar
        northCar.setPrecoSemana(630);
        northCar.setPrecoFimSemana(600);
        northCar.setCapacidade(7);
        northCar.setQtdCarros(Math.ceil(numPassageiros/northCar.getCapacidade()));
        
        System.out.println("Quantidade de veiculos necessarios: " + southCar.getQtdCarros());
   
        
        //calculo diferença de dias entre duas datas LocalDate com classe ChronoUnit
        locacao.setTotalDias(ChronoUnit.DAYS.between(dataInicial, dataFinal));
              
        List<LocalDate> listaDiaSemana = new ArrayList<>(); //crio array list que terá datas entre dataInicial e dataFinal
        
        //preencho ArrayList com dias que são de semana (diferente de fins de semana)
        for (LocalDate data = dataInicial; !data.isAfter(dataFinal); data = data.plusDays(1)) {
            DayOfWeek diaSemana = data.getDayOfWeek();
            
            if (!(diaSemana.equals(DayOfWeek.SATURDAY) || diaSemana.equals(DayOfWeek.SUNDAY))) {
                listaDiaSemana.add(data);
            }
        }
        
        locacao.setTotalDiasSemana(listaDiaSemana.size());  //calcula total de dias de semana
        
        locacao.setTotalDiasFimSemana(locacao.getTotalDias() - locacao.getTotalDiasSemana());  //calcula total dias fds
        
        System.out.println("Total de dias da locacao: " + locacao.getTotalDias());
        //System.out.println("totalDiasSemana: " + locacao.getTotalDiasSemana());
        //System.out.println("totalDiasFimSemana: " + locacao.getTotalDiasFimSemana());  
        
        //calculo totais para locação em cada locadora
        locacao.setTotalSouth( southCar.getQtdCarros() * ((locacao.getTotalDiasSemana() * southCar.getPrecoSemana()) + (locacao.getTotalDiasFimSemana() * southCar.getPrecoFimSemana())) );
        locacao.setTotalWest( westCar.getQtdCarros() * ((locacao.getTotalDiasSemana() * westCar.getPrecoSemana()) + (locacao.getTotalDiasFimSemana() * westCar.getPrecoFimSemana())) );
        locacao.setTotalNorth( northCar.getQtdCarros() * ((locacao.getTotalDiasSemana() * northCar.getPrecoSemana()) + (locacao.getTotalDiasFimSemana() * northCar.getPrecoFimSemana())) );
        
        Map mapLocacoes = new HashMap(); // crio HashMap para abrigar locadora e preço da locação
        
        //adiciono somatórias ao HashMap
        mapLocacoes.put("SouthCar", locacao.getTotalSouth());
        mapLocacoes.put("WestCar", locacao.getTotalWest());
        mapLocacoes.put("NorthCar", locacao.getTotalNorth());
                
        System.out.println("Cotacoes: " + mapLocacoes);
        
        System.out.println(achaMelhorLocacao(mapLocacoes));
    }
    
    //crio método para iterar o hashmap e achar a melhor locadora baseado no menor valor
    public static String achaMelhorLocacao(Map<String,Double>mapLocacoes){
        
        double melhorValor = (Collections.min(mapLocacoes.values())); //acha o menor valor no hashmap
        
        for (Map.Entry <String, Double> locadora:mapLocacoes.entrySet() ) { //itera até valor ser igual ao menor valor e retorna a chave
            if (locadora.getValue() == melhorValor ) {
                return "A melhor cotacao encontrada foi: " + locadora.getKey() + " : " + locadora.getValue();
            }
        }
        return null;
    }

       
}
