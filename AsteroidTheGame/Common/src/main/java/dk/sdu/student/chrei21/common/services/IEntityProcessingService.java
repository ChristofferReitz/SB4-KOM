package dk.sdu.student.chrei21.common.services;

import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;
/**
 * Process the entity.
 * Pre-condition: A game tick has passed since last call.<br />
 * Post-condition: The entity has been processed and updated.
 */

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
