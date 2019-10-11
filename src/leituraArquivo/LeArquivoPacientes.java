package leituraArquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeArquivoPacientes {
    private String pacienteNome;
    private String pacienteSexo;
    private LocalDate pacienteDataNascimento;
    private int pacienteIdade;
    private double pacienteAltura;
    private double pacientePeso;
    private double pacienteIMC;
    private String dataAtual;

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getPacienteSexo() {
        return pacienteSexo;
    }

    public void setPacienteSexo(String pacienteSexo) {
        this.pacienteSexo = pacienteSexo;
    }

    public LocalDate getPacienteDataNascimento() {
        return pacienteDataNascimento;
    }

    public void setPacienteDataNascimento(LocalDate pacienteDataNascimento) {
        this.pacienteDataNascimento = pacienteDataNascimento;
    }

    public int getPacienteIdade() {
        return pacienteIdade;
    }

    public void setPacienteIdade(int pacienteIdade) {
        this.pacienteIdade = pacienteIdade;
    }

    public double getPacienteAltura() {
        return pacienteAltura;
    }

    public void setPacienteAltura(double pacienteAltura) {
        this.pacienteAltura = pacienteAltura;
    }

    public double getPacientePeso() {
        return pacientePeso;
    }

    public void setPacientePeso(double pacientePeso) {
        this.pacientePeso = pacientePeso;
    }

    public double getPacienteIMC() {
        return pacienteIMC;
    }

    public void setPacienteIMC(double pacienteIMC) {
        this.pacienteIMC = pacienteIMC;
    }

    public String getDataAtual() {
        LocalDate hoje = LocalDate.now();
        return hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
       
    public void calcularIdadePaciente(){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataNascimento = getPacienteDataNascimento();
        int idade;
        
        idade = Period.between(dataNascimento, dataAtual).getYears();
        setPacienteIdade(idade);
    }
    
    public void calcularIMC(){
        this.pacienteIMC = pacientePeso/(pacienteAltura * pacienteAltura);
    }
     
    public static void main(String[] args) {
        LeArquivoPacientes leitura = new LeArquivoPacientes();
        File filename = new File ("pacientes.txt");
         LocalDate dt = LocalDate.now();
        
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader in = new BufferedReader(fr);
            String line = in.readLine();
            int cont = 0;
            while (line != null) {
                cont++;
                System.out.println(line);
                
                if (cont == 1){
                    leitura.setPacienteNome(line);
                }
                
                if (cont == 2){
                    leitura.setPacienteSexo(line);
                }
                
                if (cont == 3){
                    dt = LocalDate.parse(line, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    leitura.setPacienteDataNascimento(dt);
                    leitura.calcularIdadePaciente();
                }
                
                if (cont == 4) {
                    leitura.setPacienteAltura(Double.parseDouble(line));
                }
                
                if (cont == 5){
                    leitura.setPacientePeso(Double.parseDouble(line));
                    try{
                        File f1 = new File(leitura.getPacienteNome()+".txt");
                        FileWriter fr2 = new FileWriter(f1);
                        PrintWriter out = new PrintWriter(fr2);
                        out.println("Nome: " + leitura.getPacienteNome() + "\n" +
                                    "Idade: " + leitura.getPacienteIdade() +" anos (em " + leitura.getDataAtual() + ")" + "\n" +
                                    "Sexo: " + leitura.getPacienteSexo());
                        leitura.calcularIMC();
                        out.printf("IMC: %.3f", leitura.getPacienteIMC() );
                        out.close();
                    } catch (IOException e){
                        System.out.println("Erro ao escrever arquivo.");
                    }
                    cont = 0;
                }
                line = in.readLine();
            }
        in.close();
        } catch(FileNotFoundException e){
                System.out.println("Arquivo \""+filename+"\" não existe.");
        } catch (IOException e) {
                System.out.println("Erro na leitura do arquivo " + filename +".");
        }       
    }
}
