package dk.sdu.student.chrei21.main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.student.chrei21.asteroidsystem.AsteroidControlSystem;
import dk.sdu.student.chrei21.asteroidsystem.AsteroidPlugin;
import dk.sdu.student.chrei21.bulletsystem.BulletControlSystem;
import dk.sdu.student.chrei21.bulletsystem.BulletPlugin;
import dk.sdu.student.chrei21.collisionsystem.CollisionDetector;
import dk.sdu.student.chrei21.common.data.Color;
import dk.sdu.student.chrei21.common.data.entityparts.ShootingPart;
import dk.sdu.student.chrei21.common.services.IPostEntityProcessingService;
import dk.sdu.student.chrei21.common.util.SPILocator;
import dk.sdu.student.chrei21.playersystem.PlayerControlSystem;
import dk.sdu.student.chrei21.playersystem.PlayerPlugin;
import dk.sdu.student.chrei21.enemysystem.EnemyControlSystem;
import dk.sdu.student.chrei21.enemysystem.EnemyPlugin;
import dk.sdu.student.chrei21.managers.GameInputProcessor;
import dk.sdu.student.chrei21.common.data.Entity;
import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;
import dk.sdu.student.chrei21.common.services.IEntityProcessingService;
import dk.sdu.student.chrei21.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game implements ApplicationListener {
    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        // Create initial objects
        for (IGamePluginService gamePlugin : getPluginServices()) {
            gamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            Color color = entity.getColor();
            sr.setColor(color.getR(), color.getG(), color.getB(), color.getA());

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                 i < shapex.length;
                 j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
