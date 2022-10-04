package jp.ac.dendai.im.web.dataDescriptionAndWebService;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class TagRemover {
  public static void main(String[] args) {
    try {
      // ネットワーク接続の準備
      URL url = new URL("https://store.shopping.yahoo.co.jp/soukai/623922500016.html");
      URLConnection connection = url.openConnection();
      connection.connect();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

      // 1行ごとにタグ除去
      Pattern pattern = Pattern.compile("<.+?>");
      String line;
      while ((line = reader.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        String plainText = matcher.replaceAll("");
        if(plainText.length() != 0) {        // 空文字列はスキップ
          System.out.println(plainText.trim());
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}