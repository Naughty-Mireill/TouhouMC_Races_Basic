<<<<<<< HEAD
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Races_Global;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_YUM extends JavaPlugin {
	////アクティブスキル系
	///移動スキル系
	public static void tenngu_kamikaze(Player pl, int boost){
		if (boost > 0 && boost <= 2){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(8.0D));
			//pl.setFallDistance(-30F);
			//イベントリスナーへ移行 (EntityDamageEvent)
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, -1.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(14.0D));
			//pl.setFallDistance(-10F);
			//イベントリスナーへ移行 (EntityDamageEvent)
		}
	}

	///攻撃スキル系
	public static void youma_golden_shockwave(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "金の斧で全てを吹き飛ばす！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 0.0F);
		pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = Races_Global.No_Team_Friendly_Fire(plugin, pl, (Player) enemy);
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double(enemy.getLocation().getX() - pl.getLocation().getX()).doubleValue(), 0.0D, new Double(enemy.getLocation().getZ() - pl.getLocation().getZ()).doubleValue())));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
			}}
		}
	}

	public static void kappa_stone_tnt(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "石の斧でTNTを投げた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.FIRE_IGNITE, 1.0F, 0.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        TNTPrimed tnt = (TNTPrimed)pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getName());
        tnt.setMetadata("kappa-tnt", shooter);
        tnt.setVelocity(velocity);
        tnt.setIsIncendiary(true);
        tnt.setFireTicks(20);
        tnt.setFuseTicks(20);
		Races_Global.NoDamageTick(TouhouMC_Races_Basic.plugin0, pl, 15, 30);
	}

	public static void youma_wooden_upper(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "斧で地面を叩き上げた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = Races_Global.No_Team_Friendly_Fire(plugin, pl, (Player) enemy);
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(0.0D, 1.5D, 0.0D)));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 0.0F);
				}
			}
		}
	}
	
	//TODO 天狗の落下耐性
	public static void tenngu_toramporin(Player pl, final Plugin plugin, EntityDamageEvent e){
		if(pl.isSneaking()){
			double rnd = Math.random();
			if (rnd > 0.5D){
				if (rnd > 0.4D){
					e.setCancelled(true);
				}else {
					e.setDamage(e.getDamage() / 10.0D);
				}
			}else {
				e.setDamage(e.getDamage() / 6.0D);
			}
		}else {
			e.setDamage(e.getDamage() / 4.0D);
		}
	}
	
	//TODO スキマ妖 修復しよう
	public static void sukima_sukima_warp(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "スキマを開いてゲリラアタック！");
		boolean success = false;
		int dice = Bukkit.getServer().getOnlinePlayers().size() - 1;
		int num = 0;
		for (Player victim : Bukkit.getServer().getOnlinePlayers())
		{
			if (dice == num)
			{
			pl.teleport(victim.getLocation());
			pl.getWorld().playSound(pl.getLocation(), Sound.PORTAL_TRIGGER, 1.0F, 2.0F);
			if (pl.getKiller() instanceof Player)
			{
				victim.sendMessage("ハローキラー☆");
				victim.addPotionEffect(new PotionEffect (PotionEffectType.BLINDNESS , 100 , 1));
			}
			break;
		}
			num ++ ;
		}
		if (!success)
		{
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "いなかった！");
		}
	}
	
	//TODO 山河童
	@SuppressWarnings("deprecation")
	public static void yamakappa_thown_creeper(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "いけ！クリーパー！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.CREEPER_DEATH, 1.0F, 1.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        Creeper creeper = (Creeper)pl.getWorld().spawnCreature(location, EntityType.CREEPER);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getUniqueId().toString());
        creeper.setMetadata("yamagappa_creeper", shooter);
        creeper.setVelocity(velocity);
        creeper.setNoDamageTicks(1000);
        creeper.setFireTicks(20);
        creeper.setPowered(true);
	}
	
	//TODO 鴉天狗
	@SuppressWarnings("deprecation")
	public static void karasutenngu_hopping(Player pl, final Plugin plugin, int boost){
		///移動スキル系
		if (!pl.isOnGround()){
			if (boost > 0 && boost <= 2){
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(1.5D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.7D).getZ()));
			}else{
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(0.7D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.4D).getZ()));
			}
		}
	}
	
	//TODO 哨戒天狗
	public static void syoukaitenngu_scent(Player pl, final Plugin plugin, int boost){
			double scentarea;
			if (boost == 1){
				scentarea = 40D;
			}else{
				scentarea = 20D;
			}
			List<Entity> enemys=pl.getNearbyEntities(scentarea, scentarea, scentarea);
			for (Entity enemy : enemys){
				if (enemy instanceof Player && enemy.isDead() == false){
					((Player) enemy).getWorld().playEffect(((Player) enemy).getLocation().add(0D, 6D,0D), Effect.NOTE, 1);
				}
			}
	}
}
=======
package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Races_Global;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_YUM extends JavaPlugin {
	////アクティブスキル系
	///移動スキル系
	public static void tenngu_kamikaze(Player pl, int boost){
		if (boost > 0 && boost <= 2){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(8.0D));
			//pl.setFallDistance(-30F);
			//イベントリスナーへ移行 (EntityDamageEvent)
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, -1.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(14.0D));
			//pl.setFallDistance(-10F);
			//イベントリスナーへ移行 (EntityDamageEvent)
		}
	}

	///攻撃スキル系
	public static void youma_golden_shockwave(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "金の斧で全てを吹き飛ばす！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 0.0F);
		pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = Races_Global.No_Team_Friendly_Fire(plugin, pl, (Player) enemy);
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double(enemy.getLocation().getX() - pl.getLocation().getX()).doubleValue(), 0.0D, new Double(enemy.getLocation().getZ() - pl.getLocation().getZ()).doubleValue())));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
			}}
		}
	}

	public static void kappa_stone_tnt(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "石の斧でTNTを投げた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.FIRE_IGNITE, 1.0F, 0.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        TNTPrimed tnt = (TNTPrimed)pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getName());
        tnt.setMetadata("kappa-tnt", shooter);
        tnt.setVelocity(velocity);
        tnt.setIsIncendiary(true);
        tnt.setFireTicks(20);
        tnt.setFuseTicks(20);
		Races_Global.NoDamageTick(TouhouMC_Races_Basic.plugin0, pl, 15, 30);
	}

	public static void youma_wooden_upper(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "斧で地面を叩き上げた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = Races_Global.No_Team_Friendly_Fire(plugin, pl, (Player) enemy);
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(0.0D, 1.5D, 0.0D)));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 0.0F);
				}
			}
		}
	}
	
	//TODO 天狗の落下耐性
	public static void tenngu_toramporin(Player pl, final Plugin plugin, EntityDamageEvent e){
		if(pl.isSneaking()){
			double rnd = Math.random();
			if (rnd > 0.5D){
				if (rnd > 0.4D){
					e.setCancelled(true);
				}else {
					e.setDamage(e.getDamage() / 10.0D);
				}
			}else {
				e.setDamage(e.getDamage() / 6.0D);
			}
		}else {
			e.setDamage(e.getDamage() / 4.0D);
		}
	}
	
	//TODO スキマ妖
	public static void sukima_sukima_warp(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "スキマを開き自身の殺人に出会おう！");
		if (pl.getKiller() != null)
		{
		pl.teleport(pl.getKiller());
		pl.getWorld().playSound(pl.getLocation(), Sound.PORTAL_TRIGGER, 1.0F, 2.0F);
		if (pl.getKiller() instanceof Player)
		{
			((Player)pl).getKiller().sendMessage("ハローキラー☆");
			((Player)pl).getKiller().addPotionEffect(new PotionEffect (PotionEffectType.BLINDNESS , 100 , 1));
		}
		}
		else
		{
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "いなかった！");
		}
	}
	
	//TODO 山河童
	@SuppressWarnings("deprecation")
	public static void yamakappa_thown_creeper(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "いけ！クリーパー！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.CREEPER_DEATH, 1.0F, 1.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        Creeper creeper = (Creeper)pl.getWorld().spawnCreature(location, EntityType.CREEPER);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getUniqueId().toString());
        creeper.setMetadata("yamagappa_creeper", shooter);
        creeper.setVelocity(velocity);
        creeper.setNoDamageTicks(1000);
        creeper.setFireTicks(20);
        creeper.setPowered(true);
	}
	
	//TODO 鴉天狗
	@SuppressWarnings("deprecation")
	public static void karasutenngu_hopping(Player pl, final Plugin plugin, int boost){
		///移動スキル系
		if (!pl.isOnGround()){
			if (boost > 0 && boost <= 2){
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(1.5D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.7D).getZ()));
			}else{
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(0.7D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.4D).getZ()));
			}
		}
	}
	
	//TODO 哨戒天狗
	public static void syoukaitenngu_scent(Player pl, final Plugin plugin, int boost){
			double scentarea;
			if (boost == 1){
				scentarea = 40D;
			}else{
				scentarea = 20D;
			}
			List<Entity> enemys=pl.getNearbyEntities(scentarea, scentarea, scentarea);
			for (Entity enemy : enemys){
				if (enemy instanceof Player && enemy.isDead() == false){
					((Player) enemy).getWorld().playEffect(((Player) enemy).getLocation().add(0D, 6D,0D), Effect.NOTE, 1);
				}
			}
	}
}
>>>>>>> origin/master
