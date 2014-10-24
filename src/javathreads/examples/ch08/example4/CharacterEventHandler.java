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

    // ���Ƕ�listerners ѭ���Ե��õ�ÿһ���� ��������thread���ܻ�������ѭ����ʱ����� remove 
    // ���ܻ�� vector���Ѿ�ɾ���ĵ����ݽ��м��㣬�����race condition��
    // ��ʹʹ�� �� thread ��ȫ�Ե�collection �����ܱ�֤�������ȷ�ԣ�������Ҫ�ڷ���������ȷ��ͬ��
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
