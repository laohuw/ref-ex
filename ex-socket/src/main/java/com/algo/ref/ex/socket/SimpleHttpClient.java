package com.algo.ref.ex.socket;

import javax.annotation.RegEx;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public class SimpleHttpClient {
    public static void main(String[] args) throws IOException {
        int port=8089;
        String host="localhost";
        String service="/department/list";
        get(host, port, service);
    }

    private static void get(String host, int port, String service) throws IOException {
//        Pattern.compile("http:\\/\\/([^\\/]+)[:(d+)][\\/(.+)]");
        Socket socket=new Socket(host, port);
        BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter out=new OutputStreamWriter(socket.getOutputStream());
        String msg="GET "+service+" HTTP/1.1\r\nHost: developer.mozilla.org\r\n\r\n";
        out.write(msg);
        out.flush();
        String response;
        response="i";
        while(response.length()>=0) {
            response = input.readLine();
            System.out.println(response);
        }
        socket.close();
    }

}
