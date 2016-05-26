package org.acouster.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashMapUtils
{
	/** taken from http://stackoverflow.com/questions/1335935/whats-the-quickest-way-to-remove-an-element-from-a-map-by-value-in-java */
	public static <TK, TV> void removeValue(Map<TK, TV> map, TV value)
	{
		for (
		    Iterator<Map.Entry<TK, TV>> iter = map.entrySet().iterator();
		    iter.hasNext();
		) {
		    Map.Entry<TK, TV> entry = iter.next();
		    if (value == entry.getValue()) {
		        iter.remove();
		        //break; // if only want to remove first match.
		    }
		}
	}
	/** taken from http://stackoverflow.com/questions/4700873/efficient-way-to-delete-values-from-hashmap-object */
	public static <TV> void removeKeyThatContains(Map<String, TV> map, String[] rem)
	{
		List<String> remList = Arrays.asList(rem);
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
		    String key = it.next();
		    String[] tokens = key.split("-");
		    for (int i = 0; i < tokens.length; i++) {
		        String token = tokens[i];
		        if (remList.contains(token)) {
		            it.remove();
		            break;
		        }
		     }
		}
	}
}
