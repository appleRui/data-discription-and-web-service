import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Yahoo!ショッピングの商品
 */
public class YahooShoppingItem {  // ソートする場合は Comparable インタフェースを実装する
  // フィールド、アクセサメソッドを追加
  public String title = null;
  public Integer price = null;
  public Integer shipping = null;

  /**
   *  コンストラクタ
   *  @param urlString Yahoo!ショッピングの商品ページのURL
   */
  public YahooShoppingItem(String urlString) {
    fetch(urlString);
  }

  public String getTitle() {
    return this.title;
  }

  public Integer getPrice() {
    return this.price;
  }

  public Integer getShipping() {
    return this.shipping;
  }

  /**
   * Yahoo!ショッピングの商品情報の取得
   * @param urlString Yahoo!ショッピングの商品ページのURL
   */
  public void fetch(String urlString) {
    try {
      URL url = new URL(urlString);
      URLConnection connection = url.openConnection();
      connection.connect();
      InputStream inputStream = connection.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader reader = new BufferedReader(inputStreamReader);

      // 抽出のためのパターン
      Pattern patternTitle = Pattern.compile("(<p class=\"elName\">)(.+)(</p>)");
      Pattern patternPrice = Pattern.compile("(span class=\"elPriceNumber\">)(.+)(</span>)");
      Pattern patternShipping = Pattern.compile("(span class=\"elPostageValue\">送料)(.+)(円</span>)");

      String line;
      while ((line = reader.readLine()) != null) {
        Matcher matcherTitle = patternTitle.matcher(line);
        Matcher matcherPrice = patternPrice.matcher(line);;
        Matcher matcherShipping = patternShipping.matcher(line);;
        // 商品名の探索
        if(matcherTitle.find() && this.title == null) {
          this.title = matcherTitle.group(2);
          continue;
        }
        // 価格の探索
        if(matcherPrice.find()) {
          var priseToString = matcherPrice.group(2);
          try {
            Number number = NumberFormat.getInstance().parse(priseToString);
            this.price = number.intValue();
            continue;
          } catch (ParseException e) {
            System.err.println("数値のフォーマットに適合しません: " + priseToString);
          }
        }
        // 送料の探索
        if(matcherShipping.find()) {
          var shippingToString = matcherShipping.group(2);
          try {
            Number number = NumberFormat.getInstance().parse(shippingToString);
            this.shipping = number.intValue();
            continue;
          } catch (ParseException e) {
            System.err.println("数値のフォーマットに適合しません: " + shippingToString);
          }
          System.out.println(line);
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 動作確認用 */
  public static void main(String[] args) {
    // 商品を格納するリストを用意
    ArrayList<YahooShoppingItem> itemList = new ArrayList<YahooShoppingItem>();

    // リストに商品を追加
    itemList.add(new YahooShoppingItem("https://store.shopping.yahoo.co.jp/soukai/623922500016.html"));	// メープルシロップ
    itemList.add(new YahooShoppingItem("https://store.shopping.yahoo.co.jp/nomimon/10003093.html"));	// アイス
    // リストのソート (価格の安い順)
    // Collections.sort(itemList);

    // リストの全商品を表示
    for(YahooShoppingItem item: itemList) {
      System.out.println("商品名: " + item.getTitle());
      System.out.println("価格: " + item.getPrice() + " 円");
      System.out.println("送料: " + item.getShipping() + " 円");
      System.out.println();
    }
  }
}
