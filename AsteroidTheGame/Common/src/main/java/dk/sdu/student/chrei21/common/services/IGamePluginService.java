package dk.sdu.student.chrei21.common.services;

import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;

/**
 * Start the plugin.
 * Pre-condition: The game is starting and the plugin has not yet been started.
 * Post-condition: The plugin has been started and entities have been added to the world.
 */

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
