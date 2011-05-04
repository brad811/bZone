package com.bradsproject.BradleyJewell.bZone;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class bZoneHealthTask implements Runnable
{
	private final bZone plugin;
	
	public bZoneHealthTask(bZone instance)
	{
		plugin = instance;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 20);
	}

	@Override
	public void run()
	{
		List<World> worlds = plugin.getServer().getWorlds();
		for(World world : worlds)
		{
			List<LivingEntity> players = world.getLivingEntities();
			for(LivingEntity p : players)
			{
				if(!(p instanceof Player))
					continue;
				
				Player player = (Player)p;
				
				try {
					try {
						Zone zone = plugin.getZone(player.getLocation());
						if(zone == null)
						{
							Wilderness wild = plugin.getWilderness(player.getLocation());
							if(wild.hurting && !wild.hasPlayer(player.getName()))
							{
								if(player.getHealth() > 0)
									player.setHealth(player.getHealth() - 1);
							}
						}
						else if(zone.healing)
						{
							if(player.getHealth() < 20 && player.getHealth() > 0)
								player.setHealth(player.getHealth() + 1);
						}
					} catch(NullPointerException e)
					{
						
					}
				} catch(IllegalArgumentException e)
				{
					// bad health setting (not between 0 and 200)
				}
			}
		}
	}
	
}
