package org.openstreetmap.josm.plugins.notes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.User;

public class Note {

    public enum State { open, closed }

    private long id;
    private LatLon latLon;
    private Date createdAt;
    private State state;
    private String noteUrl;
    private List<Note.Comment> comments = new ArrayList<Note.Comment>();

    public class Comment {
        private String text;
        private User user;
        private Date createdAt;

        public Comment(Date createDate, User user, String comment) {
            this.text = comment;
            this.user = user;
            this.createdAt = createDate;
        }

        public String getText() {
            return text;
        }
        public User getUser() {
            return user;
        }
        public Date getCreatedAt() {
            return createdAt;
        }

    }

    public Note(LatLon latLon) {
        this.latLon = latLon;
    }

    public long getId() {
    	return id;
    }

    public LatLon getLatLon() {
        return latLon;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public State getState() {
        return state;
    }

    public List<Note.Comment> getComments() {
        return comments;
    }
    
    public String getNoteUrl() {
    	return noteUrl;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setNoteUrl(String url) {
    	this.noteUrl = url;
    }

    public void addComment(Note.Comment comment) {
        this.comments.add(comment);
    }

    public Note.Comment getFirstComment() {
        return this.comments.get(0);
    }

    public void updateWith(Note note) {
        this.comments = note.comments;
        this.createdAt = note.createdAt;
        this.id = note.id;
        this.state = note.state;
        this.latLon = note.latLon;
        this.noteUrl = note.noteUrl;
    }

}
