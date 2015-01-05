package jp.mydns.dyukusi.MurdererKiller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MKListener implements Listener {
	private final MurdererKiller plugin;

	MKListener(MurdererKiller mk) {
		this.plugin = mk;
	}

	@EventHandler
	public void PlayerJoingEvent(PlayerLoginEvent join) {
		plugin.getServer().broadcastMessage("MurdererKiller test");
	}

	//プレイヤーKILL
	@EventHandler
	public void PlayerKill(PlayerDeathEvent death) {
		Player Killed = death.getEntity();
		Player Killer = death.getEntity().getKiller();
		
		if(Killer == null)
			return;
		
		plugin.getServer().broadcastMessage(Killer.getName()+" , "+Killer.getEntityId()+" , "+Killer.getPlayerTime());
		
		
		// �E�Q�҂�
		Killer.sendMessage("§4[MurdererKiller] あなたは" + Killed.getName()
				+ "を殺害したため、"+Killed.getName()+"の判断よって投獄される可能性があります。§b< You killed "+Killed.getName()+" and you could be jailed by " + Killed.getName() + ". >");

		// ��Q�҂�
		Killed.sendMessage("§4[MurdererKiller] あなたは" + Killer.getName() + "に殺害されたため、投獄する権利を獲得しました。 §b< You aquire power to jail " + Killer.getName() + ".");
		Killed.sendMessage("§4[MurdererKiller] " + Killer.getName() + "を投獄するには §c/mk jail "+Killer.getName()+"§4 とチャット欄に入力し、実行して下さい。§b< If you want to jail "
				+ Killer.getName() + ", type §c/mk jail " + Killer.getName()+"§4>");
		Killed.sendMessage("§4[MurdererKiller] 釈放したい場合はDyukusi[ mail: dyukusin@gmail.com , skype_id: dyukusin ]までご連絡下さい。　< Please note that you have to contact Dyukusi if you want to release him/her.");

		Killed.setMetadata("killed", new FixedMetadataValue(plugin, Killer.getName()));

	}

}
