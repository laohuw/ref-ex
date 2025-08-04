package com.algo.ref.loki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class Main {
    private static final Logger logger= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        var template = "'{' \"streams\": ['{' \"stream\": '{' \"app\": \"{0}\" '}', \"values\": [[ \"{1}\", \"{2}\" ]]'}']'}'"; //1
        var now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        var nowInEpochNanos = NANOSECONDS.convert(now.getEpochSecond(), SECONDS) + now.getNano();
        var payload = MessageFormat.format(template, "demo", String.valueOf(nowInEpochNanos), "Hello from Java App");           //1
        var request = HttpRequest.newBuilder()                                                                                  //2
                .uri(new URI("http://10.152.183.47:3100/loki/api/v1/push"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());                                         //3

        logger.info("Hello and welcome!");
        for (int i = 1; i <= 5; i++) {
            logger.info("i = " + i);
        }


    }
}