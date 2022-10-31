package jp.ac.dendai.im.web;

public class For {
  public static void main(String[] args) {
    for (int i = 1; i < 10; i++){
      if (i % 3 == 0){
        continue;
      }
      System.out.println("i = " + i);
    }
    System.out.println("End");
  }
}
