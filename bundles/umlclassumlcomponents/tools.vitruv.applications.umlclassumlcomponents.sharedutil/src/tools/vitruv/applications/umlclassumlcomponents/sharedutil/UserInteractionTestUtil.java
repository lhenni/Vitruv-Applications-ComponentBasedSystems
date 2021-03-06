package tools.vitruv.applications.umlclassumlcomponents.sharedutil;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import tools.vitruv.testutils.TestUserInteraction;

public class UserInteractionTestUtil {
	
	private static ConcurrentLinkedQueue<Integer> concurrentIntLinkedQueue = new ConcurrentLinkedQueue<Integer>();
	
	//Store given UserInteractions for later by appending them
	public static void queueUserInteractionSelections(final Integer... nextSelections) {
		concurrentIntLinkedQueue.addAll(Arrays.asList(nextSelections));
	}
	
	//Commit currently pending UserInteractions
	public static void sendCollectedUserInteractionSelections(TestUserInteraction userInteractor) {
		int[] selectionIndices = concurrentIntLinkedQueue.stream().mapToInt(i -> i).toArray();
		for (int selection : selectionIndices) {
			userInteractor.addNextSingleSelection(selection);
		}
        concurrentIntLinkedQueue.clear();
	}
}
