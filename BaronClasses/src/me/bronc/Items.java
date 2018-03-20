package me.bronc;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Items {
	
	
	
	
	public static ItemStack brute1() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aDefensive Stance&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}

	public static ItemStack brute2() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aMagical Shield&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack brute3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aRampage&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack mage1() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aFire Charge&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}

	public static ItemStack mage2() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aBurning Soul&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack mage3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aEruption&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack summoner1() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aSummoning Ritual&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}

	public static ItemStack summoner2() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aDetonate Minions&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack summoner3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aGolem&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack warrior1() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aShockwave&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}

	public static ItemStack warrior2() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aCharge&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack warrior3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aFull Recovery&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack archer3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aExplosive Arrow&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack healer1() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aRegenerative Aura&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack healer2() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aHealer's Beam&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack healer3() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aAura's Blessing&7«"));

		item.setItemMeta(itemMeta);

		return item;
	}
	public static ItemStack archer() {
		ItemStack archer = new ItemStack(Material.BOW);
		ItemMeta aMeta = archer.getItemMeta();
		aMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aArcher&7«"));
		ArrayList<String> aLore = new ArrayList<String>();
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Charged Shot &8(Default)"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Barrage&8 (Lv. 5)"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Tele-Arrow &8(Lv. 10)"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Explosive Shot&8 (Lv. 15)"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&e 5&7/&c10"));
		aLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Ranged Damage"));
		aMeta.setLore(aLore);
		archer.setItemMeta(aMeta);
		return archer;
	}
	public static ItemStack brute() {
		ItemStack brute = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aBrute&7«"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Leap &8(Default)"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Defensive Stance&8 (Lv. 5)"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Magical Shield &8(Lv. 10)"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Rampage&8 (Lv. 15)"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&a 3&7/&c10"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Melee Tank"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack mage() {
		ItemStack brute = new ItemStack(Material.STICK);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aMage&7«"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Fireball"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Fire Charge"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Burning Soul"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Eruption"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&e 6&7/&c10"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Ranged Spellcaster"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack summoner() {
		ItemStack brute = new ItemStack(Material.BLAZE_ROD);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aSummoner&7«"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5TBD"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5TBD"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5TBD"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5TBD"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&c 8&7/&c10"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Melee Spellcaster"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack warrior() {
		ItemStack brute = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aWarrior&7«"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Rage"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Shockwave"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Charge"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Full Recovery"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&a 2&7/&c10"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Melee Damage"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack healer() {
		ItemStack brute = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aHealer&7«"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&8&oStarter Class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nSpells:"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Cure Wounds"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Regenerative Aura"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Healer's Blessing"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7- &5Oasis"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Difficulty Level:&e 6&6&7/&c10"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7&nClass Type:&a Ranged Healer"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack emerald() {
		ItemStack brute = new ItemStack(Material.EMERALD);
		ItemMeta bMeta = brute.getItemMeta();
		bMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aPick a class to get started."));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7Choose a class to the right"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7or left to start off your adventure."));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7The spells and difficulty of the class"));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7can be read on each peice of paper."));
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7Thanks for choosing &3Baron&6MC"));
		bMeta.setLore(bLore);
		brute.setItemMeta(bMeta);
		return brute;
	}
	public static ItemStack attack() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aAttack Power"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&7/&c50"));

		itemMeta.setLore(bLore);

		item.setItemMeta(itemMeta);

		return item;
	}

}
