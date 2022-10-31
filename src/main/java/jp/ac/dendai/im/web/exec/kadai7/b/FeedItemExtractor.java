package jp.ac.dendai.im.web.exec.kadai7.b;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FeedItemExtractor {
  static final String TARGET_KEYWORD = "TypeScript";

  public static void main(String[] args) {
    Feed feed = new Feed("https://zenn.dev/feed", "utf-8");
    List<Item> itemList = feed.getTargetKeywordItemList(TARGET_KEYWORD);
    // リストのitem要素を表示
    for(Item item: itemList) {
      System.out.println(item);
      System.out.println();
    }
    System.out.println("「" + TARGET_KEYWORD + "」の検索結果");
  }
}
