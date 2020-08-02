package io.mewsub.testing;

import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Server;

import io.mewsub.testing.commands.TestingCommand;

import io.mewsub.testing.listeners.EntityToggleSwim;
import io.mewsub.testing.listeners.HangingBreak;
import io.mewsub.testing.listeners.PlayerDropItem;
import io.mewsub.testing.listeners.PlayerInteract;

import org.bukkit.scheduler.BukkitRunnable;

public class Testing extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;

    public static Game game;

    @Override
    public void onEnable() {
        Testing.plugin = ( Plugin ) this;
        Testing.server = this.getServer();
        Testing.server.getPluginManager().registerEvents( new EntityToggleSwim(), this );
        Testing.server.getPluginManager().registerEvents( new HangingBreak(), this );
        Testing.server.getPluginManager().registerEvents( new PlayerDropItem(), this );
        Testing.server.getPluginManager().registerEvents( new PlayerInteract(), this );

        Testing.game = new Game();
        Testing.game.runTaskTimer( Testing.plugin, 0L, 1L );

        this.getCommand( "testing" ).setExecutor( new TestingCommand() );

        getDataFolder().mkdir();
    }

    @Override
    public void onDisable() {
        Testing.plugin = null;
        Testing.server = null;
        Testing.game.cancel();
        Testing.game = null;
    }

}
