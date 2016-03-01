package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener;

import java.util.Random;
import java.util.UUID;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_SkillMiscListener implements Listener {

	private static FileConfiguration conf = TouhouMC_Races_Basic.conf;

	public Races_SkillMiscListener(TouhouMC_Races_Basic thrplugin){
		thrplugin.getServer().getPluginManager().registerEvents(this, thrplugin);
	}

	@EventHandler
	public void onSkillDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(e.getDamager() instanceof Snowball){
				Entity damagerentity = e.getDamager();
				Snowball snowball = (Snowball)damagerentity;
				if (snowball.hasMetadata("seirei-lightball")) {
					//����e
					e.setDamage(6.0D);
				}else if(snowball.hasMetadata("kappa-yukidama")){
					e.setDamage(20);
					p.sendMessage(((Player)snowball.getShooter()).getName() + "����̍U�����󂯂��I");
				}else if(snowball.hasMetadata("fireeffect")){
				     e.setDamage(15);
				     p.setFireTicks(160);
				}else if (snowball.hasMetadata("hannrei-curseball")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 3));
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 150, 3));
					if (((e.getEntity() instanceof Player)) && (Bukkit.getPlayer(UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString())) != null)){
						if (conf.getInt("user." + p.getUniqueId() + ".spilit") >= 30){
							conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + 30.0D));
							conf.set("user." + p.getUniqueId() + ".spilit", Double.valueOf(conf.getInt("user." + p.getUniqueId() + ".spilit") - 30.0D));
							if (conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
								conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
							}
						}else{
							conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Integer.valueOf(conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + conf.getInt("user." + p.getUniqueId() + ".spilit")));
							conf.set("user." + p.getUniqueId() + ".spilit", Integer.valueOf(0));
							if (conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
								conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
							}
						}
						p.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_PURPLE + "��͂��z�����ꂽ�I�I�I");
					}
//�N���b�N�V���b�g�ł͂��̕��͋@�\���܂���
				}else if((Player)e.getEntity() == snowball.getShooter()){
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e){
		Entity ent = e.getEntity();
		if(ent.getType().equals(EntityType.PRIMED_TNT)){
			if(ent.hasMetadata("kappa-tnt")){
				final World world = e.getLocation().getWorld();
				final Location loc = e.getLocation();
				e.setCancelled(true);
				world.createExplosion(loc, 0.0F);
				final Player shooter = Bukkit.getServer().getPlayer(ent.getMetadata("kappa-tnt").get(0).asString());
				for (int shot = 200; shot > 0; shot--){
					int x = new Random().nextInt(90) - 45;
					int y = new Random().nextInt(70) - 45;
					int z = new Random().nextInt(90) - 45;
					Snowball snowball = world.spawn(loc, Snowball.class);
					snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, true));
					snowball.setShooter(shooter);
					Vector vectory = new Vector(x, y, z);
					snowball.setVelocity(vectory);
				}
				Bukkit.getScheduler().runTaskLater(TouhouMC_Races_Basic.plugin0, new Runnable() {
					public void run() {
						world.createExplosion(loc, 0.0F);
						for (int shot = 200; shot > 0; shot--){
							int x = new Random().nextInt(90) - 45;
							int y = new Random().nextInt(70) - 45;
							int z = new Random().nextInt(90) - 45;
							Snowball snowball = world.spawn(loc, Snowball.class);
							snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, true));
							snowball.setShooter(shooter);
							Vector vectory = new Vector(x, y, z);
							snowball.setVelocity(vectory);
						}
					}
				}, 5);
				Bukkit.getScheduler().runTaskLater(TouhouMC_Races_Basic.plugin0, new Runnable() {
					public void run() {
						world.createExplosion(loc, 0.0F);
						for (int shot = 170; shot > 0; shot--){
							int x = new Random().nextInt(90) - 45;
							int y = new Random().nextInt(70) - 45;
							int z = new Random().nextInt(90) - 45;
							Snowball snowball = world.spawn(loc, Snowball.class);
							snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, true));
							snowball.setShooter(shooter);
							Vector vectory = new Vector(x, y, z);
							snowball.setVelocity(vectory);
						}
					}
				},10);
			}
		}
	}
}
