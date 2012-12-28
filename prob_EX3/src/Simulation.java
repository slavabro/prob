/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Simulation class of the exercise
 * @author Moshe Hazoom & Slava Bronfman
 *
 */
public class Simulation {
	
	private String m_strDevFileName;
	private String m_strTestFileName;
	private String m_strWord1;
	private String m_strWord2;
	private String m_strOutputFileName;
	private OutputFile m_outFile;
	private InputFile m_devFile;
	private InputFile m_testFile;
	private SplitInputFile m_devSplitBy09;
	private SplitInputFile m_devSplitBy1;
	private SplitInputFile m_testSplitBy1;
	private double m_bestLamdaValue;
	
	/**
	 * Constructor
	 * @param strDevFileName - Development file name
	 * @param strTestFileName - Test file name
	 * @param strWord - Given word
	 * @param strOutputFileName - Output file name
	 */
	public Simulation(String strDevFileName, String strTestFileName, String strWord1, String strWord2, String strOutputFileName) {
		this.m_strDevFileName = strDevFileName;
		this.m_strOutputFileName = strOutputFileName;
		this.m_strTestFileName = strTestFileName;
		this.m_strWord1 = strWord1;
		this.m_strWord2 = strWord2;
		this.m_outFile = new OutputFile(this.m_strOutputFileName);
	}
	
	/**
	 * Main function of the program.
	 */
	public void simulate() {
		this.createInputObjcets();
		this.init();
		this.modelTraining();
		this.evaluationOnTestSet();
		this.debug();
		
		this.m_outFile.close();
	}
	
	/**
	 * Create the input files objects - development, test and splitter.
	 */
	private void createInputObjcets() {
		// Initialize and parse the development file
		this.m_devFile = new InputFile(this.m_strDevFileName);
		
		// Initialize and parse the test file
		this.m_testFile = new InputFile(this.m_strTestFileName);
		
		// Split the development by 0.9
		this.m_devSplitBy09 = new SplitInputFile(this.m_devFile.getEvents(), this.m_devFile.getSize(), 0.9);
		
		// No split of the development set
		this.m_devSplitBy1 = new SplitInputFile(this.m_devFile.getEvents(), this.m_devFile.getSize(), 1.0);
		
		// No split of the test set
		this.m_testSplitBy1 = new SplitInputFile(this.m_testFile.getEvents(), this.m_testFile.getSize(), 1.0);
	}
	
	/**
	 * Create the init section of the exercise - lines 1 to 5
	 */
	private void init() {
		this.m_outFile.writeLine("#Students\t" + "Moshe Hazoom\t" + "Slava Bronfman\t" + "201337904\t" + "305917601");
		this.m_outFile.writeLine("#Output1\t" + this.m_strDevFileName);
		this.m_outFile.writeLine("#Output2\t" + this.m_strTestFileName);
		this.m_outFile.writeLine("#Output3\t" + this.m_strWord1 + " " + this.m_strWord2);
		this.m_outFile.writeLine("#Output4\t" + this.m_strOutputFileName);
		this.m_outFile.writeLine("#Output5\t" + ApplicationConstants.VOCABULARY_SIZE);
	}
	
	/**
	 * Model Training section - lines 6 to 16
	 */
	private void modelTraining() {
		this.m_outFile.writeLine("#Output6\t" + this.m_devFile.getSize());
		this.m_outFile.writeLine("#Output7\t" + this.m_devSplitBy09.getSecondSize());
		this.m_outFile.writeLine("#Output8\t" + this.m_devSplitBy09.getFirstSize());
		
		// Number of different events in the training set - Vt
		this.m_outFile.writeLine("#Output9\t" + this.m_devSplitBy09.get_firstHashMap().keySet().size());
		
		// The number of times the event INPUT WORD1 appears in the training set
		if (this.m_devSplitBy09.get_firstHashMap().containsKey(this.m_strWord1)) {
			this.m_outFile.writeLine("#Output10\t" + this.m_devSplitBy09.get_firstHashMap().get(this.m_strWord1));
		} else {
			this.m_outFile.writeLine("#Output10\t" + 0.0);
		}
		
		// The number of times the bigram (INPUT WORD1 INPUT WORD2) appears in the training set
		Tuple<String,String> tuple = new Tuple<String, String>(this.m_strWord1,this.m_strWord2);
		if (this.m_devSplitBy09.getFirstTupleHashMap().containsKey(tuple)) {
			this.m_outFile.writeLine("#Output11\t" + this.m_devSplitBy09.getFirstTupleHashMap().get(tuple));
		} else {
			this.m_outFile.writeLine("#Output11\t" + 0.0);
		}
		
		// The perplexity with Lamda=0.0001
		this.m_outFile.writeLine("#Output12\t" + Lidstone.calculatePerplexityBigramLidstone(this.m_devSplitBy09.getSecondTupleHashMap(),
				this.m_devSplitBy09.getFirstTupleHashMap(), this.m_devSplitBy09.get_firstHashMap(), 0.0001));
		
		// The perplexity with Lamda=0.001
		this.m_outFile.writeLine("#Output13\t" + Lidstone.calculatePerplexityBigramLidstone(this.m_devSplitBy09.getSecondTupleHashMap(),
				this.m_devSplitBy09.getFirstTupleHashMap(), this.m_devSplitBy09.get_firstHashMap(), 0.001));
		
		// The perplexity with Lamda=0.1
		this.m_outFile.writeLine("#Output14\t" + Lidstone.calculatePerplexityBigramLidstone(this.m_devSplitBy09.getSecondTupleHashMap(),
				this.m_devSplitBy09.getFirstTupleHashMap(), this.m_devSplitBy09.get_firstHashMap(), 0.1));
		
		// Calculate best lamda
		List<Double> bestLidstone = getBestPerplexity(this.m_devSplitBy09.getSecondTupleHashMap(),
				this.m_devSplitBy09.getFirstTupleHashMap(), this.m_devSplitBy09.get_firstHashMap());
		
		// Save the best lamda value for further use
		this.m_bestLamdaValue = bestLidstone.get(0);
		
		// The value of Lamda which minimize the perplexity
		this.m_outFile.writeLine("#Output15\t" + bestLidstone.get(0));
		
		// The minimized perplexity
		this.m_outFile.writeLine("#Output16\t" + bestLidstone.get(1));
	}
	
	/**
	 * Evaluation on test set section, line 17
	 */
	private void evaluationOnTestSet() {
		this.m_outFile.writeLine("#Output17\t" + Lidstone.calculatePerplexityBigramLidstone(this.m_testSplitBy1.getFirstTupleHashMap(),
				this.m_devSplitBy1.getFirstTupleHashMap(), this.m_devSplitBy1.get_firstHashMap(), this.m_bestLamdaValue));
	}	
	
	/**
	 * Calculates the best perplexity value, for Lamda in range of 0-2 in steps of 0.01 
	 * @param unseenSet
	 * @param seenMap
	 * @param seenSetSize
	 * @return
	 */
	private List<Double> getBestPerplexity(Map<Tuple<String,String>, Integer> tupleUnseenMap, Map<Tuple<String,String>, Integer> tupleSeenMap,
			Map<String,Integer> eventsMap){
		List<Double> resList = new ArrayList<Double>();
		resList.add(0, Double.MAX_VALUE);
		resList.add(1, Double.MAX_VALUE);
		
		double lamda = ApplicationConstants.MIN_LAMDA_VALUE;
		
		// go over all lamda values in range
		while (lamda <= ApplicationConstants.MAX_LAMDA_VALUE){
			double tmp = Lidstone.calculatePerplexityBigramLidstone(tupleUnseenMap, tupleSeenMap, eventsMap, lamda);
			
			// set the minimum value
			if(tmp < resList.get(1)){
				resList.set(0, lamda);
				resList.set(1, tmp);
			}
			lamda += ApplicationConstants.LAMDA_RANGE_STEP;
		}
		
	
		return resList;
	}
	
	/**
	 * Rounds the number
	 * @param num
	 * @param digits
	 * @return
	 */
	private double roundNdigits(double num, int digits) {
		int digNum = (int) Math.pow(10, digits);
		
		int tmp = (int) Math.round(num * digNum);
		return (double)tmp / (double)digNum;
	}
	
	/**
	 * Method sorts a given hash-map
	 * @param map
	 * @return
	 */
	private Map<String,Double> sortHashMap(Map<String,Double> map) {
		
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> m1, Map.Entry<String, Double> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<String, Double> result = new HashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        
        return (result);
	}
	
	/**
	 * Debug section - line 18
	 */
	private void debug() {
		// Number of different events in the training set - Vt
		int Vt = this.m_devSplitBy09.get_firstHashMap().keySet().size();
		
		// Create map of all the back-off probabilities
		Map<String, Double> backOffProbs = new HashMap<String,Double>();
		for (String event : this.m_devSplitBy09.get_firstHashMap().keySet()) {
			Tuple<String,String> tuple = new Tuple<String,String>(this.m_strWord1, event);
			backOffProbs.put(event, BackOff.calculateBackOff(tuple, this.m_devSplitBy09.getFirstTupleHashMap(),
					this.m_devSplitBy09.get_firstHashMap(), 0.001));
		}
		
		// Sort the map by back-off probability
		backOffProbs = this.sortHashMap(backOffProbs);
		
		// Iterate the different events in the training set
		int i = 1;
		for (String event : backOffProbs.keySet()) {
			
			this.m_outFile.write(i + "\t" + event + "\t");
			
			// count(INPUT_WORD1,x)
			Tuple<String,String> tuple = new Tuple<String,String>(this.m_strWord1, event);
			if (this.m_devSplitBy09.getFirstTupleHashMap().containsKey(tuple)) {
				this.m_outFile.write(this.m_devSplitBy09.getFirstTupleHashMap().get(tuple) + "\t");
			} else {
				this.m_outFile.write(0 + "\t");
			}
			
			// PB(x|INPUT_WORD1)
			this.m_outFile.writeLine(String.valueOf(backOffProbs.get(event)));
			i++;
		}
		
		// Write last line of unseen events
		this.m_outFile.writeLine((ApplicationConstants.VOCABULARY_SIZE - Vt) + "\t" + "UNSEEN_EVENTS" + "\t" + 0 +
				"\t" + (1.0 * Lidstone.computeUnigramLidstoneValueOfEvent(null, Vt, null, ApplicationConstants.LAMDA1, false)));
	}
}
