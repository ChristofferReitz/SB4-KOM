package dk.sdu.student.chrei21.common.services;

import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;

/**
 * Process entity after all ordinary processing.
 * This can be for collision detection or similar elements, that needs to be processed after all entities has been processed.
 * Pre-condition: A game tick has passed since last call and all entities has been processed.
 * Post-condition: The entity has been processed and updated.
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
