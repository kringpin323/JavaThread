package javathreads.examples.ch08.example1;

public interface CharacterSource {
	// source �� listener ����ע��
    public void addCharacterListener(CharacterListener cl);
    public void removeCharacterListener(CharacterListener cl);
    public void nextCharacter();
}    
