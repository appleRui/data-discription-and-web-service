package jp.ac.dendai.im.web.exec.kadai7.b;

/**
 * フィードの item 要素
 */
public class Item {
	/** id要素 */
	private Integer id;
	/** title要素 */
	private String title;
	/** link要素 */
	private String link;
	/** description要素 */
	private String description;
	/** creator要素 */
	private String creator;

	/**
	 *  コンストラクタ
	 *  @param title タイトル
	 *  @param link リンク
	 *  @param description 説明の文章
	 *  @param creator 作成者
	 */
	public Item(int id, String title, String link, String description, String creator) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		this.creator = creator;
	}
	/**
	 *  title要素の内容である item のタイトルを返す
	 *  @return タイトル
	 */
	public String getTitle() {
		return title;
	}
	/**
	 *  link要素の内容である URL を文字列で返す
	 *  @return リンク先のURL
	 */
	public String getLink() {
		return link;
	}
	/**
	 *  description要素の内容である説明の文章を返す
	 *  @return 説明の文章
	 */
	public String getDescription() {
		return description;
	}
	/**
	 *  この item 要素の文字列表現を返す
	 *  @return この item 要素の文字列表現
	 */
	@Override
	public String toString() {
		return "id: " + this.id + "\ntitle: " + title + "\nlink: " + link + "\ndescription: " + description+ "\ncreator: " + creator;
	}
}
