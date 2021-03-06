package me.desht.chesscraft.commands;

import me.desht.chesscraft.ChessCraft;
import me.desht.chesscraft.Messages;
import me.desht.chesscraft.chess.BoardView;
import me.desht.chesscraft.chess.ChessGame;
import me.desht.chesscraft.exceptions.ChessException;
import me.desht.dhutils.PermissionUtils;
import me.desht.dhutils.commands.AbstractCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

public class TeleportCommand extends AbstractCommand {

	public TeleportCommand() {
		super("chess tp", 0, 2);
		setPermissionNode("chesscraft.commands.teleport");
		setUsage(new String[] {
				"/chess tp [<game-name>]",
				"/chess tp <board-name> -b"
		});
	}

	@Override
	public boolean execute(Plugin plugin, CommandSender sender, String[] args) throws ChessException {
		notFromConsole(sender);
		
		if (!ChessCraft.getInstance().getConfig().getBoolean("teleporting")) {
			throw new ChessException(Messages.getString("ChessCommandExecutor.noTeleporting"));
		}
		
		Player player = (Player)sender;
		
		switch (args.length) {
		case 0:
			BoardView.teleportOut(player);
			break;
		case 1:
			ChessGame.getGame(args[0]).summonPlayer(player);
			break;
		case 2:
			if (args[1].startsWith("-b")) {
				PermissionUtils.requirePerms(sender, "chesscraft.commands.teleport.board");
				BoardView.getBoardView(args[0]).summonPlayer(player);
			}
		}
		return true;
	}

}
