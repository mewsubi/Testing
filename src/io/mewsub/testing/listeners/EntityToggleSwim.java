package io.mewsub.testing.listeners;

import io.mewsub.testing.Testing;

import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Entity;;
import org.bukkit.entity.Player;

import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.entity.EntityToggleSwimEvent;

import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

public class EntityToggleSwim implements Listener {
    
    @EventHandler
    public void onEntityToggleSwim( EntityToggleSwimEvent evt ) {
    	
    }

}
