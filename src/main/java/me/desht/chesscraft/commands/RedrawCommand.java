package me.desht.chesscraft.commands;

import me.desht.chesscraft.Messages;
import me.desht.chesscraft.chess.BoardView;
import me.desht.chesscraft.exceptions.ChessException;
import me.desht.dhutils.MiscUtil;
import me.desht.dhutils.commands.AbstractCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class RedrawCommand extends AbstractCommand {

	public RedrawCommand() {
		super("chess red", 0, 1);
		setPermissionNode("chesscraft.commands.redraw");
		setUsage("/chess redraw [<board-name>]");
	}

	@Override
	public boolean execute(Plugin plugin, CommandSender player, String[] args) throws ChessException {
		if (args.length >= 1) {
			BoardView bv = BoardView.getBoardView(args[0]);
			bv.reloadStyle();
			bv.paintAll();
			MiscUtil.statusMessage(player, Messages.getString("ChessCommandExecutor.boardRedrawn", bv.getName())); //$NON-NLS-1$
		} else {
			for (BoardView bv : BoardView.listBoardViews()) {
				bv.reloadStyle();
				bv.paintAll();
			}
			MiscUtil.statusMessage(player, Messages.getString("ChessCommandExecutor.allBoardsRedrawn")); //$NON-NLS-1$
		}
		return true;
	}

}
