package pl.mate00.sleeperfriendlyapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import pl.mate00.sleeperfriendlyapp.db.IPlaylistSelector;
import pl.mate00.sleeperfriendlyapp.db.PathEntity;

public class Mp3ShufflerTest {

    private Mp3Shuffler subject = new Mp3Shuffler();

    @Test
    public void shouldNotReturnEmptyWhenAllUnplayed() {
    	IPlaylistSelector selector = new AllUnplayedSelector();
    	subject.setSelector(selector);
    	
    	assertFalse(subject.getNext().equals(Mp3Shuffler.EMPTY));
    }
    
    @Test
    public void shouldNotReturnEmptyWhenThereAreSomeUnplayed() {
    	IPlaylistSelector selector = new SomeUnplayedSelector();
    	subject.setSelector(selector);
    	
    	assertFalse(subject.getNext().equals(Mp3Shuffler.EMPTY));
    }
    
    @Test
    public void shouldNotReturnAlreadyPlayed() {
    	subject.setSelector(new SomeUnplayedSelector());

        String result = subject.getNext();
        assertFalse(result.equals("path2"));
    }
    
    @Test
    public void shouldReturnUnplayed() {
    	subject.setSelector(new SomeUnplayedSelector());

    	String result = subject.getNext();
        assertTrue(result.equals("path1") || result.equals("path3") || result.equals("path4"));
    	assertFalse(result.equals("path2"));
    }

    @Test
    public void shouldResetWhenNoneUnplayed() {
        subject.setSelector(new NoneUnplayedSelector());

        String result = subject.getNext();
        assertFalse(result.equals(Mp3Shuffler.EMPTY));
    }
}
