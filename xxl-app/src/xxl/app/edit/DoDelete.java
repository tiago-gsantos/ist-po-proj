package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.CellOutOfRangeException;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Delete command.
 */
class DoDelete extends Command<Spreadsheet> {

    DoDelete(Spreadsheet receiver) {
        super(Label.DELETE, receiver);
        addStringField("address", Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.deleteContents(stringField("address"));
        }
        catch(CellOutOfRangeException e){ throw new InvalidCellRangeException(stringField("address"));}
        catch(UnrecognizedEntryException e){} // Excecão nunca é lançada ??????
    }
}
