package jp.ac.dendai.im.web.exec.kadai7.a;

import java.util.ArrayList;

public class FeedItemViewer {
  public static void main(String[] args) {
    Feed feed = new Feed("https://zenn.dev/feed", "utf-8");
    ArrayList<Item> itemList = feed.getItemList();
    // リストのitem要素を表示
    for(Item item: itemList) {
      System.out.println(item);
      System.out.println();
    }
  }
}
