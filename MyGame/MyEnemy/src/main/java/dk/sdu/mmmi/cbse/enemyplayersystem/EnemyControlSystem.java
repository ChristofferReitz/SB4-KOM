package dk.sdu.mmmi.cbse.enemyplayersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;
import static java.lang.Math.sin;

public class EnemyControlSystem implements IEntityProcessingService {

    private float totalTime = 0f;
    private float getRandomNumber(float min, float max){
        return (float) ((Math.random() * (max-min)) + min);
    }

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            
            //Random movements for the enemy ship
            this.totalTime = (this.totalTime + gameData.getDelta()) % 100;
            float controlAmplifier = (float) (Math.random() * 2f) + 0.1f;
            float controlGeneralAmplifier = (float) (Math.random() * 2f) + 0.1f;
            movingPart.setLeft((Math.sin(totalTime * controlAmplifier + (Math.random() * 2f)) * controlGeneralAmplifier) < this.getRandomNumber(-0.3f, -controlAmplifier));
            movingPart.setRight((Math.sin(totalTime * controlAmplifier + (Math.random() * 2f)) * controlGeneralAmplifier) > this.getRandomNumber(0.8f, controlGeneralAmplifier));
            movingPart.setUp(this.getRandomNumber(0.01f, 1f) > this.getRandomNumber(0.5f, 1f));

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
