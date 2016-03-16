package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener;

import java.io.File;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponPreShootEvent;

public class CrackShotListener implements Listener {
	
	static String pluginpre = TouhouMC_Races_Basic.tmc_Races_pre;
	static File file = TouhouMC_Races_Basic.configfile;
	static FileConfiguration conf = TouhouMC_Races_Basic.conf;

	public CrackShotListener(TouhouMC_Races_Basic thrplugin){
		thrplugin.getServer().getPluginManager().registerEvents(this, thrplugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void race_deny_guns(WeaponPreShootEvent event) {
		String gunname = event.getWeaponTitle();
		Player pl = event.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toLowerCase();
		String inforace = conf.getString("race." + race);
		if (inforace != null)
		{
			String[] racetype = conf.getString("race." + race + ".gun.guntype").split("-");
			int racelevel = conf.getInt("race." + race + ".gun.gunlevel");
			String[] gundata = gunname.split("-");
			boolean race_ok = false;
			if (racelevel >= Integer.parseInt(gundata[1]))
			{
				for (String onerace : racetype)
				{
					if (gundata[0].contains(onerace)) race_ok = true;
				}
			}
			if(!race_ok)
			{
				pl.sendMessage(pluginpre + ChatColor.RED + "Œ»İ‚Ìí‘°‚Å‚Íg—p‚Å‚«‚Ü‚¹‚ñI");
				pl.playSound(pl.getLocation(), Sound.LAVA_POP, 1, 1);
				event.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void WeaponDamage(WeaponDamageEntityEvent event){
		Player shooter = event.getPlayer();
		Entity victim = event.getVictim();
		Races_EventActionListener.EntityDamageByEntity(new EntityDamageByEntityEvent(shooter,victim,DamageCause.PROJECTILE,event.getDamage()));
	}
	
}
