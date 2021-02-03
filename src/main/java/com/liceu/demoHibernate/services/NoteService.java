package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.repos.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoteService {

    @Autowired
    NoteRepo noteRepo;

    public Note findById(Long id){
        return noteRepo.findById(id).get();
    }

    public Note save(Note note){
        return noteRepo.save(note);
    }

    public void delete(Note note){
     noteRepo.delete(note);
    }

    public List<Note> findAllByUser(User u){
        return noteRepo.findAllByUser(u);
    }

    public Note findNoteByIdAndUser(Long note_id,User u){
        return noteRepo.findNoteByIdAndUser(note_id,u);
    }

    /**
     * This will filter the given notes by type matching the input.
     * @param notes All the notes of the user.
     * @param input search input (title, content...).
     * @param type the type of the search.
     * @return filtered list of notes.
     */
    public List<Note> filterNotes(List<Note> notes, String input, int type){
        List<Note> filteredNotes = new ArrayList<>();
        switch (type){
            case 1:
                // Filter by title.
                for (Note n : notes) if (n.getTitle().toLowerCase().contains(input.toLowerCase())) filteredNotes.add(n);
                break;
            case 2:
                // Filter by text.
                for (Note n : notes) if (n.getText().toLowerCase().contains(input.toLowerCase())) filteredNotes.add(n);
                break;
            case 3:
                // Filter by regex expression.
                Pattern pattern = Pattern.compile(input);
                for (Note n : notes) {
                    Matcher noteMatcher = pattern.matcher(n.getText());
                    if (noteMatcher.matches()){
                        filteredNotes.add(n);
                    }
                }
                break;
            case 4:
                // Filter by creation date.
                try {
                    for (Note n : notes) {
                        SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date1 = formatter5.parse(input);
                        //if (n.getDate().after(date1)) filteredNotes.add(n);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                // Filter by last modification date.
                try {
                    for (Note n : notes) {
                        SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date1 = formatter5.parse(input);
                        System.out.println("TODO 4 and 5");
                        //if (n.getLast_modification().isAfter(date1))filteredNotes.add(n);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

        }
        return filteredNotes;
    }
}
