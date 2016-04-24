package selenium_utils;

import java.util.ArrayList;
import java.util.List;

public final class Functional {
	public static List<Element> all(List<Element> elements, ElementCondition condition) {
		ArrayList<Element> result = new ArrayList<>();
		
		for (int index = 0; index < elements.size(); index ++) {
			Element element = elements.get(index);
			
			if (condition.apply(element).booleanValue()) {
				result.add(element);
			}
		}
		return result;
	}
	
	public static Element first(List<Element> elements, ElementCondition condition) {
		return all(elements, condition).get(0);
	}
	
	public static Element last(List<Element> elements, ElementCondition condition) {
		List<Element> result = all(elements, condition);
		return result.get(result.size() - 1);
	}
}
