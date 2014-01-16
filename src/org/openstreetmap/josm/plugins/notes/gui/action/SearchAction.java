package org.openstreetmap.josm.plugins.notes.gui.action;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.widgets.HistoryChangedListener;
import org.openstreetmap.josm.plugins.notes.ConfigKeys;
import org.openstreetmap.josm.plugins.notes.Note;
import org.openstreetmap.josm.plugins.notes.NotesPlugin;
import org.openstreetmap.josm.plugins.notes.api.util.NotesApi;
import org.openstreetmap.josm.plugins.notes.gui.NotesDialog;
import org.openstreetmap.josm.plugins.notes.gui.dialogs.TextInputDialog;

public class SearchAction extends NotesAction {

    private static final long serialVersionUID = 1L;

    private NotesPlugin plugin;

    private String searchTerm;

    public SearchAction(NotesDialog dialog, NotesPlugin plugin) {
        super(tr("Reopen note"), dialog);
        this.plugin = plugin;
    }

    @Override
    protected void doActionPerformed(ActionEvent e) throws Exception {
        if(Main.pref.getBoolean(ConfigKeys.NOTES_API_OFFLINE)) {
            JOptionPane.showMessageDialog(Main.map, "You must be in online mode to use the search feature");
            canceled = true;
            return;
        }
        List<String> history = new LinkedList<String>(Main.pref.getCollection(ConfigKeys.NOTES_SEARCH_HISTORY, new LinkedList<String>()));
        HistoryChangedListener l = new HistoryChangedListener() {
            public void historyChanged(List<String> history) {
                Main.pref.putCollection(ConfigKeys.NOTES_SEARCH_HISTORY, history);
            }
        };
        searchTerm = TextInputDialog.showDialog(Main.map,
                tr("Search for notes?"),
                tr("<html>Search for notes (will go into offline mode)</html>"),
                NotesPlugin.loadIcon("find_notes.png"),
                history, l);

        if(searchTerm == null) {
            canceled = true;
            return;
        }
        dialog.refreshNoteStatus();
    }

    @Override
    public void execute() throws Exception {
        List<Note> searchResults = NotesApi.getNotesApi().searchNotes(searchTerm, 1000, 0);
        System.out.println("search results: " + searchResults.size());
        plugin.getDataSet().clear();
        plugin.getDataSet().addAll(searchResults);
        Main.map.mapView.repaint();
        dialog.refreshNoteStatus();
        dialog.setConnectionMode(true);
        dialog.showQueuePanel();
        Main.pref.put(ConfigKeys.NOTES_API_OFFLINE, true);
    }

    @Override
    public String toString() {
        return tr("Search term: " + searchTerm);
    }

    @Override
    public NotesAction clone() {
        SearchAction action = new SearchAction(dialog, plugin);
        action.canceled = canceled;
        action.searchTerm = searchTerm;
        return action;
    }
}
