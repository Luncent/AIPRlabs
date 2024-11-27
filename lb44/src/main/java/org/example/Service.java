package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {
    private static final String directoryPath = System.getProperty("user.dir")+"\\files";

    @WebMethod(operationName = "getFiles")
    public String[] getFiles() throws IOException {
        File directory = new File(directoryPath);
        Path path = Paths.get(directoryPath);

        List<String> files = Files.list(path)
                .map(el->el.getFileName().toString())
                .collect(Collectors.toList());

        int filesQuantity = files.size();
        String[] filesArr = new String[filesQuantity];
        for(int i=0; i<filesQuantity; i++){
            filesArr[i]=files.get(i);
        }
        return filesArr;
    }
    @WebMethod(operationName = "getFileContent")
    public String getFileContent(String fileName){
        try(BufferedReader br =new BufferedReader(new FileReader (directoryPath+"\\"+fileName))) {
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            return sb.toString();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @WebMethod(operationName = "calc")
    public double calc(String expression) throws Exception {
        List<Double> numbers = new LinkedList<>();
        List<Character> operations = new LinkedList<>();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<expression.length(); i++){
            Character character = expression.charAt(i);
            boolean isOperation = character.equals('+') || character.equals('-') || character.equals('/')
                    || character.equals('*');
            if(isOperation){
                operations.add(character);
                try {
                    numbers.add(Double.valueOf(sb.toString()));
                } catch (Exception ex) {
                    throw new Exception("Введены некорректные символы");
                }
                sb.delete(0, sb.length());
            }
            else {
                sb.append(character);
            }
        }
        try {
            numbers.add(Double.valueOf(sb.toString()));
        } catch (Exception ex) {
            throw new Exception("Введено некорректное выражение");
        }

        while(operations.size()!=0){
            int index = 0;
            if(operations.contains('*') || operations.contains('/')){
                Iterator<Character> iter = operations.iterator();
                while(iter.hasNext()){
                    index++;
                    Character operation = iter.next();
                    if(operation.equals('*')){
                        Double multiply = numbers.get(index-1)* numbers.get(index);
                        numbers.add(index-1,multiply);
                        //2 раза тк результат вставляется перед 2 числами над которыми сов операция
                        numbers.remove(index);
                        numbers.remove(index);
                        iter.remove();
                        index=0;
                        break;
                    }
                    else if(operation.equals('/')){
                        Double division = numbers.get(index-1)/ numbers.get(index);
                        numbers.add(index-1,division);
                        //2 раза тк результат вставляется перед 2 числами над которыми сов операция
                        numbers.remove(index);
                        numbers.remove(index);
                        iter.remove();
                        index=0;
                        break;
                    }
                }
            }
            else if(operations.contains('+') || operations.contains('-')){
                Iterator<Character> iter = operations.iterator();
                while(iter.hasNext()){
                    index++;
                    Character operation = iter.next();
                    if(operation.equals('+')){
                        Double summ = numbers.get(index-1)+ numbers.get(index);
                        numbers.add(index-1,summ);
                        //2 раза тк результат вставляется перед 2 числами над которыми сов операция
                        numbers.remove(index);
                        numbers.remove(index);
                        iter.remove();
                        index=0;
                        break;
                    }
                    else if(operation.equals('-')){
                        Double subtraction = numbers.get(index-1)- numbers.get(index);
                        numbers.add(index-1,subtraction);
                        //2 раза тк результат вставляется перед 2 числами над которыми сов операция
                        numbers.remove(index);
                        numbers.remove(index);
                        iter.remove();
                        index=0;
                        break;
                    }
                }
            }
        }
        return numbers.get(0);
    }

}
