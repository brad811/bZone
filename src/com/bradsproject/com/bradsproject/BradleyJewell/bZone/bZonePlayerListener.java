package com.bradsproject.BradleyJewell.bZone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class bZonePlayerListener extends PlayerListener
{
	private final bZone plugin;
	
	public bZonePlayerListener(bZone instance)
	{
		plugin = instance;
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		Location location = player.getLocation();
		Zone zone = plugin.getZone(location);
		
		if(!plugin.players.containsKey(player.getName()))
		{
			bZonePlayer p = new bZonePlayer(player);
			plugin.players.put(player.getName(), p);
		}
		
		bZonePlayer bplayer = plugin.players.get(player.getName());
		try
		{
			if(!bplayer.zone.equals(zone.name))
			{
				if(!zone.name.equals("none"))
				{
					player.sendMessage("You are in the zone: " + zone.name);
					bplayer.zone = zone.name;
				} else
				{
					player.sendMessage("You are in the wilderness.");
					bplayer.zone = "none";
				}
			}
		} catch (NullPointerException e)
		{
			if(!bplayer.zone.equals("none"))
			{
				player.sendMessage("You are in the wilderness.");
				bplayer.zone = "none";
			}
		}
	}
	
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(
				event.getMaterial() == Material.CHEST
				|| event.getMaterial() == Material.FURNACE
				|| event.getMaterial() == Material.BURNING_FURNACE
				|| event.getMaterial() == Material.DISPENSER
			)
		{
			event.setCancelled(plugin.cancelAction(
					event.getClickedBlock().getLocation(),
					event.getPlayer(),
					"chest"
				)
			);
		}
	}
}
