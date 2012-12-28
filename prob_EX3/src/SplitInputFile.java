/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class handles a splitter set
 * @author Moshe Hazoom & Slava Bronfman
 *
 */
public class SplitInputFile {

	private ArrayList<String> m_allEvents = null;
	private int m_allSize = 0;
	private ArrayList<String> m_firstSet = null;
	private int m_firstSize = 0;
	private ArrayList<String> m_secondSet = null;
	private int m_secondSize = 0;
	private double percent;
	private Map<String,Integer> m_firstHashMap = null;
	private Map<String,Integer> m_secondHashMap = null;
	private Map<Tuple<String,String>,Integer> m_firstTupleHashMap = null;
	private Map<Tuple<String,String>,Integer> m_secondTupleHashMap = null;
	
	public SplitInputFile(ArrayList<String> events, int eventsSize, double percent) {
		this.m_allEvents = events;
		this.m_allSize = eventsSize;
		this.percent = percent;
		this.split();
	}
	
	/**
	 * Split the given set into two sets such that the first set contains |S| * percent and the second set contains all others.
	 * @param percent
	 */
	private void split() {
		this.m_firstSize = (int) Math.round(this.percent * this.m_allSize);
		
		// Create the first set
		this.m_firstSet = new ArrayList<String>();
		this.m_firstHashMap = new HashMap<String, Integer>();
		this.m_firstTupleHashMap = new HashMap<Tuple<String,String>, Integer>();
		int i = 0;
		
		while (i < this.m_firstSize - 1) {
			String word1 = this.m_allEvents.get(i);
			String word2 = this.m_allEvents.get(i + 1);
			
			//create the hash map data structure
			if (!this.m_firstHashMap.containsKey(word1)) {
				this.m_firstHashMap.put(word1, 1);
			} else {
				this.m_firstHashMap.put(word1, m_firstHashMap.get(word1) + 1);
			}
			
			//create the set data structure
			this.m_firstSet.add(word1);
			
			// Create the tuple data structure
			Tuple<String,String> tuple = new Tuple<String, String>(word1, word2);
			if (!this.m_firstTupleHashMap.containsKey(tuple)) {
				this.m_firstTupleHashMap.put(tuple, 1);
			} else {
				this.m_firstTupleHashMap.put(tuple, this.m_firstTupleHashMap.get(tuple) + 1);
			}
			
			i++;
		}
		
		// Handle the last word of the first set
		String word = this.m_allEvents.get(i);
		
		//create the hash map data structure
		if (!this.m_firstHashMap.containsKey(word)) {
			this.m_firstHashMap.put(word, 1);
		} else {
			this.m_firstHashMap.put(word, m_firstHashMap.get(word) + 1);
		}
		
		// Create the set data structure
		this.m_firstSet.add(word);
		
		// Create the second set and Hash map
		this.m_secondSet = new ArrayList<String>();
		this.m_secondHashMap = new HashMap<String, Integer>();
		this.m_secondTupleHashMap = new HashMap<Tuple<String,String>, Integer>();
		
		while (i < this.m_allSize - 1) {
			String word1 = this.m_allEvents.get(i);
			String word2 = this.m_allEvents.get(i + 1);
			
			//create the hash map data structure
			if (!this.m_secondHashMap.containsKey(word1)) {
				this.m_secondHashMap.put(word1, 1);
			} else {
				this.m_secondHashMap.put(word1, m_secondHashMap.get(word1) + 1);
			}
			
			//create the set data structure
			this.m_secondSet.add(word1);
			
			// Create the tuple data structure
			Tuple<String,String> tuple = new Tuple<String, String>(word1, word2);
			if (!this.m_secondTupleHashMap.containsKey(tuple)) {
				this.m_secondTupleHashMap.put(tuple, 1);
			} else {
				this.m_secondTupleHashMap.put(tuple, this.m_secondTupleHashMap.get(tuple) + 1);
			}
			
			i++;
		}
		this.m_secondSize = this.m_secondSet.size();
		
		// Handle the last word of the second set
		word = this.m_allEvents.get(i);
		
		//create the hash map data structure
		if (!this.m_secondHashMap.containsKey(word)) {
			this.m_secondHashMap.put(word, 1);
		} else {
			this.m_secondHashMap.put(word, m_secondHashMap.get(word) + 1);
		}
		
		//create the set data structure
		this.m_secondSet.add(word);
	}
	
	public ArrayList<String> getFirstSet() {
		return m_firstSet;
	}

	public int getFirstSize() {
		return m_firstSize;
	}

	public ArrayList<String> getSecondSet() {
		return m_secondSet;
	}


	public int getSecondSize() {
		return m_secondSize;
	}
	
	public Map<String, Integer> get_firstHashMap() {
		return m_firstHashMap;
	}

	public Map<String, Integer> get_secondHashMap() {
		return m_secondHashMap;
	}

	public Map<Tuple<String, String>, Integer> getFirstTupleHashMap() {
		return m_firstTupleHashMap;
	}

	public Map<Tuple<String, String>, Integer> getSecondTupleHashMap() {
		return m_secondTupleHashMap;
	}
}
