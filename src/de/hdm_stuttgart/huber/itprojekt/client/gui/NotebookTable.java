package de.hdm_stuttgart.huber.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm_stuttgart.huber.itprojekt.client.ApplicationPanel;
import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.client.ShowNotebook;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

import java.util.Vector;

/**
 * Die Klasse NotebookTable wird alle Notizbücher in einer Tabelle Darstellen
 *
 * @author Erdmann, Nalivayko
 */

public class NotebookTable extends FlowPanel {

    EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
    DataGrid<Notebook> table = new DataGrid<>();
    private FlowPanel buttonPanel = new FlowPanel();
    private Vector<Notebook> noteB;

    public NotebookTable(Vector<Notebook> list) {
        this.noteB = list;
    }

    public Vector<Notebook> getNoteBooks() {
        return noteB;
    }

    public void setNoteBooks(Vector<Notebook> noteB) {
        this.noteB = noteB;
    }

    public DataGrid<Notebook> getTable() {
        return table;
    }

    public void setTable(DataGrid<Notebook> table) {
        this.table = table;
    }

    @Override
    public void onLoad() {
        TextColumn<Notebook> title = new TextColumn<Notebook>() {

            @Override
            public String getValue(Notebook noteB) {

                return noteB.getTitle();
            }
        };
        table.addColumn(title, "Title");

        TextColumn<Notebook> subtitle = new TextColumn<Notebook>() {

            @Override
            public String getValue(Notebook noteB) {
                // TODO Auto-generated method stub
                return noteB.getSubtitle();
            }
        };
        table.addColumn(subtitle, "Subtitle");

        TextColumn<Notebook> creationDate = new TextColumn<Notebook>() {

            @Override
            public String getValue(Notebook noteB) {
                // !!!! Könnte Fehler verursachen
                return noteB.getCreationDate().toString();
            }
        };
        table.addColumn(creationDate, "Creation Date");

        table.setRowCount(noteB.size(), false);
        table.setWidth("80%");
        table.setVisibleRange(0, noteB.size());
        table.setRowData(0, noteB);
        LayoutPanel panel = new LayoutPanel();
        panel.setSize("60em", "40em");
        panel.add(table);

        table.setStyleName("googleTable");

        this.add(buttonPanel);
        this.add(panel);

    }

    /**
     * Ein angeklicktes Notizbuch wird angezeigt
     */

    public void addClickNote() {
        final SingleSelectionModel<Notebook> selection = new SingleSelectionModel<>();
        table.setSelectionModel(selection);
        selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
    }

    @SuppressWarnings("unused")
    private class DeleteClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            if (Window.confirm("Wollen Sie das notizbuch löschen?")) {
                NotebookTable.this.add(new HTML("<p> Das Notizbuch wurde gelöscht</p>"));
            }

        }

    }

    private class SelectionChangeHandler implements Handler {
        private final SingleSelectionModel<Notebook> selection;

        private SelectionChangeHandler(SingleSelectionModel<Notebook> selection) {
            this.selection = selection;
        }

        @Override
        public void onSelectionChange(SelectionChangeEvent event) {

            /*
      Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
     */
            Notebook selected = selection.getSelectedObject();
            ShowNotebook sn = new ShowNotebook(selected);
            ApplicationPanel.getApplicationPanel().replaceContentWith(sn);

        }
    }

}
