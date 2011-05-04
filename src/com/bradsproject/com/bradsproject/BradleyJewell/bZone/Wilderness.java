package com.bradsproject.BradleyJewell.bZone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

public class Wilderness
{
	public final World world;
	public boolean hurting;
	public Map<String,Boolean> protection;
	public List<String> creatures;
	public List<String> players;
	
	public Wilderness(World w)
	{
		world = w;
		
		players = new ArrayList<String>();
		creatures = new ArrayList<String>();
		protection = new HashMap<String,Boolean>();
	}
	
	public boolean hasPlayer(String player)
	{
		player = player.toLowerCase();
		if(players.contains(player))
		{
			return true;
		}
		return false;
	}
}
