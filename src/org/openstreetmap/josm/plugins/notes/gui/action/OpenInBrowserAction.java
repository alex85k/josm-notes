/* Copyright (c), Henrik Niehaus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.openstreetmap.josm.plugins.notes.gui.action;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.AbstractInfoAction;
import org.openstreetmap.josm.plugins.notes.Note;
import org.openstreetmap.josm.plugins.notes.gui.NotesDialog;
import org.openstreetmap.josm.tools.OpenBrowser;

public class OpenInBrowserAction extends NotesAction {

    private static final long serialVersionUID = 1L;

    private Note note;

    public OpenInBrowserAction(NotesDialog dialog) {
        super(tr("Open in browser"), dialog);
    }

    @Override
    protected void doActionPerformed(ActionEvent e) throws Exception {
        note = dialog.getSelectedNote();
        if(note != null) {
            String uri = AbstractInfoAction.getBaseBrowseUrl() + "/note/" + note.getId();
            System.out.println("Opening URI " + uri);
            OpenBrowser.displayUrl(uri);
        } else {
            JOptionPane.showMessageDialog(Main.parent, tr("No item selected"), tr("Error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void execute() throws IOException {
    }

    @Override
    public String toString() {
        return tr("Open in browser: Note " + note.getId());
    }

    @Override
    public OpenInBrowserAction clone() {
        OpenInBrowserAction action = new OpenInBrowserAction(dialog);
        action.note = note;
        return action;
    }
}
