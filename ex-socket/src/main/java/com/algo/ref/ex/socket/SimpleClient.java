package com.algo.ref.ex.socket;

import java.io.*;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost", 8080);
        BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        OutputStreamWriter out=new OutputStreamWriter(socket.getOutputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String msg="hello world from simple socket client\n";
        out.write(msg);
//        out.flush();
        msg="Done";
        out.write(msg);
//        out.flush();
        String response;
        response=input.readLine();
        System.out.println(response);
        socket.close();
    }
}
