package it.its.alberghi.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import it.its.alberghi.entity.Hotel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.LogManager;

public class HotelHandler implements HttpHandler  {

    private static List<Hotel> hotelList = Arrays.asList(
            new Hotel("Hotel A", 100, 200),
            new Hotel("Hotel B", 150, 250),
            new Hotel("Hotel C", 120, 180)
    );




    private String listAllHotels() {
        StringBuilder response = new StringBuilder();
        for (Hotel hotel : hotelList) {
            response.append(hotel).append("\n");
        }
        return response.toString();
    }

    private String listAllHotelsSortedByPrice() {
        List<Hotel> sortedList = new ArrayList<>(hotelList);
        sortedList.sort(Comparator.comparingInt(Hotel::getPrice));
        StringBuilder response = new StringBuilder();
        for (Hotel hotel : sortedList) {
            response.append(hotel).append("\n");
        }
        return response.toString();
    }

    private String findMoreExpensiveSuite() {
        Optional<Hotel> mostExpensiveSuite = hotelList.stream()
                .max(Comparator.comparingInt(Hotel::getSuitePrice));
        return mostExpensiveSuite.map(hotel -> "Hotel: " + hotel.getName() + ", Suite Price: " + hotel.getSuitePrice())
                .orElse("No hotel with a suite found");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")) {
            String query = exchange.getRequestURI().getQuery();
            String response;
            if (query != null) {
                switch (query) {
                    case "all":
                        response = listAllHotels();
                        break;
                    case "all_sorted":
                        response = listAllHotelsSortedByPrice();
                        break;
                    case "more_expensive_suite":
                        response = findMoreExpensiveSuite();
                        break;
                    default:
                        response = "Invalid command";
                        break;
                }
            } else {
                response = "No command specified";
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Request processed");
            System.out.println("Response: " + response);
        } else {
            String response = "Unsupported request method";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Unsupported request method");
        }
    }


}