package com.bradsproject.BradleyJewell.bZone;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class bZoneEntityListener extends EntityListener
{
	private final bZone plugin;
	
	public bZoneEntityListener(bZone instance)
	{
		plugin = instance;
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		Location location = event.getEntity().getLocation();
		Entity entity = event.getEntity();
		
		event.setCancelled(removeCreature(location, entity));
	}
	
	@Override
	public void onEntityTarget(EntityTargetEvent event)
	{
		Location location = event.getEntity().getLocation();
		Entity entity = event.getEntity();
		
		removeCreature(location, entity);
	}
	
	public boolean removeCreature(Location location, Entity entity)
	{
		Zone zone = plugin.getZone(location);
		String type = entity.toString().replace("Craft", "").toLowerCase();
		
		try {
			if(!zone.creatures.contains(type))
			{
				entity.remove();
				return true;
			}
		} catch(NullPointerException e)
		{
			return false;
		}
		return false;
	}
	
	@Override
	public void onEntityExplode(EntityExplodeEvent event)
	{
		if(event.getEntity() != null)
			event.setCancelled(cancelAction(event.getEntity(), "explosion"));
		else
			event.setCancelled(cancelAction(event.getLocation(), "explosion"));
	}
	
	@Override
	public void onExplosionPrime(ExplosionPrimeEvent event)
	{
		event.setCancelled(cancelAction(event.getEntity(), "explosion"));
	}
	
	public boolean cancelAction(Entity entity, String type)
	{
		try {
			Location location = entity.getLocation();
			Zone zone = plugin.getZone(location);
			if((zone.protection.get(type) == true))
			{
				//player.sendMessage("Zone "+zone.name+" has "+type+" protection.");
				entity.remove();
				return true;
			}
		} catch(NullPointerException e)
		{
			//e.printStackTrace();
			//player.sendMessage("The wilderness has "+type+" protection.");
			return true;
		}
		return false;
	}
	
	public boolean cancelAction(Location location, String type)
	{
		try {
			Zone zone = plugin.getZone(location);
			if((zone.protection.get(type) == true))
			{
				return true;
			}
		} catch(NullPointerException e)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			if(event.getCause() == DamageCause.BLOCK_EXPLOSION)
			{
				Player player = (Player) event.getEntity();
				event.setCancelled(cancelAction(player.getLocation(), "explosion"));
			}
		}
	}
}
