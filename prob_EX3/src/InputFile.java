/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class InputFile {

	private ArrayList<Article> articles;
	private ArrayList<String> events; // All events
	private int m_size = 0; // Number of events
	private BufferedReader m_br;
	private final String REGEX_SPLIT = " ";
	
	public InputFile(String filename) {
		try {
			this.m_br = new BufferedReader(new FileReader(filename));
			this.m_size = 0;
			this.parseFile();
			this.m_br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFile() {
		this.articles = new ArrayList<Article>();
		this.events = new ArrayList<String>();
		
		String line = null;
		int counter = 0;
		String header = null;
		try {
			while ((line = this.m_br.readLine()) != null) {
				
				// Not an empty line
				if (!"".equals(line)) {
					counter++;
					
					if (counter % 2 == 1) { // Header
						header = line;
					} else { // Not an header
						String[] words = line.split(REGEX_SPLIT);
						this.m_size += words.length;
						int length = words.length;
						for (int i = 0; i < length; i++) {
							this.events.add(words[i]);
						}
						this.articles.add(new Article(header, words));
					}
				}
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Article> getArticles() {
		return articles;
	}
	
	public ArrayList<String> getEvents() {
		return events;
	}
	
	public int getSize() {
		return (this.m_size);
	}
}
