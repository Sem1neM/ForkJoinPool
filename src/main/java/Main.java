import java.io.*;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static String SITE_URL = "https://lenta.ru/";
    private static final String SITEMAP_DOC = "src/main/resources/result.txt";

    public static void main(String[] args) {

        Link rootUrl = new Link(SITE_URL);
        new ForkJoinPool().invoke(new LinkRecursiveTask(rootUrl,rootUrl));
        appendStringInFile(SITEMAP_DOC,createSitemapString(rootUrl,0));
    }

    public static String createSitemapString(Link node, int depth) {
        String tabs = String.join("", Collections.nCopies(depth, "\t"));
        StringBuilder result = new StringBuilder(tabs + node.getUrl());
        node.getChildren().forEach(child -> {
            result.append("\n").append(createSitemapString(child, depth + 1));
        });
        return result.toString();
    }

    private static void appendStringInFile(String fileName, String data) {
        try {
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(data.getBytes());
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
