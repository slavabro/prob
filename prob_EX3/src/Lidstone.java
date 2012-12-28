import java.util.Map;


public class Lidstone {
	
	/**
	 * private C'tor
	 * for creating a static class
	 */
	private Lidstone(){}
	
	/**
	 * Computes the lidstone value of any event with r appearances 
	 * @param map
	 * @param setSize
	 * @param lamda
	 * @param r
	 * @return
	 */
	public static double computeLidstoneValueOfFrequencyNum(int setSize, double lamda, int r, double vocabularSize) {
		
		return (((double)r + lamda)) / (double) ((double)setSize
				+ lamda * vocabularSize);
	}
	
	/**
	 * Computes the unigram lidstone value of the given event in a given set, with a give Lamda value
	 * @param map
	 * @param setSize
	 * @param event
	 * @param lamda
	 * @param isWordExists
	 * @return
	 */
	public static double computeUnigramLidstoneValueOfEvent(Map<String, Integer> map, int setSize,
			String event, double lamda, boolean isEventExists) {
		int result = 0;
		
		if (isEventExists) {
			if (map.containsKey(event)) {
				result = map.get(event);
			}
		}
		
		return Lidstone.computeLidstoneValueOfFrequencyNum(setSize, lamda, result, (double)ApplicationConstants.VOCABULARY_SIZE);
	}
	
	/**
	 * Computes the bigram lidstone value of the given tuple in a given set, with a give Lamda value
	 * @param tupleMap
	 * @param setSize
	 * @param tuple
	 * @param lamda
	 * @param isTupleExists
	 * @return
	 */
	public static double computeBigramLidstoneValueOfEvent(Map<String,Map<String,Integer>> tupleMap, int setSize,
			Tuple<String,String> tuple, double lamda) {
		int result = 0;
		
		if (tupleMap.containsKey(tuple)) {
			result = Util.getTupleCount(tupleMap, (tuple));
		}
		
		return Lidstone.computeLidstoneValueOfFrequencyNum(setSize, 
				lamda, result, Math.pow(ApplicationConstants.VOCABULARY_SIZE, 2));
	}
	
	/**
	 * Calculates the perplexity of a given set and lamda for bigram model.
	 * @param unseenSet
	 * @param seenSet
	 * @param lamda
	 * @return
	 */
	public static double calculatePerplexityBigramLidstone(Map<String,Map<String,Integer>> tupleUnseenMap, Map<String,Map<String,Integer>> tupleSeenMap,
			Map<String,Integer> eventsMap, double lamda) {
		double result = 0.0;
		
		Map<String, Double> alphaMapHelper = BackOff.calculateAlphaMap(tupleUnseenMap, tupleSeenMap, lamda, eventsMap, tupleUnseenMap.keySet().size());
		
		for (String word1 : tupleUnseenMap.keySet()) {
			for (String word2 : tupleUnseenMap.get(word1).keySet()) {
				// Calculate by log 2
				double p =  Math.log(BackOff.calculateBackOff(new Tuple<String, String>(word1, word2),
						tupleSeenMap, eventsMap, lamda, alphaMapHelper))
						/ Math.log(2);
				result += (p * Util.getTupleCount(tupleUnseenMap, new Tuple<String, String>(word1, word2)));
			}
		}
		
		return (Math.pow(2, (-1.0 / (double)tupleUnseenMap.keySet().size()) * result));
	}

}
