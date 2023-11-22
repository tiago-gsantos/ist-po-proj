package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import xxl.Calculator;
import xxl.exceptions.MissingFileAssociationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSave extends Command<Calculator> {

    /** @param receiver */
    DoSave(Calculator receiver) {
        super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
    }

    /** @see pt.tecnico.uilib.menu.Command#execute() */
    @Override
    protected final void execute() throws CommandException{
        try{
            _receiver.save();
        } catch(MissingFileAssociationException e){
            try{
                _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
            } catch(MissingFileAssociationException | IOException e1){throw new FileOpenFailedException(e1);}
        } catch(IOException e){throw new FileOpenFailedException(e);}
    }
}
