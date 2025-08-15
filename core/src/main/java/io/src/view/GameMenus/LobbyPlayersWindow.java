package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.model.App;
import io.src.model.Player;
import io.src.model.skills.Skill;

import java.util.ArrayList;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import io.src.model.Player;
import io.src.model.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class LobbyPlayersWindow extends Window implements InputProcessor {
    private SortType currentSort = SortType.SKILL;

    private Table playersTable;
    private Skin skin;
    private List<Player> currentPlayers = new ArrayList<>();

    public LobbyPlayersWindow(Skin skin) {
        super("Lobby Leaderboard", skin);
        this.skin = skin;

        setMovable(true);
        setResizable(false);
        setSize(450, 400);
        setPosition(100, 550);
        getColor().a = 0.9f;
        playersTable = new Table();
        playersTable.top().left().pad(10);
        add(playersTable).expand().fill();
        this.currentPlayers.clear();
        this.currentPlayers.addAll(App.getCurrentUser().getCurrentGame().getPlayers());
        TextButton sortButton = new TextButton("Sort: Skill", skin);
        sortButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentSort == SortType.SKILL) {
                    currentSort = SortType.GOLD;
                    sortButton.setText("Sort: Gold");
                } else if (currentSort == SortType.GOLD) {
                    currentSort = SortType.QUEST;
                    sortButton.setText("Sort: Quest");
                } else {
                    currentSort = SortType.SKILL;
                    sortButton.setText("Sort: Skill");
                }
                sortAndDisplay();
            }
        });
        playersTable.add(sortButton).colspan(4).pad(5);
        playersTable.row();

        sortAndDisplay();
    }

    public void refresh() {
        if (!currentPlayers.isEmpty()) {
            sortAndDisplay();
        }
    }

    private void sortAndDisplay() {
        playersTable.clear();

        currentPlayers.sort((p1, p2) -> {
            switch (currentSort) {
                case GOLD:
                    return Integer.compare(p2.getGold(), p1.getGold());
                case QUEST:
                    return Integer.compare(getQuestCount(p2), getQuestCount(p1));
                case SKILL:
                default:
                    double avg1 = getAverageSkill(p1);
                    double avg2 = getAverageSkill(p2);
                    if (avg1 != avg2) return Double.compare(avg2, avg1);
                    if (p1.getGold() != p2.getGold()) return Integer.compare(p2.getGold(), p1.getGold());
                    return Integer.compare(getQuestCount(p2), getQuestCount(p1));
            }
        });


        int rank = 1;
        for (Player p : currentPlayers) {
            Label nameLabel = new Label(rank + ". " + p.getUserName(), skin);
            Label skillLabel = new Label(String.format("%.1f", getAverageSkill(p)), skin);
            Label goldLabel = new Label("" + p.getGold(), skin);
            Label questLabel = new Label("" + getQuestCount(p), skin);

            if (rank == 1) {
                nameLabel.setColor(Color.GOLD);
            } else if (rank == 2) {
                nameLabel.setColor(Color.LIGHT_GRAY);
            } else if (rank == 3) {
                nameLabel.setColor(new Color(0.8f, 0.5f, 0.2f, 1f));
            }

            playersTable.row();
            playersTable.add(nameLabel).left().pad(5);
            playersTable.add(skillLabel).pad(5);
            playersTable.add(goldLabel).pad(5);
            playersTable.add(questLabel).pad(5);

            rank++;
        }
    }

    private double getAverageSkill(Player player) {
        return player.getSkills().stream().mapToInt(Skill::getLevel).average().orElse(0);
    }

    private int getQuestCount(Player player) {
        return 0;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.U) {
            if (StardewValley.getGameView().getLobbyPlayersWindow().isVisible()) {
                Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
            }
            StardewValley.getGameView().getLobbyPlayersWindow().setVisible(!StardewValley.getGameView().getLobbyPlayersWindow().isVisible());
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    public enum SortType {
        SKILL,
        GOLD,
        QUEST
    }

}
