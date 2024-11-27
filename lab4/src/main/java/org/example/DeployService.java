package org.example;

import javax.xml.ws.Endpoint;

public class DeployService {

    public static void main(String[] args) {
        String bindingURI = "http://localhost:8078/MyService";
        ServiceClass webService = new ServiceClass();
        Endpoint.publish(bindingURI, webService);
        System.out.println("Server started at: " + bindingURI);
    }
}
