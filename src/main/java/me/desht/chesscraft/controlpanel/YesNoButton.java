package me.desht.chesscraft.controlpanel;

import me.desht.chesscraft.ChessCraft;
import me.desht.chesscraft.Messages;
import me.desht.chesscraft.chess.ChessGame;
import me.desht.chesscraft.expector.ExpectDrawResponse;
import me.desht.chesscraft.expector.ExpectSwapResponse;
import me.desht.dhutils.LogUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class YesNoButton extends AbstractSignButton {

	private final int colour;
	private final boolean yesOrNo;
	
	public YesNoButton(ControlPanel panel, int x, int y, int colour, boolean yesOrNo) {
		super(panel, yesOrNo ? "yesBtn" : "noBtn", null, x, y);
		this.colour = colour;
		this.yesOrNo = yesOrNo;
	}

	@Override
	public void execute(PlayerInteractEvent event) {
		ChessCraft.handleYesNoResponse(event.getPlayer(), yesOrNo);
	}

	@Override
	public boolean isEnabled() {
		return !getOfferText().isEmpty();
	}
	
	@Override
	public String[] getCustomSignText() {
		String[] text = getSignText();
		
		text[0] = getOfferText();
		
		return text;
	}

	private String getOfferText() {
		ChessGame game = getGame();
		if (game == null) return "";
		
		String playerName = game.getPlayer(colour);
		Player p = Bukkit.getPlayer(playerName);
		
		if (p == null) {
			LogUtils.warning("unknown player:" + playerName + " (offline?) in game " + game.getName());
			return ""; //$NON-NLS-1$
		} else if (ChessCraft.getResponseHandler().isExpecting(p, ExpectDrawResponse.class)) {
			return Messages.getString("ControlPanel.acceptDrawBtn"); //$NON-NLS-1$
		} else if (ChessCraft.getResponseHandler().isExpecting(p, ExpectSwapResponse.class)) {
			return Messages.getString("ControlPanel.acceptSwapBtn"); //$NON-NLS-1$
		} else {
			return ""; //$NON-NLS-1$
		}
	}
}