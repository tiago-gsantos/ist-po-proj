package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.CellOutOfRangeException;

/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

    DoShow(Spreadsheet receiver) {
        super(Label.SHOW, receiver);
        addStringField("address", Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _display.popup(_receiver.showCells(stringField("address")));
        }
        catch(CellOutOfRangeException e){throw new InvalidCellRangeException(stringField("address"));}
    }
}
