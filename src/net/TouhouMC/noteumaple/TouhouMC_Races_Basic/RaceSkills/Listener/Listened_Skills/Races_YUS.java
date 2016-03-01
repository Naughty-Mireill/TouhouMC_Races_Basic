package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Races_YUS extends JavaPlugin {
	///�A�N�e�B�u�X�L���n
	//�ړ��X�L���n
	public static void yousei_feather(Player pl, Plugin plugin){
		pl.setVelocity(pl.getLocation().getDirection().multiply(1.1D));
		pl.getWorld().playSound(pl.getLocation(), Sound.SHOOT_ARROW, 1.0F, 1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
	}

	//�U���X�L���n
	public static void yousei_illusion(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.LIGHT_PURPLE + "���̃V���x���̋P�����������f�킷�I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.CAT_PURR, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.HAPPY_VILLAGER, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
		for (Entity enemy : enemys){
			if ((enemy instanceof Player)){
				((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 3));
				((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 3));
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 60L);
	}

	public static void kibito_venom(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GREEN + "���l�͓ł��΂�܂����I");
		pl.getWorld().playSound(pl.getLocation(), Sound.PIG_DEATH, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.VOID_FOG, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)) {
				((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2));
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 100L);
	}

	///�p�b�V�u�X�L���n
	public static void yousei_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.9D){
			pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1.0F, 2.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
		}
    }

	public static void kobito_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.7D){
			pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1.0F, 2.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
		}else if ((event.getDamage() > 1.0D) && (plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 20)){
			event.setDamage(event.getDamage() + 2.0D);
		}else if (event.getDamage() > 1.0D){
			event.setDamage(event.getDamage() + 4.0D);
		}
	}

	public static void yousei_fall_protection(Player pl, Plugin plugin, EntityDamageEvent event){
		if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setDamage(event.getDamage() / 2.0D);
	}

	public static void satori_satori(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if(event.getDamage() >= pl.getHealth() && event.isCancelled() == false){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"), Double.valueOf(plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 50.0D));
			pl.sendMessage(event.getDamager().getName() + ":�̗�:" + ((Player)event.getDamager()).getHealth());
			pl.sendMessage(event.getDamager().getName() + ":���W:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
			pl.sendMessage(ChatColor.DARK_PURPLE + "�o��܂����E�E�E�o���ĂȂ����E�E�E");
			String satorin0 = event.getDamager().getName();
			MetadataValue satorin00 = new FixedMetadataValue(plugin, satorin0);
			pl.setMetadata("satorin0", satorin00);
		}
	}
}
