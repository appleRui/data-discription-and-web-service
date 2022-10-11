import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Yahoo!ショッピングの商品情報から送料を抽出 (正規表現を利用)
 */
public class YahooShoppingShippingExtractorIntValueAnswer {
  public static void main(String[] args) {
    try {
      // URLオブジェクトを生成
      URL url = new URL("https://store.shopping.yahoo.co.jp/nomimon/10003093.html");  // アイス
      // URLオブジェクトから、接続にいくURLConnectionオブジェクトを取得
      URLConnection connection = url.openConnection();
      // 接続
      connection.connect();
      // サーバからやってくるデータをInputStreamとして取得
      InputStream inputStream = connection.getInputStream();
      // 次に inputStream を読み込む InputStreamReader のインスタンス inputStreamReader を生成
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      // さらに inputStreamReader をラップする BufferedReader のインスタンス reader を生成
      BufferedReader reader = new BufferedReader(inputStreamReader);
      //BufferedReader reader = new BufferedReader(new FileReader(filename));

      Pattern pattern = Pattern.compile("送料([0-9,]+)円");  // グループの指定が1つ(1番目)
      int shipping = -1;  // 送料 (見つからなかったら -1 のまま)
      String line;
      while ((line = reader.readLine()) != null) {		// readLine() が null (=ページ終端) でない間
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()) {  // パターンにマッチしたら
          String numberString = matcher.group(1);   // 1番目のグループにマッチした文字列を取り出す
          try {
            Number number = NumberFormat.getInstance().parse(numberString);
            shipping = number.intValue();
            break;
          } catch (ParseException e) {
            System.err.println("数値のフォーマットに適合しません: " + numberString);
          }
        }
        else if(line.contains("送料無料")) {
          shipping = 0;   // 「合計10,000円以上で送料無料」とかあるので自信なし
          // この後に明確な送料の記述があるかもしれないので break しない
        }
      }
      reader.close();
      // 送料に関する情報を表示
      if(shipping > 0) {
        System.out.println("送料は " + NumberFormat.getInstance().format(shipping) + " 円です。");	// 「,」ありで表示
      }
      else if(shipping == 0) {
        System.out.println("送料は無料っぽいです。");
      }
      else {
        System.out.println("送料に関する情報がありません。");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
