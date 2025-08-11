package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.model.App;
import io.src.model.Result;

public class CheatWindow extends Window {

    private final Label cheatLabel;
    private final TextField cheatInputField;
    private Skin skin;

    // اینو نگه می‌داریم تا بعد از بستن پنجره، InputProcessor قبلی رو برگردونیم
    private InputProcessor previousProcessor;

    public CheatWindow(Skin skin) {
        super("", skin);
        this.skin = skin;
        setSize(300, 130);

        cheatLabel = new Label("cheat BOX", skin);
        cheatLabel.setAlignment(Align.center);
        this.add(cheatLabel).colspan(1).center().padTop(10).row();

        cheatInputField = new TextField("", skin);
        cheatInputField.setAlignment(Align.center);
        this.add(cheatInputField).width(250).height(50).padBottom(10).row();

        // وقتی Enter زده شد
        cheatInputField.setTextFieldListener((textField, c) -> {
            if (c == '\r' || c == '\n') {
                String command = cheatInputField.getText();
                executeCheat(command);
                // پنجره بسته میشه و InputProcessor قبلی برگردونده میشه داخل executeCheat/hideDialog
            }
        });

        // ESC برای بستن فوری
        cheatInputField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    hideDialog(getStage());
                    return true;
                }
                return false;
            }
        });

        // کل پنجره هم ESC رو هندل کنه
        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    hideDialog(getStage());
                    return true;
                }
                return false;
            }
        });

        setPosition(100, 100);
        setVisible(false);
    }

    private void executeCheat(String command) {
        Result result = App.getCurrentMenu().checkCommand(App.getScanner(), command);

        // پاکسازی/بستن
        cheatInputField.setText("");
        getStage().unfocus(cheatInputField);
        hideDialog(StardewValley.getGameView().getStage());

        // نمایش نتیجه
        WarningWindow resultWindow = StardewValley.getGameView().getWarningWindow();
        resultWindow.setVisible(true);
        resultWindow.showDialog("Cheat Result:" + ((result.isSuccess()) ? "Success" : "Failure"),
            result.message(), 400);
    }

    /**
     * باز کردن پنجره و گرفتن فوکس. این متد وضعیت پردازشگر قبلی را ذخیره می‌کند
     * و یک InputMultiplexer جدید می‌سازد که اول stage را دارد تا UI حتماً ورودی‌ها را بگیرد.
     */
    public void showWithFocus(Stage stage) {
        // ذخیرهٔ پردازشگر فعلی
        previousProcessor = Gdx.input.getInputProcessor();

        // بلافاصله interrupt flag رو روشن کن (تا game adapter زودتر چیزی consume نکنه)
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);

        // بسازیم یک multiplexer که stage را اول می‌گذارد (UI اولویت دارد)
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        if (previousProcessor != null && previousProcessor != stage) {
            // اضافه کردن پردازشگر قبلی تا زمانی که پنجره بسته شد بتونیم برگردونیم
            multiplexer.addProcessor(previousProcessor);
        }
        // ست کردن روی Gdx.input (این تضمین می‌کند stage رویدادها را دریافت کند)
        Gdx.input.setInputProcessor(multiplexer);

        // فوکس را در runnable قرار می‌دهیم تا بعد از اضافه شدن پردازنده‌ها انجام شود
        Gdx.app.postRunnable(() -> {
            this.setVisible(true);
            cheatInputField.setDisabled(false);
            stage.setKeyboardFocus(cheatInputField);
            stage.setScrollFocus(cheatInputField);
        });
    }

    /**
     * بستن پنجره و بازگرداندن InputProcessor قبلی
     */
    public void hideDialog(Stage stage) {
        // بازگرداندن flag به adapter
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);

        // بازگرداندن پردازشگر قبلی (اگر null بود، هیچ کاری نکرده و Gdx.input پاک نمیشه)
        if (previousProcessor != null) {
            Gdx.input.setInputProcessor(previousProcessor);
            previousProcessor = null;
        }

        // آزادسازی فوکس و مخفی کردن پنجره
        if (stage != null) {
            stage.unfocus(cheatInputField);
        }
        this.setVisible(false);
    }

    public Actor getTextField() {
        return cheatInputField;
    }
}
