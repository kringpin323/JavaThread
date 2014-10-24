package javathreads.examples.ch08.example4;

import java.util.*;

public class CharacterEventHandler {
    private ArrayList listeners = new ArrayList();

    public void addCharacterListener(CharacterListener cl) {
        synchronized(listeners) {
            listeners.add(cl);
        }
    }

    public void removeCharacterListener(CharacterListener cl) {
        synchronized(listeners) {
            listeners.remove(cl);
        }
    }

    // 我们对listerners 循环以调用到每一个， 但是其他thread可能会在我们循环的时候调用 remove 
    // 可能会对 vector中已经删除的的数据进行计算，这就是race condition？
    // 即使使用 有 thread 安全性的collection 并不能保证程序的正确性，还是需要在范例中有明确的同步
    public void fireNewCharacter(CharacterSource source, int c) {
        CharacterEvent ce = new CharacterEvent(source, c);
        CharacterListener[] cl;
        synchronized(listeners) {
            cl = (CharacterListener[] )
                                 listeners.toArray(new CharacterListener[0]);
        }
        for (int i = 0; i < cl.length; i++)
            cl[i].newCharacter(ce);
    }
}
