package com.servervvs.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HttpConnectionWorkerThread extends Thread {
    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);


    public HttpConnectionWorkerThread(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run(){
        InputStream inputStream =null;
        OutputStream outputStream =null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

//            int _byte;
//            while((_byte= inputStream.read()) >= 0){
//                System.out.println((char) _byte);
//            }
            String data = "";
            try {
                File file = new File("src\\main\\resources\\a.html");
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    data += myReader.nextLine();

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            //String html = "<html><head>gsggshs<title></title></head><body><h1>hahdhj</h1></body></html>";
            final String CRLF = "\n\r";
            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content Length:" + data.getBytes().length + CRLF +
                            CRLF +
                            data +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());


            LOGGER.info("Connection processing finished");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Problem with communication",e);
        }finally {
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

