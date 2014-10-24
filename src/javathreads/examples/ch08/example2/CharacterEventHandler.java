package javathreads.examples.ch08.example2;

import java.util.*;

public class CharacterEventHandler {
	// 无线程安全性 的 collection
    private ArrayList listeners = new ArrayList();
    
    // 与 vector 相比，明确管理同步
    public synchronized void addCharacterListener(CharacterListener cl) {
        listeners.add(cl);
    }

    public synchronized void removeCharacterListener(CharacterListener cl) {
        listeners.remove(cl);
    }

    public synchronized void fireNewCharacter(CharacterSource source, int c) {
        CharacterEvent ce = new CharacterEvent(source, c);
        CharacterListener[] cl = (CharacterListener[] )
                                 listeners.toArray(new CharacterListener[0]);
        for (int i = 0; i < cl.length; i++)
            cl[i].newCharacter(ce);
    }
}
