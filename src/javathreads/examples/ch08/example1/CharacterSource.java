package javathreads.examples.ch08.example1;

public interface CharacterSource {
	// source ÔÚ listener ÀïÃæ×¢²á
    public void addCharacterListener(CharacterListener cl);
    public void removeCharacterListener(CharacterListener cl);
    public void nextCharacter();
}    
