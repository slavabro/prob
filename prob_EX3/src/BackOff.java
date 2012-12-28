import java.util.HashMap;
import java.util.Map;


public class BackOff {
	
	/**
	 * Private C'tor for static class
	 */
	private BackOff() {}
	
	/**
	 * Method calculates backOff probability
	 * @param tuple
	 * @param tupleMap
	 * @param eventsMap
	 * @param lamda
	 * @return
	 */
	public static double calculateBackOff(Tuple<String,String> tuple, Map<String,Map<String,Integer>> tupleMap, 
			Map<String,Integer> eventsMap, double lamda, Map<String, Double> alphaMapHelper) {
		
		// Seen tuple
		if (tupleMap.containsKey(tuple)) {
			return (Lidstone.computeBigramLidstoneValueOfEvent(tupleMap, tupleMap.keySet().size(), tuple, lamda));
		// Unseen tuple
		} else {
			//return (computeAlpha(tuple.getFirst(), tupleMap, lamda, eventsMap, tupleMap.keySet().size()) *
			//		Lidstone.computeUnigramLidstoneValueOfEvent(eventsMap, eventsMap.keySet().size(), tuple.getSecond(), ApplicationConstants.LAMDA1, true));
			return (alphaMapHelper.get(tuple.getFirst())*
					Lidstone.computeUnigramLidstoneValueOfEvent(eventsMap, eventsMap.keySet().size(), tuple.getSecond(), ApplicationConstants.LAMDA1, true));
		}
	}
	
	/**
	 * Calculate the Alpha value for the whole validation set
	 * @param tupleMap
	 * @param lamda
	 * @param eventsMap
	 * @param eventsMapSize
	 * @return
	 */
	public static Map<String, Double> calculateAlphaMap(Map<String,Map<String,Integer>> tupleMap,  
			Map<String,Map<String,Integer>> tupleSeenMap, double lamda, Map<String,Integer> eventsMap,
			int eventsMapSize) {
		
		Map<String, Double> alphaHashMap = new HashMap<String, Double>();
		
		for (String word : tupleMap.keySet()) {
			alphaHashMap.put(word, computeAlpha(word, tupleSeenMap, lamda,
					eventsMap, eventsMapSize));
		}
		
		return alphaHashMap;
	}
	
	/**
	 * Method computes the coefficient alpha for the unseen tuples in the back-off calculation.
	 * @param word
	 * @param tupleMap
	 * @param lamda
	 * @param eventsMap
	 * @param eventsMapSize
	 * @return
	 */
	private static double computeAlpha(String word, Map<String,Map<String,Integer>> tupleMap, double lamda,
			Map<String,Integer> eventsMap, int eventsMapSize) {
		
		double nominator = 0.0;
		double denominator = 0.0;
		
		// case word1 is not part of the training set
		if(!tupleMap.containsKey(word))
			return 1.0;
		
		// Iterate all the tuples that appear at least one time with the given word appears first
		for (String word2 : tupleMap.get(word).keySet()) {	
			nominator += Lidstone.computeBigramLidstoneValueOfEvent(tupleMap, eventsMapSize, 
					new Tuple<String, String>(word, word2), lamda);
			denominator += Lidstone.computeUnigramLidstoneValueOfEvent(eventsMap, eventsMapSize, 
					word2, ApplicationConstants.LAMDA1, true);
			
		}
		
		return ((1.0 - nominator) / (1.0 - denominator));
	}

}
