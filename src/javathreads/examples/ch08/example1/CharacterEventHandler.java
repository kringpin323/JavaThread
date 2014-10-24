package javathreads.examples.ch08.example1;

import java.util.*;

public class CharacterEventHandler {
	// �����ᵽ��listeners Collection �� thread ��ȫ��
	// ����Ӱ�� vector�ڲ���״̬�����и����ʣ�Ϊʲôʹ�� toArray ������ race condition��
	// ����������������ȸ�����ʲô�� race condition
	// �Ÿ���Ҫ
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
            cl[i].newCharacter(ce);// Listener ��� Event ��һ�����飬û�з���ֵ���� Listener����� Event��
    }
}
