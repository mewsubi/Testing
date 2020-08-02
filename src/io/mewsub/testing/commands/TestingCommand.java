package io.mewsub.testing.commands;

import io.mewsub.testing.Testing;

import org.bukkit.entity.Player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestingCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if( sender instanceof Player ) {
			Player player = ( Player ) sender;
			if( args.length > 0 ) {
				String cmd = args[ 0 ];
				if( cmd.equals( "stand" ) ) {
					Testing.game.stand( player );
				} else if( cmd.equals( "map" ) ) {
					Testing.game.map( player );
				}
			}
		}
		return true;
	}
	
}