package io.mewsub.testing.listeners;

import io.mewsub.testing.Testing;

import org.bukkit.entity.Player;
import org.bukkit.entity.Item;

import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {
    
    @EventHandler
    public void onPlayerDropItem( PlayerDropItemEvent evt ) {
        Player player = evt.getPlayer();
        Item item = evt.getItemDrop();
        item.setVelocity( player.getEyeLocation().getDirection().multiply( 1.75 ) );
        item.setPickupDelay( 5 );
        Testing.game.addThrowing( item );
    }

}
