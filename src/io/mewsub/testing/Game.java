package io.mewsub.testing;

import io.mewsub.testing.Testing;

import java.io.File;

import java.lang.Math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.util.EulerAngle;

import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;

import org.bukkit.map.MapView;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.MapMeta;

import org.bukkit.scheduler.BukkitRunnable;

public class Game extends BukkitRunnable {

	private Map<UUID, ArmorStand> stands;
	private Set<Item> throwing;

	public Game() {
		stands = new HashMap<UUID, ArmorStand>();
		this.throwing = new HashSet<Item>();
	}

	private EulerAngle rangle() {
		Random rng = new Random();
		int x = rng.nextInt( 360 );
		int y = rng.nextInt( 360 );
		int z = rng.nextInt( 360 );
		return new EulerAngle( x / 180.0 * Math.PI, y / 180.0 * Math.PI, z / 180.0 * Math.PI );
	}

	public void addThrowing( Item item ) {
		this.throwing.add( item );
	}

	public void run() {
		Iterator<Map.Entry<UUID, ArmorStand>> itr = this.stands.entrySet().iterator();
		Map.Entry<UUID, ArmorStand> entry;
		ArmorStand stand;
		Player player;
		
		int x, y, z;
		while( itr.hasNext() ) {
			entry = itr.next();
			player = Testing.server.getPlayer( entry.getKey() );
			stand = entry.getValue();
			Location location = player.getLocation().clone();
			location.setYaw( ( stand.getLocation().getYaw() + 75 ) % 360 );
			stand.teleport( location );
			double angle =  ( 9 / 180.0 * Math.PI );
			//stand.setRightArmPose( stand.getRightArmPose().add( angle, 0, 0 ) );
			/*stand.setLeftLegPose( stand.getLeftLegPose().add( angle, angle, angle ) );
			stand.setRightArmPose( stand.getRightArmPose().add( angle, angle, angle ) );
			stand.setRightLegPose( stand.getRightLegPose().add( angle, angle, angle ) );
			stand.setBodyPose( stand.getBodyPose().add( angle, angle, angle ) );
			stand.setHeadPose( stand.getHeadPose().add( angle, angle, angle ) );*/
		}
		Iterator<Item> itemItr = this.throwing.iterator();
		Item item;
		while( itemItr.hasNext() ) {
			item = itemItr.next();
			if( item.isOnGround() ) {
				itemItr.remove();
				item.remove();
			} else {
				for( Entity ent : item.getNearbyEntities( 1, 1, 1 ) ) {
					if( ent instanceof Damageable ) {
						if( item.getBoundingBox().overlaps( ent.getBoundingBox() ) ) {
							( ( Damageable ) ent ).damage( 1 );
						}
					}
				}				
			}
		}
	}

	public void stand( Player player ) {
		UUID uuid = player.getUniqueId();
		if( !this.stands.containsKey( uuid ) ) {
			ArmorStand stand = ( ArmorStand ) player.getWorld().spawnEntity( player.getLocation(), EntityType.ARMOR_STAND );
			stand.setArms( true );
			stand.setBasePlate( false );
			/*stand.setLeftArmPose( rangle() );
			stand.setLeftLegPose( rangle() );
			stand.setRightArmPose( rangle() );
			stand.setRightLegPose( rangle() );
			stand.setBodyPose( rangle() );
			stand.setHeadPose( rangle() );*/
			stand.setRightArmPose( new EulerAngle( 0, 0, 3.0 * Math.PI / 2.0 ) );
			stand.setVisible( false );
			//stand.setSmall( true );
			this.stands.put( uuid, stand );
		}
	}

	public void map( Player player ) {
		World world = player.getWorld();

		File[] subFiles = Testing.plugin.getDataFolder().listFiles();
		for( File file : subFiles ) {
			BufferedImage img = null;
			try {
			    img = ImageIO.read( file );
			} catch( Exception e ) {
				System.out.println( "bad" );
			}

			int rows, cols, diffY, diffX;
			if( img.getHeight() % 128 == 0 ) {
				rows = img.getHeight() / 128;
				diffY = 0;
			} else {
				rows = ( int ) Math.ceil( img.getHeight() / 128.0 );
				diffY = ( rows * 128 ) - img.getHeight();
			}
			if( img.getWidth() % 128 == 0 ) {
				cols = img.getWidth() / 128;
				diffX = 0;
			} else {
				cols = ( int ) Math.ceil( img.getWidth() / 128.0 );
				diffX = ( cols * 128 ) - img.getWidth();
			}

			BufferedImage imgs[] = new BufferedImage[ rows * cols ];
			System.out.println( rows );
			System.out.println( cols );
			int r, c;
			int x, y;
			for( r = 0; r < rows; ++r ) {
				for( c = 0; c < cols; ++c ) {
					x = y = 0;
					if( c == cols - 1 ) x = diffX;
					if( r == rows - 1 ) y = diffY;
					imgs[ r * cols + c ] = img.getSubimage( c * 128, r * 128, 128 - x, 128 - y );
				}
			}

			BufferedImage i;
			for( r = 0; r < rows; ++r ) {
				for( c = 0; c < cols; ++c ) {
					i = imgs[ r * cols + c ];

					ItemStack item = new ItemStack( Material.FILLED_MAP );
					MapMeta meta = ( MapMeta ) item.getItemMeta();
					meta.setDisplayName( "" + r + " " + c );
					MapView map = Testing.server.createMap( world );

					map.getRenderers().clear();
					map.setLocked( true );
					map.setCenterX( 0 );
					map.setCenterZ( 0 );
					map.setTrackingPosition( false );
					map.setUnlimitedTracking( false );
					map.setWorld( world );
					map.addRenderer( new Renderer( i ) );

					meta.setMapView( map );
					item.setItemMeta( meta );

					world.dropItemNaturally( player.getLocation(), item );
				}
			}
		}
	}
}
