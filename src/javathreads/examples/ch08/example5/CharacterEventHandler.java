package javathreads.examples.ch08.example5;

import java.util.*;

public class CharacterEventHandler {
	private Vector listeners = new Vector();

	public void addCharacterListener(CharacterListener cl) {
		listeners.add(cl);
	}

	public void removeCharacterListener(CharacterListener cl) {
		listeners.remove(cl);
	}

	// page 164
	public void fireNewCharacter(CharacterSource source, int c) {
		CharacterEvent ce = new CharacterEvent(source, c);
		Enumeration e;
		// 通过 Enumeration 来遍历集合
		synchronized(listeners) {
			e = listeners.elements();
			while (e.hasMoreElements()) {
				((CharacterListener) e.nextElement()).newCharacter(ce);
			}
		}
	}
}
