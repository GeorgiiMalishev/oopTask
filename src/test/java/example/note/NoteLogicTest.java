package example.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class NoteLogicTest {
    /**
     * Проверка команд /add и /notes
     */
    @Test
    void testAddNotesHandleMessage(){
        NoteLogic noteLogic = new NoteLogic();
        noteLogic.handleMessage("/add Note");
        Assertions.assertEquals("Your notes:\n1.Note", noteLogic.handleMessage("/notes"));
    }

    /**
     * Проверка команды /edit
     */
    @Test
    void testEditHandleMessage(){
        NoteLogic noteLogic = new NoteLogic();
        noteLogic.handleMessage("/add Note");
        noteLogic.handleMessage("/edit 1 EditedNote");
        Assertions.assertEquals("Your notes:\n1.EditedNote", noteLogic.handleMessage("/notes"));
    }

    /**
     * Проверка команды /del
     */
    @Test
    void testDelHandleMessage(){
        NoteLogic noteLogic = new NoteLogic();
        noteLogic.handleMessage("/add Note");
        noteLogic.handleMessage("/add Note2");
        noteLogic.handleMessage("/del 2");
        Assertions.assertEquals("Your notes:\n1.EditedNote", noteLogic.handleMessage("/notes"));
    }
}