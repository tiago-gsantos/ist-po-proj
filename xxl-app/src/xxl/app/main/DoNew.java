package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;
import xxl.Spreadsheet;

/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

    /** @param receiver */
    DoNew(Calculator receiver) {
        super(Label.NEW, receiver);
    }

    /** @see pt.tecnico.uilib.menu.Command#execute() */
    @Override
    protected final void execute() throws CommandException {

        // SaveBeforeExit 
        if(_receiver.hasSpreadsheet()){
            if(_receiver.spreadsheetChanged() && Form.confirm(Prompt.saveBeforeExit())){
                DoSave doSave = new DoSave(_receiver);
                doSave.execute();
            }
        }

        _receiver.initializeSpreadsheet(Form.requestInteger(Prompt.lines()), Form.requestInteger(Prompt.columns()));
    }

}
