/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

public class Article {
	
	private String header;
	private String[] words;
	
	public Article(String header, String[] words) {
		this.header = header;
		this.words = words;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}
}
