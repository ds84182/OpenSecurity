/**
 * 
 */
package pcl.opensecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

/**
 * @author Caitlyn
 *
 */
public class Config {
	public List<String> alarmsConfigList = new ArrayList<String>();
	//public final boolean render3D;
	public final boolean enableplaySoundAt;
	public final boolean enableMUD;
	public final int rfidMaxRange;
	public final boolean ignoreUUIDs;
	public boolean registerBlockBreak;

	public Config(Configuration config) {
		config.load();
		enableMUD = config.get("options", "enableMUD", true, "Enable the Update Checker? Disabling this will remove all traces of the MUD.").getBoolean(true);
		rfidMaxRange = config.getInt("rfidMaxRange", "options", 16, 1, 64, "The maximum range of the RFID Reader in blocks");
		enableplaySoundAt = config.get("options", "playSoundAt", false, "Enable/Disable the playSoundAt feature of alarm blocks, this allows any user to play any sound at any location in a world, and is exploitable, disabled by default.").getBoolean(false);
		ignoreUUIDs = config.getBoolean("ignoreUUIDs", "options", false, "RFID and Mag cards will return '-1' for UUIDs.  Allows for less secure security.");
		registerBlockBreak = config.getBoolean("registerBlockBreak", "options", true, "If false the block break event will not be registered, which will leave Door Controllers and Security Doors able to be broken.");

		if (config.hasChanged()) {
			config.save();
		}
	}
}
