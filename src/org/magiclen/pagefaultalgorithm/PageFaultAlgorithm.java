/*
 *
 * Copyright 2015 magiclen.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magiclen.pagefaultalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This is a program which can simulate memory page replacement.
 *
 * @author Magic Len
 */
public class PageFaultAlgorithm {

    /**
     * Enumerate all the algorithms.
     */
    public static enum Algorithm {

	FIFO, SECOND_CHANGE, LRU, OPTIMAL
    }

    /**
     * Program start here.
     *
     * @param args [algorithm number_of_frames references...]
     */
    public static void main(final String[] args) {
	if (args != null && args.length >= 3) {
	    String algorithmString = args[0];
	    final Algorithm algorithm;
	    if (algorithmString == null) {
		return;
	    }
	    algorithmString = algorithmString.trim().toLowerCase().replaceAll(" ", "-");
	    switch (algorithmString) {
		case "fifo":
		case "first-in-first-out":
		    algorithm = Algorithm.FIFO;
		    break;
		case "second-chance":
		case "clock-replacement":
		case "clock":
		case "cr":
		case "sc":
		    algorithm = Algorithm.SECOND_CHANGE;
		    break;
		case "lru":
		case "least-recently-used":
		    algorithm = Algorithm.LRU;
		    break;
		case "optimal":
		    algorithm = Algorithm.OPTIMAL;
		    break;
		default:
		    algorithm = null;
	    }
	    final int numberOfFrames = Integer.parseInt(args[1]);
	    final String[] references = Arrays.copyOfRange(args, 2, args.length);
	    readPage(algorithm, numberOfFrames, references);
	} else {
	    final Scanner sc = new Scanner(System.in);
	    System.out.printf("Choose a page fault algorithm:%n");
	    System.out.printf("1.FIFO%n2.Second Chance%n3.LRU%n4.Optimal%n");
	    Algorithm algorithm = null;
	    while (algorithm == null) {
		System.out.printf("Input[1-4]: ");
		final String line = sc.nextLine();
		try {
		    final int n = Integer.parseInt(line);
		    switch (n) {
			case 0:
			    algorithm = Algorithm.FIFO;
			    break;
			case 1:
			    algorithm = Algorithm.SECOND_CHANGE;
			    break;
			case 2:
			    algorithm = Algorithm.LRU;
			    break;
			case 3:
			    algorithm = Algorithm.OPTIMAL;
			    break;
		    }
		} catch (final Exception ex) {
		    // Nothing to do
		}
	    }
	    int numberOfFrames = 0;
	    while (numberOfFrames <= 0) {
		System.out.printf("Input the number of frames (>0): ");
		final String line = sc.nextLine();
		try {
		    numberOfFrames = Integer.parseInt(line);
		} catch (final Exception ex) {
		    // Nothing to do
		}
	    }
	    System.out.printf("Input the sequence of references (input # to end): ");
	    final ArrayList<String> al = new ArrayList<>();
	    while (true) {
		final String line = sc.next();
		if ("#".equals(line)) {
		    break;
		}
		al.add(line);
	    }
	    final String[] references = new String[al.size()];
	    al.toArray(references);

	    readPage(algorithm, numberOfFrames, references);
	}
    }

    /**
     * Start to read pages.
     *
     * @param pageFaultAlgorithm input a page fault algorithm
     * @param numberOfFrames input the number of frames
     * @param references input the sequence of references
     */
    public static void readPage(final Algorithm pageFaultAlgorithm, final int numberOfFrames, String... references) {
	if (pageFaultAlgorithm == null) {
	    // You need a replacement algorithm when page fault happened.
	    return;
	}
	if (numberOfFrames < 1) {
	    // You need at least 1 frame
	    return;
	}
	// Initialize frames
	final Frame frames[] = new Frame[numberOfFrames];
	for (int i = 0; i < numberOfFrames; ++i) {
	    frames[i] = new Frame();
	}

	// Make sure references is not null
	if (references == null) {
	    references = new String[0];
	}

	final int numberOfPageFault;
	switch (pageFaultAlgorithm) {
	    case FIFO:
		numberOfPageFault = FIFO(frames, numberOfFrames, references);
		break;
	    case SECOND_CHANGE:
		numberOfPageFault = secondChance(frames, numberOfFrames, references);
		break;
	    case LRU:
		numberOfPageFault = LRU(frames, numberOfFrames, references);
		break;
	    case OPTIMAL:
		numberOfPageFault = optimal(frames, numberOfFrames, references);
		break;
	    default:
		numberOfPageFault = 0;
	}
	System.out.printf("Number of Page Fault: %d%n", numberOfPageFault);
    }

    /**
     * Optimal Page Replacement.
     *
     * @param frames input the available frames
     * @param numberOfFrames input the number of frames
     * @param references input the sequence of references
     */
    private static int optimal(final Frame frames[], final int numberOfFrames, final String... references) {
	// Initialize victim
	int victim = 0;

	// Initialize the number of page fault
	int numberOfPageFault = 0;

	// Initialize the next reference sequence
	final int[] nextSequence = new int[numberOfFrames];
	for (int i = 0; i < numberOfFrames; ++i) {
	    nextSequence[i] = -1;
	}

	// Show initial frames information
	System.out.printf("Sequence Number: 0%n");
	for (int i = 0; i < numberOfFrames; ++i) {
	    final Frame frame = frames[i];
	    System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tNext Reference Sequence: %s%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference, (nextSequence[i] == -1) ? "Undefined" : (nextSequence[i] == 0) ? "No" : String.valueOf(nextSequence[i]));
	}

	// Load references in sequence
	final int numberOfReferences = references.length;
	for (int n = 0; n < numberOfReferences; ++n) {
	    System.out.printf("Sequence Number: %d%n", n + 1);
	    final String reference = references[n];
	    if (reference == null || reference.equalsIgnoreCase("null")) {
		System.out.printf("\tNothing found to load.%n");
		continue;
	    }
	    System.out.printf("\tReference: %s%n", reference);

	    // Find whether the reference exists in frames or not. If it is not found, the page fault happened.
	    boolean fault = true;
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		if (reference.equals(frame.reference)) {
		    // Break the search loop
		    fault = false;
		    break;
		}
	    }
	    System.out.printf("\tPage Fault: %b%n", fault);
	    if (fault) {
		// Page fault happened, then use the victim frame to replace its reference.
		frames[victim].reference = reference;
		// Choose next victim
		victim = (victim + 1) % numberOfFrames;

		// Increase the number of page fault
		++numberOfPageFault;
	    }

	    // Find the next reference sequence in the future, and optimize the victim
	    int tempVictim = victim, tempSequence = nextSequence[victim];
	    for (int i = 0; i < numberOfFrames; ++i) {
		final int index = (victim + i) % numberOfFrames;
		final String currentReference = frames[index].reference;
		if (currentReference == null) {
		    continue;
		}
		int next;
		for (next = n + 1; next < numberOfReferences; ++next) {
		    final String futureReference = references[next];
		    if (currentReference.equals(futureReference)) {
			break;
		    }
		}
		final int ns;
		if (next == numberOfReferences) {
		    ns = 0;
		} else {
		    ns = next + 1;
		}
		nextSequence[index] = ns;
		if (tempSequence > 0 && (ns == 0 || ns > tempSequence)) {
		    tempSequence = ns;
		    tempVictim = index;
		}
	    }
	    victim = tempVictim;

	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tNext Reference Sequence: %s%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference, (nextSequence[i] == -1) ? "Undefined" : (nextSequence[i] == 0) ? "No" : String.valueOf(nextSequence[i]));
	    }
	    System.out.printf("%n");
	}
	return numberOfPageFault;
    }

    /**
     * Least Recently Used Page Replacement.
     *
     * @param frames input the available frames
     * @param numberOfFrames input the number of frames
     * @param references input the sequence of references
     */
    private static int LRU(final Frame frames[], final int numberOfFrames, final String... references) {
	// Use linked list to store the index of frames, the order of the list is the order of victims. The larger index, the more recent frame.
	final LinkedList<Integer> list = new LinkedList<>();

	// Initialize list
	for (int i = 0; i < numberOfFrames; ++i) {
	    list.add(i);
	}

	// Initialize the number of page fault
	int numberOfPageFault = 0;

	// Show initial frames information
	System.out.printf("Sequence Number: 0%n");
	for (int i = 0; i < numberOfFrames; ++i) {
	    final Frame frame = frames[i];
	    final int recentOrder = list.indexOf(i);
	    System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tRecent Order: %d%n", i + 1, (recentOrder == 0) ? "(least recently used)" : "", frame.reference, recentOrder);
	}

	// Load references in sequence
	final int numberOfReferences = references.length;
	for (int n = 0; n < numberOfReferences; ++n) {
	    System.out.printf("Sequence Number: %d%n", n + 1);
	    final String reference = references[n];
	    if (reference == null || reference.equalsIgnoreCase("null")) {
		System.out.printf("\tNothing found to load.%n");
		continue;
	    }
	    System.out.printf("\tReference: %s%n", reference);

	    // Find whether the reference exists in frames or not. If it is not found, the page fault happened.
	    boolean fault = true;
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		if (reference.equals(frame.reference)) {
		    // Set the recent order, and break the search loop
		    final int indexInList = list.indexOf(i);
		    list.remove(indexInList);
		    list.add(i);
		    fault = false;
		    break;
		}
	    }
	    System.out.printf("\tPage Fault: %b%n", fault);
	    if (fault) {
		// Page fault happened, then use the least recently used frame to replace its reference.
		final int victim = list.get(0);
		frames[victim].reference = reference;
		// Set the recent order
		list.remove(0);
		list.add(victim);

		// Increase the number of page fault
		++numberOfPageFault;
	    }
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		final int recentOrder = list.indexOf(i);
		System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tRecent Order: %d%n", i + 1, (recentOrder == 0) ? "(least recently used)" : "", frame.reference, recentOrder);
	    }
	    System.out.printf("%n");
	}
	return numberOfPageFault;
    }

    /**
     * First in First out Page Replacement.
     *
     * @param frames input the available frames
     * @param numberOfFrames input the number of frames
     * @param references input the sequence of references
     */
    private static int FIFO(final Frame frames[], final int numberOfFrames, final String... references) {
	// Initialize victim
	int victim = 0;

	// Initialize the number of page fault
	int numberOfPageFault = 0;

	// Show initial frames information
	System.out.printf("Sequence Number: 0%n");
	for (int i = 0; i < numberOfFrames; ++i) {
	    final Frame frame = frames[i];
	    System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference);
	}

	// Load references in sequence
	final int numberOfReferences = references.length;
	for (int n = 0; n < numberOfReferences; ++n) {
	    System.out.printf("Sequence Number: %d%n", n + 1);
	    final String reference = references[n];
	    if (reference == null || reference.equalsIgnoreCase("null")) {
		System.out.printf("\tNothing found to load.%n");
		continue;
	    }
	    System.out.printf("\tReference: %s%n", reference);

	    // Find whether the reference exists in frames or not. If it is not found, the page fault happened.
	    boolean fault = true;
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		if (reference.equals(frame.reference)) {
		    // Break the search loop
		    fault = false;
		    break;
		}
	    }
	    System.out.printf("\tPage Fault: %b%n", fault);
	    if (fault) {
		// Page fault happened, then use the victim frame to replace its reference.
		frames[victim].reference = reference;
		// Choose next victim
		victim = (victim + 1) % numberOfFrames;

		// Increase the number of page fault
		++numberOfPageFault;
	    }
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference);
	    }
	    System.out.printf("%n");
	}
	return numberOfPageFault;
    }

    /**
     * The Clock Page Replacement.
     *
     * @param frames input the available frames
     * @param numberOfFrames input the number of frames
     * @param references input the sequence of references
     */
    private static int secondChance(final Frame frames[], final int numberOfFrames, final String... references) {
	// Initialize victim
	int victim = 0;

	// Initialize the number of page fault
	int numberOfPageFault = 0;

	// Show initial frames information
	System.out.printf("Sequence Number: 0%n");
	for (int i = 0; i < numberOfFrames; ++i) {
	    final Frame frame = frames[i];
	    System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tUse Bit: %d%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference, frame.usebit);
	}

	// Load references in sequence
	final int numberOfReferences = references.length;
	for (int n = 0; n < numberOfReferences; ++n) {
	    System.out.printf("Sequence Number: %d%n", n + 1);
	    final String reference = references[n];
	    if (reference == null || reference.equalsIgnoreCase("null")) {
		System.out.printf("\tNothing found to load.%n");
		continue;
	    }
	    System.out.printf("\tReference: %s%n", reference);

	    // Find whether the reference exists in frames or not. If it is not found, the page fault happened.
	    boolean fault = true;
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		if (reference.equals(frame.reference)) {
		    // Set use bit, and break the search loop
		    frame.usebit = 1;
		    fault = false;
		    break;
		}
	    }
	    System.out.printf("\tPage Fault: %b%n", fault);
	    if (fault) {
		// Page fault happened, then find a appropriate frame to replace.
		while (frames[victim].usebit == 1) {
		    frames[victim].usebit = 0;
		    victim = (victim + 1) % numberOfFrames;
		}
		// Found a victim frame, replace its reference and set its use bit
		frames[victim].reference = reference;
		frames[victim].usebit = 1;
		// Choose next victim
		victim = (victim + 1) % numberOfFrames;

		// Increase the number of page fault
		++numberOfPageFault;
	    }
	    for (int i = 0; i < numberOfFrames; ++i) {
		final Frame frame = frames[i];
		System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tUse Bit: %d%n", i + 1, (i == victim) ? "(victim)" : "", frame.reference, frame.usebit);
	    }
	    System.out.printf("%n");
	}
	return numberOfPageFault;
    }
}

/**
 * The structure of Frame.
 *
 * @author Magic Len
 */
class Frame {

    String reference; // Use string to represent a reference.
    byte usebit; // Reference bit. If the frame is referenced, set this use bit.

    Frame() {
	// Initialize
	reference = null;
	usebit = 0;
    }
}
