package task_4.example;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {

    private static void readContent(String url) throws IOException {

        Object content = new URL(url).getContent();

        if (content instanceof InputStream) {
            InputStream contentStream = (InputStream) content;
            try (Scanner contentScanner = new Scanner(contentStream)) {
                while (contentScanner.hasNext()) {
                    System.out.println(contentScanner.next());
                }
            }
        } else {
            throw new IOException("Unsupported content kind");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Type url");
            String url = scanner.next();

            try {
                readContent(url);
                break;
            } catch (MalformedURLException e) {
                System.out.println("Malformed URL specified!");
            } catch (UnknownHostException e) {
                System.out.println("Unknown host!");
            } catch (IOException e) {
                System.out.println("Unable to get site content. " + e.getMessage());
            }
            System.out.println("Try again");
        } while (true);
    }
}