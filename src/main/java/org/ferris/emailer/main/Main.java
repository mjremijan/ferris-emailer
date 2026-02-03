package org.ferris.emailer.main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Michael
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Ferris TV Premieres Calendar!");

        HttpResponse<String> response = getHtml();

        Document doc = Jsoup.parse(response.body());

        // Select <section class="calendar"> (class match)
        var section = doc.selectFirst("section.calendar");
        
        // Get dates 2 weeks into the future
        var dates = getDatesTwoWeeksIntoTheFuture();
        
        System.out.printf("**************************************%n");
        String key = null;
        for (Element c : section.children()) {
            if (c.tagName().equalsIgnoreCase("h6")) {
                key = dates.containsKey(c.html()) ? c.html() : null;
            }
            else
            if (c.tagName().equalsIgnoreCase("a") && key!=null) {
                dates.replace(key, dates.get(key) + c.outerHtml());
            }
        }
        
        System.out.printf("////////////////////////////////////////////%n");
        dates.entrySet().stream()
        .forEach(e -> {
            System.out.println(e.getKey() + " = " + e.getValue());
        });
         
        Path javaHome = Path.of(System.getProperty("java.home")).toAbsolutePath();
        System.out.println("java.home = " + javaHome);

            

    }
    
    private static LinkedHashMap<String,String> getDatesTwoWeeksIntoTheFuture() {
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        
        var map = new LinkedHashMap<String, String>();
        LocalDate now = LocalDate.now();
        for (int i=0; i<14; i++) {
            map.put(f.format(now.plusDays(i)), "");
        }
        System.out.printf("map %d%n", map.size());
        return map;
    }

    private static HttpResponse<String> getHtml() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.tvinsider.com/shows/calendar/"))
                .GET()
                .build();

        HttpResponse<String> response
                = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        return response;
    }
}
