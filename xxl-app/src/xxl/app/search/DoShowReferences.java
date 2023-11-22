package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;
// FIXME import classes

/**
 * Command for searching content values.
 */
class DoShowReferences extends Command<Spreadsheet> {

    DoShowReferences(Spreadsheet receiver) {
        super(Label.SEARCH_REFERENCES, receiver);
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.searchReference());
    }

}
