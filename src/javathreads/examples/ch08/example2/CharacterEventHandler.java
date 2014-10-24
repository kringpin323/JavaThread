package javathreads.examples.ch08.example2;

import java.util.*;

public class CharacterEventHandler {
	// ���̰߳�ȫ�� �� collection
    private ArrayList listeners = new ArrayList();
    
    // �� vector ��ȣ���ȷ����ͬ��
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
