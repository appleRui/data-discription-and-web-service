import java.io.*;
import java.net.*;

public class YahooShoppingShippingExtractor {
  public static void main(String[] args) {
    try {
      URL url = new URL("https://paypaymall.yahoo.co.jp/store/sunnapstore/item/cd-7db/?sc_i=shp_pc_top_history");
      Webpage page = new Webpage(url);
      page.printPostage();
    }
    catch(MalformedURLException e) {
      System.err.println("Wrong URL: " + args[0]);
    }
  }
}

class Webpage {
  private URL url;

  Webpage(URL url) {
    this.url = url;
  }

  public void printPostage() {
    try {
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
      BufferedReader fin = new BufferedReader(
              new InputStreamReader(
                      urlConnection.getInputStream(), "utf-8"));
      String line;
      while((line = fin.readLine()) != null)
        if(line.contains("送料") && line.contains("円")) {
          int startIndex = line.indexOf("送料");
          int endIndex = line.indexOf("円");
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
          System.out.println(line.substring(startIndex, endIndex+1));
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
          return;
        }
    }
    catch(IOException e) {
      System.err.println("I/O Error: " + e.toString());
    }
  }
}
