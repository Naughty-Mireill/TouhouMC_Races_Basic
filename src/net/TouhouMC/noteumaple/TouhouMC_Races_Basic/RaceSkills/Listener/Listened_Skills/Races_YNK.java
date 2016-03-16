<<<<<<< HEAD
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Races_YNK extends JavaPlugin {
	//UŒ‚ƒXƒLƒ‹Œn
	///ƒpƒbƒVƒuŒn
	//ƒ_ƒ[ƒWŒn
	public static void kami_faith_attack(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 0.0D && event.getDamage() <= 4.0D){
				event.setDamage(event.getDamage() + 1.0D);
			}else if (event.getDamage() > 4.0D && event.getDamage() <= 8.0D){
				event.setDamage(event.getDamage() + 2.0D);
			}else if (event.getDamage() > 8.0D && event.getDamage() <= 12.0D){
				event.setDamage(event.getDamage() + 3.0D);
			}else if (event.getDamage() > 12.0D){
				event.setDamage(event.getDamage() + 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() * 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 4);
		}
	}

	//–hŒäŒn
	public static void kami_faith_defence(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 2.0D && event.getDamage() <= 6.0D){
				event.setDamage(event.getDamage() - 1.0D);
			}else if (event.getDamage() > 6.0D && event.getDamage() <= 10.0D){
				event.setDamage(event.getDamage() - 2.0D);
			}else if (event.getDamage() > 10.0D && event.getDamage() <= 14.0D){
				event.setDamage(event.getDamage() - 3.0D);
			}else if (event.getDamage() > 14.0D){
				event.setDamage(event.getDamage() - 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() / 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 2);
		}
	}

	public static void houzyousin_feed(Player pl, Plugin plugin, EntityDamageEvent event){
		if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) event.setCancelled(true);
	}

	public static void houzyousin_potato(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost){
		if ((Math.random() >= 0.8D) && ((event.getEntity() instanceof Player)) && boost > 0.0D){
			((Player)event.getEntity()).setFoodLevel(((Player)event.getEntity()).getFoodLevel() - 1);
			event.getEntity().sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + pl.getName() + "‚Í‚¨‚¢‚µ‚¢ˆð‚ðŒ©‚¹‚Â‚¯‚Ä‚«‚½II");
		}
	}

	public static void yakusin_darkside(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Player && event.getDamage() >= pl.getHealth()){
			Player killplayer = (Player) event.getDamager();
			if (!killplayer.isDead()){
				killplayer.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_RED + "‚ ‚È‚½–ï_‚ÌâM‚è‚ðŽó‚¯‚½III");
				killplayer.damage(50.0D);
			}
		}
	}
}
=======
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Races_YNK extends JavaPlugin {
	//UŒ‚ƒXƒLƒ‹Œn
	///ƒpƒbƒVƒuŒn
	//ƒ_ƒ[ƒWŒn
	public static void kami_faith_attack(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 0.0D && event.getDamage() <= 4.0D){
				event.setDamage(event.getDamage() + 1.0D);
			}else if (event.getDamage() > 4.0D && event.getDamage() <= 8.0D){
				event.setDamage(event.getDamage() + 2.0D);
			}else if (event.getDamage() > 8.0D && event.getDamage() <= 12.0D){
				event.setDamage(event.getDamage() + 3.0D);
			}else if (event.getDamage() > 12.0D){
				event.setDamage(event.getDamage() + 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() * 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 4);
		}
	}

	//–hŒäŒn
	public static void kami_faith_defence(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 2.0D && event.getDamage() <= 6.0D){
				event.setDamage(event.getDamage() - 1.0D);
			}else if (event.getDamage() > 6.0D && event.getDamage() <= 10.0D){
				event.setDamage(event.getDamage() - 2.0D);
			}else if (event.getDamage() > 10.0D && event.getDamage() <= 14.0D){
				event.setDamage(event.getDamage() - 3.0D);
			}else if (event.getDamage() > 14.0D){
				event.setDamage(event.getDamage() - 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() / 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 2);
		}
	}

	public static void houzyousin_feed(Player pl, Plugin plugin, EntityDamageEvent event){
		if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) event.setCancelled(true);
	}

	public static void houzyousin_potato(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost){
		if ((Math.random() >= 0.8D) && ((event.getEntity() instanceof Player)) && boost > 0.0D){
			((Player)event.getEntity()).setFoodLevel(((Player)event.getEntity()).getFoodLevel() - 1);
			event.getEntity().sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + pl.getName() + "‚Í‚¨‚¢‚µ‚¢ˆð‚ðŒ©‚¹‚Â‚¯‚Ä‚«‚½II");
		}
	}

	public static void yakusin_darkside(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Player && event.getDamage() >= pl.getHealth()){
			Player killplayer = (Player) event.getDamager();
			if (!killplayer.isDead()){
				killplayer.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_RED + "‚ ‚È‚½–ï_‚ÌâM‚è‚ðŽó‚¯‚½III");
				killplayer.damage(50.0D);
			}
		}
	}
}
>>>>>>> origin/master
