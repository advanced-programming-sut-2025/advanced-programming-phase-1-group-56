package io.src.view.GameMenus;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import io.src.model.App;
import io.src.model.GameAssetManager;

public class EnergyBar extends Group {

    private final Image energyFill;
    private final Image frame;

    private float barWidth = 40f;
    private float barHeight = 220f;
    private float padding = 10.5f;

    public EnergyBar() {
        Stack energyStack = new Stack();

        frame = new Image(GameAssetManager.getGameAssetManager().getEnergyBar());
        energyFill = new Image(GameAssetManager.getGameAssetManager().getGreenBar());


        frame.setSize(barWidth, barHeight);
        energyFill.setSize(barWidth - 2 * padding, barHeight - 6 * padding);
        energyFill.setOrigin(Align.bottom);
        energyFill.setPosition(padding, padding);

        Stack fillStack = new Stack();
        fillStack.setSize(barWidth, barHeight);
        fillStack.add(energyFill);

        energyStack.setSize(barWidth, barHeight);
        energyStack.add(frame);
        energyStack.add(fillStack);

        addActor(energyStack);
        setSize(barWidth, barHeight);
        updateEnergyBar();
    }

    public void updateEnergyBar() {
        double energy = App.getMe().getEnergy().getEnergy();
        double maxEnergy = App.getMe().getMaxEnergy();
        float percent = (float) (energy / maxEnergy);
        percent = MathUtils.clamp(percent, 0, 1);

        float fillHeight = (barHeight - 6 * padding) * percent;
        energyFill.setSize(barWidth - 2 * padding, fillHeight);
        energyFill.setPosition(padding, padding);
    }

    public void setBarSize(float width, float height) {
        this.barWidth = width;
        this.barHeight = height;

        frame.setSize(width, height);
        setSize(width, height);

        updateEnergyBar();
    }
}
