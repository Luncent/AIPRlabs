package org.example.Client;

public class WebServiceClient {
    public static void main(String[] args) {
        try
        {
            ServiceClassService client = new ServiceClassService();

            ServiceClass ws = client.getServiceClassPort();
            String s=ws.bonjour("Dima");
            System.out.println("Answer: " + s);
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }
}


