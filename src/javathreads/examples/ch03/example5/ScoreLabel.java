
package javathreads.examples.ch03.example5;

import javax.swing.*;
import java.awt.event.*;
import javathreads.examples.ch03.*;

public class ScoreLabel extends JLabel implements CharacterListener {
    
    private volatile int score = 0;
    private int char2type = -1;
    private CharacterSource generator = null, typist = null;

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

    public synchronized void resetGenerator(CharacterSource newGenerator) {
        if (generator != null)
            generator.removeCharacterListener(this);
        generator = newGenerator;
        if (generator != null)
            generator.addCharacterListener(this);        
    }

    public synchronized void resetTypist(CharacterSource newTypist) {
        if (typist != null)
            typist.removeCharacterListener(this);
        typist = newTypist;
        if (typist != null)
            typist.addCharacterListener(this);
    }

    public synchronized void resetScore() {
        score = 0;
        char2type = -1;
        setScore();
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(score));
            }
        });
    }

    // 由于所有的 method 都是 private 因此 全部加锁 本来 不需要
    // 但是 Java 很聪明
    // 如果当前的 thread 已经占有lock，会直接让 synchronized 程序段运行
    // 如果 在上述情况下 进入 synchronized 不需要获得，锁，那么 离开时 也不会释放掉，
    // 最后在 第一个 取得 lock 的 method 退出时 将 lock 释放掉，
    // 这功能 称为： nested locking ， 内联锁
    
    // 此外 可重入锁 ReentrantLock 与 synchronized 保持一致
    // 如果 lock 的请求是 由 当前占有 lock 的 thread 发出， 那么
    // ReentrantLock 对象只会对内部的 nested lock 要求计数递增
    // unlock() 会 减 计数， 在 计数为 0 前不会释放掉
    // 所以 才叫 可重入锁， 同时也是 可重入锁的 独有特征，不是所有 继承 lock的类的特性
    
    // nested lock 出现意义： 避免死锁
    // P65 Java线程
    
    // 
    private synchronized void newGeneratorCharacter(int c) {
        if (char2type != -1) {
            score--;
            setScore();
        }
        char2type = c;
    }

    private synchronized void newTypistCharacter(int c) {
        if (char2type != c) {
            score--;
        } else {
            score++;
            char2type = -1;
        }
        setScore();
    }

    public synchronized void newCharacter(CharacterEvent ce) {
        // Previous character not typed correctly - 1 point penalty
        if (ce.source == generator) {
	    newGeneratorCharacter(ce.character);
        }

        // If character is extraneous - 1 point penalty
        // If character does not match - 1 point penalty
        else {
	    newTypistCharacter(ce.character);
        }
    } 
}
