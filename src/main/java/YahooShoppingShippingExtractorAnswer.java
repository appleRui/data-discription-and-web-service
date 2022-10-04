import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Yahoo!ショッピングの商品情報から送料を抽出
 */
public class YahooShoppingShippingExtractorAnswer {
  public static void main(String[] args) {
    try {
      // URLオブジェクトを生成
      URL url = new URL("https://store.shopping.yahoo.co.jp/soukai/623922500016.html");  // メープルシロップ
      // URLオブジェクトから、接続にいくURLConnectionオブジェクトを取得
      URLConnection connection = url.openConnection();
      // 接続
      connection.connect();
      // サーバからやってくるデータをInputStreamとして取得
      InputStream inputStream = connection.getInputStream();
      // 次に inputStream を読み込む InputStreamReader のインスタンス inputStreamReader を生成
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
      // さらに inputStreamReader をラップする BufferedReader のインスタンス reader を生成
      BufferedReader reader = new BufferedReader(inputStreamReader);
      //BufferedReader reader = new BufferedReader(new FileReader(filename));
      String message = "送料に関する情報がありません。";
      String line;
      while ((line = reader.readLine()) != null) {		// readLine() が null (=ページ終端) でない間
        int beginIndex = line.indexOf("送料");    // line の中で「送料」が最初に現れる位置
        if(beginIndex != -1) {    // line の中に「送料」が含まれていたら
          beginIndex += "送料".length();	// 開始位置を「送料」の後ろに移動
          int endIndex = line.indexOf("円", beginIndex);		// beginIndex文字目以降の「円」の現れる位置
          if(endIndex != -1) {	// line の中に「円」が含まれていたら (「送料」の後ろで)
            message = "送料は " + line.substring(beginIndex, endIndex) + " 円です。";
            break;	// 送料についての1回目の記述が抽出できたら終了
          }
          else if(line.contains("送料無料")) {
            message = "送料は無料っぽいです。";    // 「合計10,000円以上で送料無料」とかあるので自信なし
            // この後に明確な送料の記述があるかもしれないので break しない
          }
        }
      }
      reader.close();
      System.out.println(message);   // 送料に関する情報を表示
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
