package splitwise;

import java.util.HashMap;

public interface Split {
	
	

	HashMap<Integer, HashMap<Integer, Double>> splitByPercentage(Integer[] p, Double[] arr, Double total, Double[] share);

	

	HashMap<Integer, HashMap<Integer, Double>> splitEqual(Integer[] p, Double[] arr, Double total);
}
