package javathreads.examples.ch08.example1;

import java.util.*;

public class CharacterEventHandler {
	// 这里提到：listeners Collection 有 thread 安全性
	// 不会影响 vector内部的状态，我有个疑问，为什么使用 toArray 会引起 race condition，
	// 不过比起这个，首先搞明白什么是 race condition
	// 才更重要
    private Vector listeners = new Vector();

    public void addCharacterListener(CharacterListener cl) {
        listeners.add(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        listeners.remove(cl);
    }

    public void fireNewCharacter(CharacterSource source, int c) {
        CharacterEvent ce = new CharacterEvent(source, c);
        CharacterListener[] cl = (CharacterListener[] )
                                 listeners.toArray(new CharacterListener[0]);
        for (int i = 0; i < cl.length; i++)
            cl[i].newCharacter(ce);// Listener 会对 Event 做一点事情，没有返回值，在 Listener添加了 Event后
    }
}
