package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;
import org.mini2Dx.core.serialization.SerializationException;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.element.Container;
import org.mini2Dx.ui.element.Div;
import org.mini2Dx.ui.element.TextButton;
import org.mini2Dx.ui.event.ActionEvent;
import org.mini2Dx.ui.listener.ActionListener;
import org.mini2Dx.ui.style.UiTheme;

public class MainMenu extends BasicGameScreen {
    public final static int ID = 1;

    private AssetManager assetManager;
    private UiContainer uiContainer;
    private boolean isGameStarting = false;

    @Override
    public void initialise(GameContainer gc) {
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new ClasspathFileHandleResolver(), new InternalFileHandleResolver());
        assetManager = new AssetManager(fileHandleResolver);
        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        uiContainer = new UiContainer(gc, assetManager);
        Gdx.input.setInputProcessor(uiContainer);
        Container container = null;
        try {
            container = Mdx.xml.fromXml(Gdx.files.internal("mainmenu_ui.xml").reader(), Container.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        assert container != null; //your xml layout file is invalid.
        uiContainer.add(container);
        uiContainer.getChild(0).set(BreakoutGame.gameWidth / 4, BreakoutGame.gameHeight / 4, BreakoutGame.gameWidth / 2, BreakoutGame.gameHeight / 2);
        Div mainColumn = (Div) uiContainer.getElementById("mainColumn");
        TextButton newGameButton = (TextButton) mainColumn.getChild(0);
        TextButton quitButton = (TextButton) mainColumn.getChild(1);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            @Override
            public void onActionEnd(ActionEvent event) {
                isGameStarting = true;
            }
        });

        quitButton.setYFlex(20);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            @Override
            public void onActionEnd(ActionEvent event) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void update(GameContainer gc, ScreenManager screenManager, float delta) {
        if (!assetManager.update()) {
            return;
        }
        if (!UiContainer.isThemeApplied()) {
            UiContainer.setTheme(assetManager.get(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class));
        }
        uiContainer.update(delta);
        if (isGameStarting) {
            screenManager.enterGameScreen(BreakoutGame.ID, new FadeOutTransition(),
                    new FadeInTransition());
            isGameStarting = false;
        }
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        uiContainer.interpolate(alpha);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        uiContainer.render(g);
    }

    @Override
    public int getId() {
        return ID;
    }
}
