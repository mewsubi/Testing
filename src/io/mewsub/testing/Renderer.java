package io.mewsub.testing;

import java.io.File;
import java.awt.Image;

import org.bukkit.entity.Player;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Renderer extends MapRenderer {
	Image image;

	public Renderer( Image image ) {
		this.image = image;
	}

	@Override
	public void render( MapView map, MapCanvas canvas, Player player ) {
		canvas.drawImage( 0, 0, image );
		map.getRenderers().clear();
	}
}