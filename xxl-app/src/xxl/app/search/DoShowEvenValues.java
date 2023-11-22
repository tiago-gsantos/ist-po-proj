package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;
// FIXME import classes

/**
 * Command for searching content values.
 */
class DoShowEvenValues extends Command<Spreadsheet> {

    DoShowEvenValues(Spreadsheet receiver) {
      super(Label.SEARCH_EVEN_VALUES, receiver);
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.searchEvenValue());
    }

}
