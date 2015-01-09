package jp.mydns.dyukusi.biomenotificator;

import java.util.List;
import jp.mydns.dyukusi.biomenotificator.listener.PlayerLoginOut;
import jp.mydns.dyukusi.biomenotificator.task.Notificator;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class BiomeNotificator extends JavaPlugin {

	@Override
	public void onEnable() {

		this.getServer().getPluginManager().registerEvents(new PlayerLoginOut(this), this);

		int i = 0;
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.run_notificator(player, i, false);
			i++;
		}
	}

	@Override
	public void onDisable() {

	}

	public void run_notificator(Player player, long offset, boolean first_notify) {
		player.setMetadata("biome", new FixedMetadataValue(this, null));

		if (first_notify) {
			new Notificator(this, player).runTaskTimerAsynchronously(this, 120L, 60L);
		} else {
			new Notificator(this, player).runTaskTimerAsynchronously(this, (long) (offset * 13), 60L);
		}

	}

	public void set_meta_current_biome(Player player) {
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();
		player.setMetadata("biome", new FixedMetadataValue(this, player.getWorld().getBiome(x, z)));
	}

	public Biome get_current_biome(Player player) {
		return player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
	}

	public boolean isSame_biome_with_last_time(Player player) {
		List<MetadataValue> values = player.getMetadata("biome");
		Biome before_biome = null;

		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName())) {
				before_biome = (Biome) value.value();
				break;
			}
		}

		Biome current_biome = this.get_current_biome(player);

		if (current_biome.equals(before_biome)) {
			return true;
		} else {
			return false;
		}

	}
}
