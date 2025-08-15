package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import io.src.model.Enums.MusicEnum;

import java.util.*;


public class GameAudioManager {
    public static ArrayList<String> innerPlayList = new ArrayList<>(Arrays.asList(MusicEnum.SpringTheme.getPath()
        , MusicEnum.PIANO1.getPath()
        , MusicEnum.PIANO2.getPath()
        , MusicEnum.PIANO3.getPath()
        , MusicEnum.SHUNIJI.getPath()
        , MusicEnum.AXOLOTL.getPath()
        , MusicEnum.CALM1.getPath()
        , MusicEnum.CALM2.getPath()
        , MusicEnum.DRAGON_FISH.getPath()));

    // for actions :
    public static float sfxVolume = 0.7f;
    // for step :
    public static float footStepVolume = 0.5f;
    // for music :
    public static float musicVolume = 0.2f;
    // for ambient :
    public static float ambientVolume = 0.7f;

    private static GameAudioManager instance;
    private List<String> playlist;
    private int playlistIndex;
    private Music currentMusic;
    private final HashMap<String, Sound> sounds = new HashMap<>();
    private final HashMap<String, Long> loopingSoundIds = new HashMap<>();

    public Music getCurrentMusic() {
        return currentMusic;
    }

    private GameAudioManager() {
    }

    public static GameAudioManager getInstance() {
        if (instance == null) {
            instance = new GameAudioManager();
        }
        return instance;
    }

    public void playMusic(String path, boolean loop, float volume) {
        stopMusic();
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        System.out.println("music volume " + currentMusic.getVolume());
        currentMusic.setLooping(loop);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    public void stopMusic() {
        if (currentMusic != null) currentMusic.stop();
    }

    public void pauseMusic() {
        if (currentMusic != null) currentMusic.pause();
    }

    public void resumeMusic() {
        if (currentMusic != null) currentMusic.play();
    }

    public void playSound(String path, boolean loop, float volume) {
        Sound sfx = sounds.get(path);
        if (sfx == null) {
            sfx = Gdx.audio.newSound(Gdx.files.internal(path));
            sounds.put(path, sfx);
        }

        if (loop) {
            long id = sfx.loop(volume);
            loopingSoundIds.put(path, id);
        } else {
            sfx.play(volume);
        }
    }

    public void stopSound(String path) {
        Sound sfx = sounds.get(path);
        if (sfx != null) {
            Long id = loopingSoundIds.remove(path);
            if (id != null) {
                sfx.stop(id);
            }
        }
    }

    public void stopSounds() {
        for (String path : loopingSoundIds.keySet()) {
            Sound sfx = sounds.get(path);
            try {
                sfx.stop(loopingSoundIds.remove(path));
            } catch (Exception e) {
                System.out.println("sound stop failed");
            }
        }
    }

    public void dispose() {
        if (currentMusic != null) currentMusic.dispose();
        for (Sound s : sounds.values()) {
            s.dispose();
        }
    }

    public void playPlaylist(List<String> tracks, float volume) {
        this.playlist = tracks;
        this.playlistIndex = 0;
        playNextFromPlaylist(volume);
    }

    private void playNextFromPlaylist(float volume) {
        if (playlist == null || playlistIndex >= playlist.size()) return;

        String path = playlist.get(playlistIndex++);
        playMusic(path, false, volume);
        currentMusic.setOnCompletionListener(music -> playNextFromPlaylist(volume));
    }


    private static final Random random = new Random();

    public static String pickRandom(List<String> items) {
        if (items == null || items.isEmpty()) {
            return null; // یا می‌تونی استثنا بندازی
        }
        int index = random.nextInt(items.size());
        return items.get(index);
    }
}
