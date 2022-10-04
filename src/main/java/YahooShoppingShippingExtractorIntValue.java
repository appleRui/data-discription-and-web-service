import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.*;

public class YahooShoppingShippingExtractorIntValue {
  public static void main(String[] args) {
    URL url = null;
    try {
      url = new URL("https://store.shopping.yahoo.co.jp/soukai/623922500016.html");
      WebPageParse page = new WebPageParse(url);
      page.printPostage();
    } catch (MalformedURLException e) {
      System.err.println("Wrong URL: " + url);
    }
  }
}

class WebPageParse {
  private final URL url;

  WebPageParse(URL url) {
    this.url = url;
  }

  private boolean isPostage(String line) {
    Pattern pattern = Pattern.compile("送料.+円");
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  private boolean isFreeShipping(String line) {
    Pattern pattern = Pattern.compile("送料無料");
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  public void printPostage() {
    int printPostage = -1;
    try {
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
      BufferedReader fin = new BufferedReader(
              new InputStreamReader(
                      urlConnection.getInputStream(), StandardCharsets.UTF_8));
      String line;
      while ((line = fin.readLine()) != null)
        // 送料と円と記述がある場合、printPostageをその数値を代入する
        if(isPostage(line)) {
          Pattern pattern = Pattern.compile("(送料)(.+)(円)");
          Matcher matcher = pattern.matcher(line);
          if(matcher.find()){
            String postageToString = matcher.group(2);
            try {
              Number number = NumberFormat.getInstance().parse(postageToString);
              printPostage = number.intValue();
              break;
            } catch (ParseException e) {
              e.printStackTrace();
            }
          };
        }
        // 送料が無料の時は、printPostageを0を代入する
        if(isFreeShipping(String.valueOf(line!= null))){
          printPostage = 0;
        }
      System.out.println(printPostage);
    } catch (IOException e) {
      System.err.println("I/O Error: " + e.toString());
    }
  }
}
