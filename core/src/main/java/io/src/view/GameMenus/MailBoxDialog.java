package io.src.view.GameMenus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.src.model.GameObject.MailBox;

import java.util.List;

public class MailBoxDialog extends Dialog {

    private final MailBox mailBox;

    // تنظیمات قابل تغییر:
    private static final float DIALOG_WIDTH = 760f;   // بزرگ‌تر
    private static final float DIALOG_HEIGHT = 560f;  // بزرگ‌تر
    private static final float PANEL_PADDING = 10f;
    private static final float PANEL_WIDTH = 700f;    // پهنای پنل پیام
    private static final float SEPARATOR_HEIGHT = 2f; // ضخامت خط جداکننده

    // رنگ‌های دلخواه (می‌تونی تغییر بدی)
    private static final Color DIALOG_BG_COLOR = new Color(0.97f, 0.92f, 0.78f, 1f); // زردِ کمرنگِ منو/خاک
    private static final Color PANEL_BG_BRIGHT = new Color(1f, 1f, 1f, 1f);          // پس‌زمینه پیام روشن
    private static final Color PANEL_BG_SEEN = new Color(0.96f, 0.96f, 0.96f, 1f);   // پس‌زمینه پیامِ seen کمی خاکستری
    private static final Color SEPARATOR_COLOR = new Color(0.84f, 0.84f, 0.84f, 1f);

    // متن: unseen should be higher contrast (darker). seen is muted gray.
    private static final Color TEXT_COLOR_UNSEEN = new Color(0.06f, 0.06f, 0.06f, 1f); // تیره — کنتراست بالا
    private static final Color TEXT_COLOR_SEEN = new Color(0.45f, 0.45f, 0.45f, 1f);   // خاکستری ملایم

    public MailBoxDialog(Skin skin, MailBox mailBox) {
        super("Mailbox", skin);
        this.mailBox = mailBox;

        setModal(true);
        setMovable(false);

        // بک‌گراند دیالوگ: اگر اسکین drawable مناسب داشته باشه استفاده می‌کنیم،
        // در غیر این صورت یک drawable رنگی زردِ ملایم می‌سازیم.
        try {
            Drawable bg = skin.getDrawable("dialog-background");
            if (bg != null) background(bg);
            else background(createColoredDrawable(DIALOG_BG_COLOR));
        } catch (Exception ignored) {
            background(createColoredDrawable(DIALOG_BG_COLOR));
        }

        Table content = getContentTable();
        content.pad(16);

        // عنوان بالای لیست (استایل از اسکین استفاده می‌کنه)
        Label title = new Label("Mailbox", skin);
        title.setAlignment(Align.center);
        content.add(title).colspan(1).row();

        // جدول عمودی که پنل‌های پیام را نگه می‌دارد
        Table listTable = new Table();
        listTable.top().left();
        listTable.defaults().left().padTop(6).padBottom(6);

        // ابتدا unseen ها (آن‌ها را با متنِ پر contrast نمایش می‌دهیم)
        List<String> unseen = mailBox.getUnseenMessages();
        if (!unseen.isEmpty()) {
            Label newHeader = new Label("New messages", skin);
            newHeader.setAlignment(Align.left);
            newHeader.setColor(DIALOG_BG_COLOR); // header روشن‌تر
            listTable.add(newHeader).left().row();

            boolean first = true;
            for (String msg : unseen) {
                if (!first) addSeparator(listTable, skin);
                first = false;

                Actor panel = createMessageRow(skin, msg, false); // false => unseen (high contrast text)
                listTable.add(panel).width(PANEL_WIDTH).left().row();
            }
            // جداکننده بین گروه‌ها
            addSeparator(listTable, skin);
        }

        // سپس history (seen -> متن ملایم‌تر)
        List<String> history = mailBox.getHistoryMessages();
        if (!history.isEmpty()) {
            Label histHeader = new Label("History", skin);
            histHeader.setAlignment(Align.left);
            histHeader.setColor(TEXT_COLOR_SEEN);
            listTable.add(histHeader).padTop(6).row();

            boolean first = true;
            for (String msg : history) {
                if (!first) addSeparator(listTable, skin);
                first = false;

                Actor panel = createMessageRow(skin, msg, true); // true => seen (muted text)
                listTable.add(panel).width(PANEL_WIDTH).left().row();
            }
        }

        // اگر هیچ پیامی نیست
        if (unseen.isEmpty() && history.isEmpty()) {
            Label empty = new Label("No messages.", skin);
            empty.setAlignment(Align.left);
            listTable.add(empty).row();
        }

        // ScrollPane اصلی حاوی لیست
        ScrollPane scroll = new ScrollPane(listTable, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(false, false);
        content.add(scroll).width(DIALOG_WIDTH - 48).height(DIALOG_HEIGHT - 110).row();

        // دکمه Close
        button("Close", true);

        pack();
        setSize(DIALOG_WIDTH, getHeight());
    }

    /**
     * Create one narrow panel for a message.
     *
     * @param seen true => message is seen (render muted). false => unseen (high contrast text).
     */
    private Actor createMessageRow(Skin skin, String msg, boolean seen) {
        Table row = new Table(skin);
        row.left();
        row.pad(PANEL_PADDING);
        row.setFillParent(false);

        // بک‌گراند پنل: از skin drawable اگر موجود باشه استفاده کن، در غیر این صورت رنگ ملایم
        try {
            Drawable panelBg = skin.getDrawable("message-panel");
            if (panelBg != null) row.setBackground(panelBg);
            else {
                row.setBackground(createColoredDrawable(seen ? PANEL_BG_SEEN : PANEL_BG_BRIGHT));
            }
        } catch (Exception ignored) {
            row.setBackground(createColoredDrawable(seen ? PANEL_BG_SEEN : PANEL_BG_BRIGHT));
        }

        // متن پیام
        Label label = new Label(msg, skin);
        label.setWrap(true);
        label.setAlignment(Align.left);
        label.setFontScale(1f);

        // رنگ متن: unseen => high contrast (تیره)، seen => muted (خاکستری)
        if (seen) {
            label.setColor(TEXT_COLOR_SEEN);
        } else {
            label.setColor(TEXT_COLOR_UNSEEN);
        }

        row.add(label).width(PANEL_WIDTH - 20).left();

        return row;
    }

    /**
     * Add a thin separator line to listTable.
     * Uses skin's "white" drawable recolored when available otherwise creates pixmap-based drawable.
     */
    private void addSeparator(Table listTable, Skin skin) {
        Drawable sepDrawable;
        try {
            sepDrawable = skin.newDrawable("white", SEPARATOR_COLOR);
        } catch (Exception e) {
            sepDrawable = createColoredDrawable(SEPARATOR_COLOR);
        }
        Table sep = new Table();
        sep.setBackground(sepDrawable);
        listTable.add(sep).width(PANEL_WIDTH).height(SEPARATOR_HEIGHT).padTop(6).padBottom(6).row();
    }

    /**
     * Helper: create a 1x1 colored drawable (Pixmap -> TextureRegionDrawable).
     */
    private Drawable createColoredDrawable(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(tex));
    }

    @Override
    public void result(Object object) {
        // وقتی دیالوگ بسته شد، همه unseen ها به history منتقل می‌شوند (پس دفعه بعد تیره می‌شوند)
        mailBox.markAllUnseenAsSeen();
        // اگر لازم داری UI بیرونی رو اطلاع بدی اینجا صدا بزن
        // e.g., SomeUIManager.getInstance().onMailboxUpdated();
    }

    public void showCentered(Stage stage) {
        show(stage);
        // center dialog
        float x = (stage.getViewport().getWorldWidth() - getWidth()) / 2f;
        float y = (stage.getViewport().getWorldHeight() - getHeight()) / 2f;
        setPosition(x, y);
    }
}
