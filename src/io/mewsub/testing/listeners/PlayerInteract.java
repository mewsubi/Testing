package io.mewsub.testing.listeners;

import io.mewsub.testing.Testing;

import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.CrossbowMeta;

import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.util.Vector;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerInteractEvent;

import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

public class PlayerInteract implements Listener {
    
    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent evt ) {
    	Player player = evt.getPlayer();
    	if( evt.hasItem() ) {
    		ItemStack item = evt.getItem();
    		if( item.getType() == Material.CROSSBOW ) {
    			CrossbowMeta meta = ( CrossbowMeta ) item.getItemMeta();
    			if( meta.hasChargedProjectiles() ) {
    				World world = player.getWorld();
	    			Location loc = player.getEyeLocation().clone().add( 0, -0.2, 0 );
	    			double x = loc.getX();
	    			double y = loc.getY() - 0.2;
	    			double z = loc.getZ();
	    			Vector vec = loc.getDirection().multiply( 0.5 );
	    			double dx = vec.getX();
	    			double dy = vec.getY();
	    			double dz = vec.getZ();
	    			Particle.DustOptions opt = new Particle.DustOptions( Color.AQUA, 1 );
	    			int PARTICLES = 5000;
	    			for( int i = 0; i < PARTICLES; ++i ) {
	    				world.spawnParticle( Particle.REDSTONE, x + i * dx, y + i * dy, z + i * dz, 8, 0.01, 0.01, 0.01, 0.01, opt, false );
	    			}
	    			//List<Damageable> targets = new ArrayList<Damageable>();
	    			for( Entity entity : player.getNearbyEntities( dx * PARTICLES, dy * PARTICLES, dz * PARTICLES ) ) {
	    				if( ( entity instanceof Damageable ) && player.hasLineOfSight( entity ) ) {
	    					if( entity instanceof ComplexLivingEntity ) {
	    						for( ComplexEntityPart ent : ( ( ComplexLivingEntity ) entity ).getParts() ) {
	    							if( ent.getBoundingBox().rayTrace( loc.toVector(), loc.getDirection(), Math.sqrt( ( dx * PARTICLES * dx * PARTICLES ) + ( dy * PARTICLES * dy * PARTICLES ) + ( dz * PARTICLES *  dz * PARTICLES ) ) ) != null ) {
	    								entity.setFireTicks( 10 );
			    						( ( Damageable ) entity ).damage( 100, player );
			    					}
	    						}
	    					} else {
	    						if( entity.getBoundingBox().rayTrace( loc.toVector(), loc.getDirection(), Math.sqrt( ( dx * PARTICLES * dx * PARTICLES ) + ( dy * PARTICLES * dy * PARTICLES ) + ( dz * PARTICLES *  dz * PARTICLES ) ) ) != null ) {
	    							entity.setFireTicks( 10 );
		    						( ( Damageable ) entity ).damage( 100, player );
		    					}
	    					}
	    				}
	    			}
	    			evt.setCancelled( true );
    			}
    		}
    	}
    	/*Player player = evt.getPlayer();
    	PlayerInventory inv = player.getInventory();

    	ItemStack left = inv.getItemInOffHand();
    	ItemStack right = inv.getItemInMainHand();
        if( left.getType() == Material.GOLDEN_SWORD && right.getType() == Material.DIAMOND_SWORD ) {
        	PacketPlayOutAnimation anim;
        	evt.setCancelled( true );
        	switch( evt.getAction() ) {
        		case LEFT_CLICK_AIR:
        		case LEFT_CLICK_BLOCK:
        			anim = new PacketPlayOutAnimation( ( ( CraftPlayer ) player ).getHandle(), 0 );
        			for( Player p : Testing.server.getOnlinePlayers() ) {
		            	( ( CraftPlayer ) p ).getHandle().playerConnection.sendPacket( anim );
		            }
		            player.sendMessage( "left" );
		            //left.getItemMeta().setDisplayName( "swung" );
        			break;
        		case RIGHT_CLICK_AIR:
        		case RIGHT_CLICK_BLOCK:
        			anim = new PacketPlayOutAnimation( ( ( CraftPlayer ) player ).getHandle(), 3 );
        			for( Player p : Testing.server.getOnlinePlayers() ) {
		            	( ( CraftPlayer ) p ).getHandle().playerConnection.sendPacket( anim );
		            }
		            player.sendMessage( "right" );
		            //right.getItemMeta().setDisplayName( "swung" );
        			break;
        	}
        }*/
    }

}
