package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.exceptions.UnavailableFileException;

import xxl.Calculator;


/**
 * Open existing file.
 */
class DoOpen extends Command<Calculator> {
    
    /** @param receiver */
    DoOpen(Calculator receiver) {
        super(Label.OPEN, receiver);
    }

    /** @see pt.tecnico.uilib.menu.Command#execute() */
    @Override
    protected final void execute() throws CommandException {
        try{
            // SaveBeforeExit
            if(_receiver.hasSpreadsheet()){
                if(_receiver.spreadsheetChanged() && Form.confirm(Prompt.saveBeforeExit())){
                    DoSave doSave = new DoSave(_receiver);
                    doSave.execute();
                }
            }
        
            _receiver.load(Form.requestString(Prompt.openFile()));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
