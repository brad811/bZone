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
			for(LivingEntity player : players)
			{
				if(!(player instanceof Player))
					continue;
				
				try {
					try {
						Zone zone = plugin.getZone(player.getLocation());
						if(zone.healing)
						{
							if(player.getHealth() < 20 && player.getHealth() > 0)
								player.setHealth(player.getHealth() + 1);
						}
					} catch(NullPointerException e)
					{
						// user is in wilderness, don't heal them
						// ... HURT THEM.
						//player.setHealth(player.getHealth() - 1);
					}
				} catch(IllegalArgumentException e)
				{
					// bad health setting (not between 0 and 200)
				}
			}
		}
	}
	
}
