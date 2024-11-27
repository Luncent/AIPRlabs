package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Service {
    public static void main(String[] args) throws IOException {
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
        System.out.println(Arrays.toString(filesArr));
    }

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
}
