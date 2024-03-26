package it.its.alberghi;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import it.its.alberghi.server.HotelHandler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        int portNumber = 1234;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(portNumber), 0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        server.createContext("/", new HotelHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server listening on port " + portNumber + "...");
    }
}