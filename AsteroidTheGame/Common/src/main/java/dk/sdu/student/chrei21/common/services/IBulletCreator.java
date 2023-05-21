package dk.sdu.student.chrei21.common.services;

import dk.sdu.student.chrei21.common.data.Entity;
import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;

public interface IBulletCreator {
    /**
     * Start the plugin.
     *
     * Pre-condition: Game running and shooter wants bullet to appear.
     * Post-condition: Bullet entity that is ready to be added to the game world.
     */
    Entity create(Entity shooter, GameData gameData);
}
