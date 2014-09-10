
package javathreads.examples.ch03.example8;

import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import javathreads.examples.ch03.*;

public class ScoreLabel extends JLabel implements CharacterListener {
    
    private volatile int score = 0;
    private int char2type = -1;
    private CharacterSource generator = null, typist = null;
    private Lock adminLock = new ReentrantLock();
    private Lock charLock = new ReentrantLock();
    private Lock scoreLock = new ReentrantLock();


    public ScoreLabel (CharacterSource generator, CharacterSource typist) {
        this.generator = generator;
        this.typist = typist;

        if (generator != null)
            generator.addCharacterListener(this);
        if (typist != null)
             typist.addCharacterListener(this);       
    }

    public ScoreLabel () {
        this(null, null);
    }

    public void resetGenerator(CharacterSource newGenerator) {
        try {
            adminLock.lock();
            if (generator != null)
                generator.removeCharacterListener(this);

            generator = newGenerator;
            if (generator != null)
                generator.addCharacterListener(this);
        } finally {
            adminLock.unlock();
        }
    }

    public void resetTypist(CharacterSource newTypist) {
        try {
            adminLock.lock();
            if (typist != null)
                typist.removeCharacterListener(this);

            typist = newTypist;
            if (typist != null)
                typist.addCharacterListener(this);
        } finally {
            adminLock.unlock();
        }
    }

    // 据说这里有一个难以检测的死锁
    // 解决方法是 让两个  lock 的调用顺序相同
    public void resetScore() {
        try {
            charLock.lock();
            scoreLock.lock();
            score = 0;
            char2type = -1;
            setScore();
        } finally {
            charLock.unlock();
            scoreLock.unlock();
        }
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(score));
            }
        });
    }

    // 死锁 矛盾发生于 newCharacter 和 resetScore 之间
    public void newCharacter(CharacterEvent ce) {
        try {
            scoreLock.lock();
            charLock.lock();
            // Previous character not typed correctly - 1 point penalty
            if (ce.source == generator) {
                if (char2type != -1) {
                    score--;
                    setScore();
                }
                char2type = ce.character;
            }

            // If character is extraneous - 1 point penalty
            // If character does not match - 1 point penalty
            else {
                if (char2type != ce.character) {
                    score--;
                } else {
                    score++;
                    char2type = -1;
                }
                setScore();
            }
        } finally {
            scoreLock.unlock();
            charLock.unlock();
        }
    } 
}
