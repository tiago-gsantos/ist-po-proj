package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;
// FIXME import classes

/**
 * Command for searching function names.
 */
class DoShowFunctions extends Command<Spreadsheet> {

    DoShowFunctions(Spreadsheet receiver) {
        super(Label.SEARCH_FUNCTIONS, receiver);
        addStringField("function", Prompt.searchFunction());
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.searchFunction(stringField("function")));
    }

}
