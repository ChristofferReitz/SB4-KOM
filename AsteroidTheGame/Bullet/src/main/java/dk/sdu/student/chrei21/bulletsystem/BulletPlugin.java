package dk.sdu.student.chrei21.bulletsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.student.chrei21.common.data.Color;
import dk.sdu.student.chrei21.common.data.Entity;
import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;
import dk.sdu.student.chrei21.common.data.entityparts.LifePart;
import dk.sdu.student.chrei21.common.data.entityparts.MovingPart;
import dk.sdu.student.chrei21.common.data.entityparts.PositionPart;
import dk.sdu.student.chrei21.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;
    private Entity shooter;

    public BulletPlugin(Entity shooter) {
        this.shooter = shooter;
    }

    @Override
    public void start(GameData gameData, World world) {
        bullet = createBullet(gameData);
        world.addEntity(bullet);
    }

    /**
     * Create bullet entity with default parameters based on shooter
     * <br />
     * Pre-condition: New bullet entity has to be created for the game from a shooter <br />
     * Post-condition: Bullet entity, that has parameters, such that it is shot from shooter
     *
     * @param gameData Data for the game
     * @return Bullet entity with initial data from shooter
     */
    private Entity createBullet(GameData gameData) {
        PositionPart shooterPosition = this.shooter.getPart(PositionPart.class);
        MovingPart shooterMovement = this.shooter.getPart(MovingPart.class);

        float deacceleration = 0;
        float acceleration = 0;
        float maxSpeed = 1000;
        float rotationSpeed = 5;
        float radians = shooterPosition.getRadians();

        Entity bullet = new Bullet();

        bullet.setRadius(1);

        float bx = (float) MathUtils.cos(radians) * this.shooter.getRadius() * bullet.getRadius();
        float x = bx + shooterPosition.getX();
        float by = (float) MathUtils.sin(radians) * this.shooter.getRadius() * bullet.getRadius();
        float y = by + shooterPosition.getY();
        float shootSpeed = 350 + (shooterMovement.getSpeed());

        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        bullet.setColor(new Color(1,1,1,1));
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed, shootSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 1));

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(bullet);
    }
}
