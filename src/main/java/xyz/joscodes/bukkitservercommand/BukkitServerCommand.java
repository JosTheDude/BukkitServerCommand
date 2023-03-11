package xyz.joscodes.bukkitservercommand;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// lol i was sent this and asked to post it since the person didnt have a github account and was lazy

public final class BukkitServerCommand extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /c-server <player> <server>");
			return true;
		}

		String playerName = args[0];
		String serverName = args[1];

		Player player = Bukkit.getPlayer(playerName);
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "Player not found.");
			return true;
		}

		// send player to server using Velocity Plugin Messaging
		sendPlayerToServer(player, serverName);
		String message = String.format("%sSent %s to the %s server!", ChatColor.GREEN, playerName, ChatColor.YELLOW + serverName + ChatColor.GREEN);
		sender.sendMessage(message);
		return true;
	}

	private void sendPlayerToServer(Player player, String serverName) {
		// get Velocity plugin messaging channel
// 		PluginMessageChannel channel = VelocityPluginMessaging.getChannel(player.getServer());

		// create packet to send player to server
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(serverName);
		player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}


}
