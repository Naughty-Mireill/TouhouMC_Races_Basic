<<<<<<< HEAD
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class Races_Global extends JavaPlugin implements Listener {

	////冗長防止
	public static boolean magic_iscastable(Player pl, int mana,String string){
		if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "他の魔法を詠唱中です");
			return false;
		} else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "他の魔法を使用中です");
			return false;
		} else {
			if (TouhouMC_Races_Basic.conf.getDouble("user." + pl.getUniqueId() + ".spilit") > mana){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.LIGHT_PURPLE + string);
				return true;
			}else{
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "霊力が不足しています");
				return false;
			}
		}
    }

	//リスポーン体力調整グローバル (THSkillGlobalより移動)
	public static void global_respawnhealth(Player pl, Plugin plugin, PlayerRespawnEvent event){
		pl.setMaxHealth(100D);
	}

	//霊力調整グローバル (THSkillGlobalより移動)
	public static void global_charge_mana(Player pl, Plugin plugin, String pluginpre, PlayerInteractEvent event){
		Material dust_is_ok = pl.getItemInHand().getType() ;
		if (pl.getMetadata("spilituse").get(0).asDouble() != 0){
			 MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 0) ;
			 pl.setMetadata("spilituse", spilituse);
			 pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.WHITE + "霊力ノーマル");
		}else{
			if (dust_is_ok == Material.SUGAR){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 1) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.AQUA + "霊力消費小");
			}else if (dust_is_ok == Material.SULPHUR){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 3) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GRAY + "霊力消費大");
			}else if (dust_is_ok == Material.GLOWSTONE_DUST){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, -2) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "霊力回復中");
			}
		}
	}

	public static void NoDamageTick(Plugin plugin, final Player p, int wait, final int tick){
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				p.setNoDamageTicks(tick);
			}
		}, wait);
	}
	
	@SuppressWarnings({ "deprecation" })
	public static boolean No_Team_Friendly_Fire(Plugin plugin, Player damager,Player victim)
	{
		Team damagerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(damager);
		Team victimTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(victim);
		if (damagerTeam != null && victimTeam != null)
		{
			if (damagerTeam.getName().equalsIgnoreCase(victimTeam.getName()))
			{
				if (damagerTeam.allowFriendlyFire() == false){
					return true;
				}
			}
		}
		return false;
	}
}
=======
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class Races_Global extends JavaPlugin implements Listener {

	////冗長防止
	public static boolean magic_iscastable(Player pl, int mana,String string){
		if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "他の魔法を詠唱中です");
			return false;
		} else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "他の魔法を使用中です");
			return false;
		} else {
			if (TouhouMC_Races_Basic.conf.getDouble("user." + pl.getUniqueId() + ".spilit") > mana){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.LIGHT_PURPLE + string);
				return true;
			}else{
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "霊力が不足しています");
				return false;
			}
		}
    }

	//リスポーン体力調整グローバル (THSkillGlobalより移動)
	public static void global_respawnhealth(Player pl, Plugin plugin, PlayerRespawnEvent event){
		pl.setMaxHealth(100D);
	}

	//霊力調整グローバル (THSkillGlobalより移動)
	public static void global_charge_mana(Player pl, Plugin plugin, String pluginpre, PlayerInteractEvent event){
		Material dust_is_ok = pl.getItemInHand().getType() ;
		if (pl.getMetadata("spilituse").get(0).asDouble() != 0){
			 MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 0) ;
			 pl.setMetadata("spilituse", spilituse);
			 pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.WHITE + "霊力ノーマル");
		}else{
			if (dust_is_ok == Material.SUGAR){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 1) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.AQUA + "霊力消費小");
			}else if (dust_is_ok == Material.SULPHUR){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 3) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GRAY + "霊力消費大");
			}else if (dust_is_ok == Material.GLOWSTONE_DUST){
				MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, -2) ;
				pl.setMetadata("spilituse", spilituse);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "霊力回復中");
			}
		}
	}

	public static void NoDamageTick(Plugin plugin, final Player p, int wait, final int tick){
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				p.setNoDamageTicks(tick);
			}
		}, wait);
	}
	
	@SuppressWarnings({ "deprecation" })
	public static boolean No_Team_Friendly_Fire(Plugin plugin, Player damager,Player victim)
	{
		Team damagerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(damager);
		Team victimTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(victim);
		if (damagerTeam != null && victimTeam != null)
		{
			if (damagerTeam.getName().equalsIgnoreCase(victimTeam.getName()))
			{
				if (damagerTeam.allowFriendlyFire() == false){
					return true;
				}
			}
		}
		return false;
	}
}
>>>>>>> origin/master
