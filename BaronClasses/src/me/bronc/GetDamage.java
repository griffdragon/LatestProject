package me.bronc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class GetDamage {
	public static int helmetDamage;
	public static int chestDamage;
	public static int legsDamage;
	public static int bootsDamage;
	public static int armorDamage;

	public static int getHDamage(Player p) {
		helmetDamage = 0;
		ItemMeta helmetlore = p.getInventory().getHelmet().getItemMeta();
		if (p.getInventory().getHelmet() != null) {
			for (int i = 0; i < 31; i++) {
				if (helmetlore != null && helmetlore.getLore() != null && helmetlore.getLore()
						.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
					helmetDamage = helmetDamage + i;
				}
			}
		} else {
			helmetDamage = 0;
		}
		return helmetDamage;
	}

	public static int getLDamage(Player p) {
		legsDamage = 0;
		ItemMeta helmetlore = p.getInventory().getLeggings().getItemMeta();
		if (p.getInventory().getHelmet() != null) {
			for (int i = 0; i < 31; i++) {
				if (helmetlore != null && helmetlore.getLore() != null && helmetlore.getLore()
						.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
					legsDamage = legsDamage + i;
				}
			}
		} else {
			legsDamage = 0;
		}
		return legsDamage;
	}

	public static int getBDamage(Player p) {
		bootsDamage = 0;
		ItemMeta helmetlore = p.getInventory().getBoots().getItemMeta();
		if (p.getInventory().getHelmet() != null) {
			for (int i = 0; i < 31; i++) {
				if (helmetlore != null && helmetlore.getLore() != null && helmetlore.getLore()
						.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
					bootsDamage = bootsDamage + i;
				}
			}
		} else {
			bootsDamage = 0;
		}
		return bootsDamage;
	}

	public static int getCDamage(Player p) {
		chestDamage = 0;
		ItemMeta helmetlore = p.getInventory().getChestplate().getItemMeta();
		if (p.getInventory().getHelmet() != null) {
			for (int i = 0; i < 31; i++) {
				if (helmetlore != null && helmetlore.getLore() != null && helmetlore.getLore()
						.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
					chestDamage = chestDamage + i;
				}
			}
		} else {
			chestDamage = 0;
		}
		return chestDamage;
	}

	public static int getDamage(Player p) {
		armorDamage = getCDamage(p) + getHDamage(p) + getLDamage(p) + getBDamage(p);
		return armorDamage;

	}
}
